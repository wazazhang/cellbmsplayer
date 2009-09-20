package com.cell.bms.jmf;

import com.cell.bms.BMSFile;
import com.cell.bms.IImage;
import com.cell.bms.ISound;

public class NoteFactory extends com.cell.bms.NoteFactory
{
	@Override
	public IImage createImage(BMSFile bms, String image) {
		return new JMFImage(bms, image);
	}
	@Override
	public ISound createSound(BMSFile bms, String sound) {
		return new JMFSound(bms, sound);
	}
}
