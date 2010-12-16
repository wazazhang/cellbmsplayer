import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cell.j2se.CAppBridge;
import com.g2d.Canvas;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.event.MouseEvent;
import com.g2d.java2d.SimpleFrame;
import com.g2d.java2d.SimpleStage;
import com.g2d.java2d.impl.AwtEngine;


public class Test extends Stage
{
	@Override
	public void inited(Canvas root, Object[] args)
	{
		addChild(new TestShape());
		addChild(new TestShape());
		addChild(new TestShape());
		addChild(new TestShape());
		
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removed(DisplayObjectContainer parent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
	
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fill(local_bounds);
		g.setColor(Color.WHITE);
		g.drawString("FPS="+getRoot().getFPS(), 0, 0);
		
	}
	
	
	public static void main(String[] args) 
	{

    	EventQueue.invokeLater(new Runnable() {
            public void run() {
            	CAppBridge.init();
            	new AwtEngine();
                SimpleFrame frame = new SimpleFrame(800, 600);
                frame.setVisible(true);
                frame.start(60, Test.class);
            }
        });
    
	}
}
