package com.cell.bms;

public abstract class NoteFactory {

	static private	NoteFactory instance;
	static public	NoteFactory getInstance() {
		return instance;
	}
	static public	NoteFactory setInstance(NoteFactory new_instance) {
		NoteFactory old_instance = instance;
		instance = new_instance;
		return old_instance;
	}
	
	
	
	abstract public ISound createSound(BMSFile bms, String sound);
	
	abstract public IImage createImage(BMSFile bms, String image);
	
	
	
}
