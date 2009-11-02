package com.cell.sound;

import java.nio.ByteBuffer;

public class SoundInfo
{
	public String		resource;
	
	/** stereo mono */
	public int			channels;
	
	/** 16bit 8bit */
	public int			bit_length;
	
	/** 44100hz 22050hz */
	public int 			frame_rate;
	
	/** PCM raw data */
	public ByteBuffer 	data;
	
	/** raw data size */
	public int			size;
	
	/** file comment */
	public String		comment = "";
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("channels: " + channels + "\n");
		sb.append("bit_length: " + bit_length + "\n");
		sb.append("frame_rate: " + frame_rate + "\n");
		sb.append("size: " + size + "\n");
		sb.append(comment);
		return sb.toString();
	}

}
