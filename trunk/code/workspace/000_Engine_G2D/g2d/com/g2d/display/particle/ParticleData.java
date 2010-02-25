package com.g2d.display.particle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ParticleData implements Iterable<Layer>, Serializable
{
	final private ArrayList<Layer> layers = new ArrayList<Layer>();

	public ParticleData() {
		layers.add(new Layer());
	}
	
	public void addLayer(Layer layer) {
		layers.add(layer);
	}
	
	public void removeLayer(Layer layer) {
		layers.remove(layer);
	}
	
	@Override
	public Iterator<Layer> iterator() {
		return layers.iterator();
	}
	
	public int size() {
		return layers.size();
	}
}
