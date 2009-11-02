package com.cell.sound.openal_impl;

import java.io.*;
import java.net.*;
import java.util.*;

import de.jarnbjo.ogg.*;

/**
 *  Implementation of the <code>PhysicalOggStream</code> interface for reading
 *  an Ogg stream from a URL. This class performs only the necessary caching
 *  to provide continous playback. Seeking within the stream is not supported.
 */
@SuppressWarnings("unchecked")
class OggInputStream implements PhysicalOggStream
{
   private boolean closed=false;

   private InputStream sourceStream;
   private Object drainLock=new Object();
   private LinkedList pageCache=new LinkedList();
   private long numberOfSamples=-1;

   private HashMap logicalStreams=new HashMap();

   private LoaderThread loaderThread;

   private static final int PAGECACHE_SIZE = 10;

	/** Creates an instance of the <code>PhysicalOggStream</code> interface
	 *  suitable for reading an Ogg stream from a URL. 
	 */

   public OggInputStream(InputStream is) throws OggFormatException, IOException
   {
		this.sourceStream = is;

		loaderThread = new LoaderThread(sourceStream, pageCache);
		new Thread(loaderThread).start();

		while (!loaderThread.isBosDone() || pageCache.size() < PAGECACHE_SIZE) {
			 Thread.yield();
			// System.out.print("caching "+pageCache.size()+"/"+PAGECACHE_SIZE+" pages\r");
		}
      //System.out.println();
   }

   public Collection getLogicalStreams() {
      return logicalStreams.values();
   }

   public boolean isOpen() {
      return !closed;
   }

   public void close() throws IOException {
      closed=true;
      sourceStream.close();
   }


   public OggPage getOggPage(int index) throws IOException {
      while(pageCache.size()==0) {
         try {
            Thread.sleep(100);
         }
         catch (InterruptedException ex) {
         }
      }
      synchronized(drainLock) {
         return (OggPage)pageCache.removeFirst();
      }
   }

   private LogicalOggStream getLogicalStream(int serialNumber) {
      return (LogicalOggStream)logicalStreams.get(new Integer(serialNumber));
   }

   public void setTime(long granulePosition) throws IOException {
      throw new UnsupportedOperationException("Method not supported by this class");
   }

   class LoaderThread implements Runnable {

      private InputStream source;
      private LinkedList pageCache;
      private RandomAccessFile drain;
      private byte[] memoryCache;

      private boolean bosDone=false;

      private int pageNumber;

      public LoaderThread(InputStream source, LinkedList pageCache) {
         this.source=source;
         this.pageCache=pageCache;
      }

      public void run() {
         try {
            boolean eos=false;
            byte[] buffer=new byte[8192];
            while(!eos) {
               OggPage op=OggPage.create(source);
               synchronized (drainLock) {
                  pageCache.add(op);
               }

               if(!op.isBos()) {
                  bosDone=true;
               }
               if(op.isEos()) {
                  eos=true;
               }

               LogicalOggStreamImpl los=(LogicalOggStreamImpl)getLogicalStream(op.getStreamSerialNumber());
               if(los==null) {
                  los=new LogicalOggStreamImpl(OggInputStream.this, op.getStreamSerialNumber());
                  logicalStreams.put(new Integer(op.getStreamSerialNumber()), los);
                  los.checkFormat(op);
               }

               //los.addPageNumberMapping(pageNumber);
               //los.addGranulePosition(op.getAbsoluteGranulePosition());

               pageNumber++;

               while(pageCache.size()>PAGECACHE_SIZE) {
                    Thread.yield();
               }
            }
         }
         catch(EndOfOggStreamException e) {
            // ok
         }
         catch(IOException e) {
            e.printStackTrace();
         }
      }

      public boolean isBosDone() {
         return bosDone;
      }
   }

	/** 
	 *  @return always <code>false</code>
	 */

   public boolean isSeekable() {
      return false;
   }

}