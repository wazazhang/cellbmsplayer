package com.cell.rpg.particle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ParticleSystem implements Iterable<Layer>, Serializable
{
	final private ArrayList<Layer> layers = new ArrayList<Layer>();
	
	public ParticleSystem() {
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
}
