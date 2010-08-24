package com.cell.sound.decoder.ogg_impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.sound.Decoder;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;
import com.cell.sound.openal_impl.JALSoundManager;
import com.cell.sound.util.StreamSoundPlayer;

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
	public SoundInfo decode(String resource, InputStream input) throws Exception
	{
		OggStreamSoundInfo info = new OggStreamSoundInfo(resource, input);
		info.run();
		return info;
	}
	
	public static void main(String[] args) throws Throwable
	{
	
//		String url = "D:\\Projects\\EatWorld\\trunk\\data\\edit\\resource\\sound\\WC0002.ogg";

		String url = "http://game.lordol.com/eatworld_test/sound/WC0019.ogg";
		
//		OggDecoder dec = new OggDecoder();
//		SoundInfo info = dec.decode(url, CIO.loadStream(url));
//		System.out.println("Done : decode \n" + info.toString());
		
//		OggStreamSoundInfo stream = new OggStreamSoundInfo(url, CIO.getInputStream(url));
//		
//		Thread t = new Thread(stream);
//		t.start();
//		int total = 0;
//		while (t.isAlive()) {
//			if (stream.hasData()) {
//				ByteBuffer buffer = stream.getData();
//				int readed = buffer.remaining();
//				System.out.println("stream " + readed + " bytes, total " + CUtil.getBytesSizeString(total));
//				total += readed;
//			}
//			Thread.sleep(1000);
//		}
		SoundManager.setSoundManager(JALSoundManager.getInstance());
		StreamSoundPlayer player = new StreamSoundPlayer(
				SoundManager.getSoundManager().createPlayer(),
				new OggStreamSoundInfo(url, CIO.getInputStream(url)));
		
		
		while (true) {
			if (player.update()) {
//				System.out.println("stream completed !");
			}
			Thread.sleep(500);
		}
	}
}
