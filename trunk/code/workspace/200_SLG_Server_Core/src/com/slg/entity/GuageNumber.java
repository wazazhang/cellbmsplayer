package com.slg.entity;

public class GuageNumber {
	int value;
	int max;
	int min;
	
	public GuageNumber(){
		min = 0;
		max = 100;
		value = 0;
	}
	
	public GuageNumber(int min, int max){
		this.min = min;
		this.max = max;
		value = min;
	}
	
	public GuageNumber(int min, int max, int value){
		this.min = min;
		this.max = max;
		this.value = value;
	}
	public int getValue(){
		return value;
	}
	
	public void setValue(int v){
		this.value = v;
	}
	
	public int getMaxValue(){
		return max;
	}
	public void setMaxValue(int v){
		this.max = v;
	}
	
	public int getMinValue(){
		return min;
	}
	public void setMinValue(int v){
		this.min = v;
	}
	public int add(int v){
		if (value+v>max){
			value = max;
		}
		else if (value+v<min){
			value = min;
		}
		else{
			value = value+v;
		}
		return value;
	}
	
	public double getPercent(){
		return (double)(value-min)/(double)Math.max(max-min, 1);
	}
}
