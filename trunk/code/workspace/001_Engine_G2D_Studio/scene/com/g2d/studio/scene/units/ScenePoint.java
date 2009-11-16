package com.g2d.studio.scene.units;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import com.cell.math.MathVector;
import com.cell.rpg.scene.Point;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.ui.Menu;
import com.g2d.display.ui.Menu.MenuItem;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.Version;
import com.g2d.studio.scene.editor.PointLinkMenu;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneUnitMenu;


@Property("一个点，通常用于路点")
public class ScenePoint extends com.g2d.game.rpg.Unit implements SceneUnitTag<Point>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	final Point 				point;
	final SceneEditor			editor;
	final HashSet<ScenePoint>	next_nodes 	= new HashSet<ScenePoint>();
	
	@Property("color")
	Color 						color 		= new Color(0xffffff00, true);
	Rectangle 					snap_shape 	= new Rectangle(-1, -1, 2, 2);

//	--------------------------------------------------------------------------------------------------------
	
	public ScenePoint(SceneEditor editor, int x, int y) 
	{
		this.editor		= editor;
		this.setLocation(x, y);
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		this.point		= new Point(getID()+"", x, y);
	}
	
	public ScenePoint(SceneEditor editor, Point in) throws IOException
	{
		this.editor		= editor;
		this.point 		= in;
		{
			this.setID(editor.getGameScene().getWorld(), 
					point.name);
			this.setLocation(
					point.x,
					point.y);
			this.color = new Color(
					point.color, true);
			this.alpha = point.alpha;
		}
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
	}

	@Override
	public Point onWrite()
	{
		point.name		= getID() + "";
		point.x			= getX();
		point.y			= getY();
		point.color		= color.getRGB();
		point.alpha		= alpha;
		return point;
	}
	
	@Override
	public void added(DisplayObjectContainer parent) 
	{
		enable				= true;
		enable_drag			= true;
		enable_input		= true;
		enable_focus 		= true;
		enable_input 		= true;
		local_bounds.setBounds(-4, -4, 8, 8);
		priority = Integer.MAX_VALUE / 2;
		super.added(parent);
	}
	
	@Override
	public void removed(DisplayObjectContainer parent) {
		super.removed(parent);
		try{
			synchronized(next_nodes) {
				for (ScenePoint p : editor.getGameScene().getWorld().getChildsSubClass(ScenePoint.class)) {
					if (p.next_nodes.contains(this)) {
						p.next_nodes.remove(this);
					}
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
//	--------------------------------------------------------------------------------------------------------

	@Override
	public void onReadComplete(Vector<SceneUnitTag<?>> all) {
		next_nodes.clear();
		if (point.next_ids!=null) {
			for (String next : point.next_ids) {
				try{
					ScenePoint next_point = editor.getUnit(ScenePoint.class, next);
					next_nodes.add(next_point);
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		}
	}
	@Override
	public void onWriteReady(Vector<SceneUnitTag<?>> all) {
		point.next_ids = new ArrayList<String>(next_nodes.size());
		for (ScenePoint next : next_nodes) {
			try{
				point.next_ids.add(next.getID()+"");
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public Point getUnit() {
		return point;
	}
	
	@Override
	public ScenePoint getGameUnit() {
		return this;
	}
	
	@Override
	public Color getSnapColor() {
		return Color.YELLOW;
	}
	
	@Override
	public Shape getSnapShape() {
		return snap_shape;
	}

//	--------------------------------------------------------------------------------------------------------
	
	public javax.swing.ImageIcon getIcon(boolean update) {
		return null;
	}
	
	@Override
	public String getName() {
		return getID() + "";
	}
	
//	--------------------------------------------------------------------------------------------------------
	
//	@Override
//	public Menu getEditMenu() {
//		return new UnitMenu(scene_view, this);
//	}
	
//	--------------------------------------------------------------------------------------------------------

	public HashSet<ScenePoint> getNextNodes() {
		return next_nodes;
	}
	
	public void linkNext(ScenePoint next) {
		synchronized(next_nodes) {
			next_nodes.add(next);
		}
	}
	
	public void removeLink(ScenePoint next) {
		synchronized(next_nodes) {
			next_nodes.remove(next);
		}
	}

	public Menu getLinkMenu(final ScenePoint next) 
	{
		return new PointLinkMenu(this, next);
	}
	
//	--------------------------------------------------------------------------------------------------------
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		if (editor!=null) 
		{
			if (editor.getSelectedPage().isSelectedType(getClass())) 
			{
				g.setColor(color);
				float talpha = 0.5f;
				if (editor.getSelectedUnit() == this) {
					g.setColor(Color.WHITE);
					talpha = 1f;
				}
				if (!next_nodes.isEmpty()) {
					synchronized(next_nodes) {
						pushObject(g.getComposite());
						pushObject(g.getStroke());
						setAlpha(g, talpha);
						g.setStroke(new BasicStroke(2));
						int[] tx3 = new int[]{-4,-20,-20};
						int[] ty3 = new int[]{ 0, -5,  5};
						for (ScenePoint next : next_nodes) {
							double nx = next.x - x;
							double ny = next.y - y;
							double angle = MathVector.getDegree(nx, ny);
							g.drawLine(0, 0, (int)nx, (int)ny);
							g.translate(nx, ny);
							g.rotate(angle);
							g.fillPolygon(tx3, ty3, 3);
							g.rotate(-angle);
							g.translate(-nx, -ny);
						}
						g.setStroke(popObject(Stroke.class));
						g.setComposite(popObject(Composite.class));
					}
				}
				g.fill(local_bounds);
				// 选择了该精灵
				if (editor.getSelectedUnit() == this) {
					g.setColor(Color.BLUE);
					g.draw(local_bounds);
				} 
				// 当鼠标放到该精灵上
				else if (isCatchedMouse() && editor.isToolSelect()) {
					g.setColor(Color.RED);
					g.draw(local_bounds);
				}
				this.enable = editor.isToolSelect();
			} else {
				Composite composite = g.getComposite();
				setAlpha(g, alpha * 0.5f);
				g.setColor(color);
				g.fill(local_bounds);
				g.setComposite(composite);
				this.enable = false;
			}
		}
	}
	
//	@Override
//	public DisplayObjectEditor<?> createEditorForm() 
//	{
//		return new DisplayObjectEditor<ScenePoint>(
//				this,
//				new RPGUnitPanel(point),
//				new AbilityPanel(this, point));
//	}
	
	@Override
	public String toString() 
	{
		return getID()+"";
	}
	
	
//	@Override
//	public DisplayObjectEditor<?> createEditorForm() 
//	{
//		return new DisplayObjectEditor<ScenePoint>(
//				this,
//				new RPGUnitPanel(point),
//				new AbilityPanel(this, point));
//	}
	
	@Override
	public Menu getEditMenu() {
		return new SceneUnitMenu(editor, this);
	}
}