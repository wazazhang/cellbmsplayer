package com.cell.sound.mute_impl;

import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;

public class NullSound implements ISound
{	
	@Override
	public SoundInfo getSoundInfo() {
		return null;
	}

	public void dispose() {}
}
