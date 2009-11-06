package com.cell;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;

import com.cell.util.MarkedHashtable;

/**
 * @author WAZA
 * 提供了对序列化支持的对象
 */
public abstract class ExtObject implements Externalizable
{
	/***
	 * 当反序列化结束后被调用<br>
	 * @param data
	 */
	abstract protected void onRead(MarkedHashtable data);

	/***
	 * 当序列化开始前被调用<br>
	 * @param data
	 */
	abstract protected void onWrite(MarkedHashtable data);
	
//	--------------------------------------------------------------------------------------------------------
	@Override
	final public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		MarkedHashtable	data_group = (MarkedHashtable)in.readObject();
		onRead(data_group);
	}
	@Override
	final public void writeExternal(ObjectOutput out) throws IOException {
		MarkedHashtable	data_group = new MarkedHashtable();
		onWrite(data_group);
		out.writeObject(data_group);
	}
	
}
