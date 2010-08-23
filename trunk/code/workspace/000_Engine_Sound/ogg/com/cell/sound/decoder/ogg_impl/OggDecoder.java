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
	public SoundInfo decode(String resource, InputStream input) throws Exception
	{
		OggStreamSoundInfo info = new OggStreamSoundInfo(resource, input);
		info.run();
		return info;
	}
	
	public static void main(String[] args) throws Exception
	{
		OggDecoder dec = new OggDecoder();
		
		SoundInfo info = dec.decode(
				"D:\\Projects\\EatWorld\\trunk\\data\\edit\\resource\\sound\\WC0002.ogg",
				CIO.loadStream("D:\\Projects\\EatWorld\\trunk\\data\\edit\\resource\\sound\\WC0002.ogg"));
		
		System.out.println("Done : decode \n" + info.toString());
	}
}
