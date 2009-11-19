package com.cell.sound.decoder.ogg_impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.sound.Decoder;
import com.cell.sound.SoundInfo;

import com.jcraft.jogg.*;
import com.jcraft.jorbis.*;

public class OggDecoder extends Decoder
{
	final static String NAME = "ogg";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public SoundInfo decode(InputStream input) throws Exception
	{
		SoundInfo info = new SoundInfo();
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		int 		convsize	= 4096 * 2;
		byte[] 		convbuffer	= new byte[convsize];

		SyncState	oy 			= new SyncState();
		StreamState os 			= new StreamState();
		Page 		og 			= new Page();
		Packet 		op 			= new Packet();
		Info 		vi 			= new Info();
		Comment 	vc 			= new Comment();
		DspState 	vd 			= new DspState();
		Block 		vb 			= new Block(vd);

		byte[] buffer;
		int bytes = 0;

		oy.init();

		while (true) {
			int eos = 0;

			int index = oy.buffer(4096);
			buffer = oy.data;
			try {
				bytes = input.read(buffer, index, 4096);
			} catch (Exception e) {
				throw e;
			}
			oy.wrote(bytes);

			if (oy.pageout(og) != 1) {
				if (bytes < 4096)
					break;
				throw new IOException("Input does not appear to be an Ogg bitstream.");
			}

			os.init(og.serialno());
			vi.init();
			vc.init();
			if (os.pagein(og) < 0) {
				throw new IOException("Error reading first page of Ogg bitstream data.");
			}
			if (os.packetout(op) != 1) {
				throw new IOException("Error reading initial header packet.");
			}
			if (vi.synthesis_headerin(vc, op) < 0) {
				throw new IOException("This Ogg bitstream does not contain Vorbis audio data.");
			}

			int i = 0;
			while (i < 2) {
				while (i < 2) {
					int result = oy.pageout(og);
					if (result == 0)
						break;
					if (result == 1) {
						os.pagein(og);
						while (i < 2) {
							result = os.packetout(op);
							if (result == 0)
								break;
							if (result == -1) {
								throw new IOException("Corrupt secondary header.  Exiting.");
							}
							vi.synthesis_headerin(vc, op);
							i++;
						}
					}
				}
				index = oy.buffer(4096);
				buffer = oy.data;
				try {
					bytes = input.read(buffer, index, 4096);
				} catch (Exception e) {
					throw e;
				}
				if (bytes == 0 && i < 2) {
					throw new IOException("End of file before finding all Vorbis headers!");
				}
				oy.wrote(bytes);
			}

			{
//				byte[][] ptr = vc.user_comments;
//				for (int j = 0; j < ptr.length; j++) {
//					if (ptr[j] == null)
//						break;
//					System.err.println(new String(ptr[j], 0, ptr[j].length - 1));
//				}
			
//				System.err.println("\nBitstream is " + vi.channels+ " channel, " + vi.rate + "Hz");
//				System.err.println("Encoded by: "+ new String(vc.vendor, 0, vc.vendor.length - 1)+ "\n");
			}

			convsize = 4096 / vi.channels;

			vd.synthesis_init(vi);
			vb.init(vd);

			float[][][] _pcm = new float[1][][];
			int[] _index = new int[vi.channels];
			while (eos == 0) {
				while (eos == 0) {
					int result = oy.pageout(og);
					if (result == 0)
						break;
					if (result == -1) {
						System.err.println("Corrupt or missing data in bitstream; continuing...");
					} else {
						os.pagein(og);
						while (true) {
							result = os.packetout(op);
							if (result == 0)
								break;
							if (result == -1) {
							} else {
								
								int samples;
								if (vb.synthesis(op) == 0) {
									vd.synthesis_blockin(vb);
								}

								while ((samples = vd.synthesis_pcmout(_pcm, _index)) > 0) {
									float[][] pcm = _pcm[0];
									int bout = (samples < convsize ? samples : convsize);

									for (i = 0; i < vi.channels; i++) {
										int ptr = i * 2;
										int mono = _index[i];
										for (int j = 0; j < bout; j++) {
											int val = (int) (pcm[i][mono + j] * 32767.);
											if (val > 32767) {
												val = 32767;
											}
											if (val < -32768) {
												val = -32768;
											}
											if (val < 0)
												val = val | 0x8000;
											convbuffer[ptr] = (byte) (val);
											convbuffer[ptr + 1] = (byte) (val >>> 8);
											ptr += 2 * (vi.channels);
										}
									}
									output.write(convbuffer, 0, 2 * vi.channels * bout);
									
									vd.synthesis_read(bout);
								}
							}
						}
						if (og.eos() != 0)
							eos = 1;
					}
				}
				if (eos == 0) {
					index = oy.buffer(4096);
					buffer = oy.data;
					try {
						bytes = input.read(buffer, index, 4096);
					} catch (Exception e) {
						throw e;
					}
					oy.wrote(bytes);
					if (bytes == 0)
						eos = 1;
				}
			}
			os.clear();
			vb.clear();
			vd.clear();
			vi.clear();
		}

		oy.clear();
		
		info.channels	= vi.channels;
		info.frame_rate	= vi.rate;
		info.bit_length	= 16;
		info.size 		= output.size();
		info.data 		= ByteBuffer.wrap(output.toByteArray());		
		info.comment	= vc.toString();  
		
		return info;
	}
	
	public static void main(String[] args) throws Exception
	{
		OggDecoder dec = new OggDecoder();
		
		SoundInfo info = dec.decode(CIO.loadStream("D:\\EatWorld\\trunk\\eatworld\\data\\edit\\resource\\sound\\WC0002.ogg"));
		
		System.out.println("Done : decode \n" + info.toString());
	}
}
