package com.g2d.display.event;

/**
 * 是否拥有获得所有键盘鼠标事件的焦点，
 * 一旦该控件是Stage中Picked的单位，
 * 则Root.isKeyDown等方法都返回false
 * @return
 */
public interface KeyInputer {

	/**
	 * 是否拥有获得所有键盘鼠标事件的焦点，
	 * 一旦该控件是Stage中Picked的单位，
	 * 则Root.isKeyDown等方法都返回false
	 * @return
	 */
	public boolean isInput() ;
	
}
