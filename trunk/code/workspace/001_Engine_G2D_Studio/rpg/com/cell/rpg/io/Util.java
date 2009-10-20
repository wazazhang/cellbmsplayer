package com.cell.rpg.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.cell.rpg.entity.Unit;

public class Util
{
	
	final static public void wirteObjects(ArrayList<Unit> objects, ObjectOutputStream oos) throws IOException
	{
		ArrayList<Unit> outputs = new ArrayList<Unit>(objects);
		oos.writeObject(outputs);
	}
	
	final static public ArrayList<Unit> readObjects(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		@SuppressWarnings("unchecked")
		ArrayList<Unit> outputs = (ArrayList<Unit>)ois.readObject();
		return outputs;
	}
	
}
