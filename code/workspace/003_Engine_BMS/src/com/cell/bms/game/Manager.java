package com.cell.bms.game;

import com.cell.bms.NoteFactory;
import com.cell.bms.oal.JALNoteFactory;

public class Manager 
{
	private static NoteFactory note_factory;
	
	public static NoteFactory getNoteFactory() {
		if (note_factory == null) {
			try{
				note_factory = new JALNoteFactory();
			}catch(Exception err) {
				err.printStackTrace();
			}
		}
		return note_factory;
	}
	
}
