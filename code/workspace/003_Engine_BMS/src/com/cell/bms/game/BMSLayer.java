package com.cell.bms.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import com.cell.bms.BMSPlayer;
import com.cell.bms.BMSPlayerListener;
import com.cell.bms.IDefineImage;
import com.cell.bms.BMSFile.Note;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.j2se.CGraphics;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSprite;
import com.g2d.cell.CellSetResource.WorldSet;
import com.g2d.cell.CellSetResource.WorldSet.SpriteObject;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;
import com.g2d.display.Stage;

public class BMSLayer extends Sprite implements BMSPlayerListener
{
	BMSPlayer		player;
	
	CellSetResource	skin;
	CSprite			effect;
	CSprite			keys;
	CSprite			nodes;
	
	Key[]			key_map		= new Key[40];
	
	
	
	
	public BMSLayer(BMSPlayer player)
	{
		this.player = player;
		this.player.is_auto_play = true;
		this.player.addListener(this);
		
		try 
		{
			skin	= new CellSetResource("/skin.properties");
			effect	= skin.getSprite("hit");
			keys	= skin.getSprite("keys");
			nodes	= skin.getSprite("nodes");
			
			WorldSet main_frame = skin.getSetWorld("main_frame");
			for (SpriteObject obj : main_frame.Sprs) {
				if (obj.SprID.equals("keys")) {
					Key k = new Key(obj);
					addChild(k, true);
					key_map[k.track] = k;
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void added(DisplayObjectContainer parent) {
		super.added(parent);
		if (parent instanceof StageGame) {
			setSize(parent.getWidth(), parent.getHeight());
			player.start();
		} 
	}
	
	@Override
	public void update()
	{
		super.update();
		player.update();
	}
	
	@Override
	public void render(Graphics2D g) 
	{
		CGraphics cg = new CGraphics(g);
		
		// paint notes
		{
			IDefineImage img_bg = player.getPlayBGImage();
			if (img_bg != null && img_bg.getImage() != null) {
				g.drawImage(img_bg.getImage(), 0, 0, getWidth(), getHeight(), this);
			} else {
				g.setColor(Color.BLACK);
				g.fill(local_bounds);
			}
			
			g.setColor(Color.WHITE);
			for (Note note : player.getPlayTracks(player.getBMSFile().LINE_SPLIT_DIV * 4)) {
				Key k = key_map[note.track];
				if (k != null) {
					double x = k.getX() + (k.getWidth() >> 1);
					double y = (player.getPlayPosition() - note.getBeginPosition()) + k.y;
					nodes.render(cg, (int)x, (int)y, k.anim, timer % nodes.getFrameCount(k.anim));
				} else {
					double x = getHeight()-8;
					double y = (player.getPlayPosition() - note.getBeginPosition()) + getHeight();
					g.fillRect((int) x, (int) y, 8, 2);
				}
			}
		}
	}
	
	@Override
	public void onBeat(BMSPlayer player, int beatCount) {
//		System.out.println("BEAT : " + beatCount + " : " + player.getPlayPosition());
	}
	
	@Override
	public void onDropNote(BMSPlayer player, Note note) {
//		System.out.println("Drop Note : " + note);
	}

//	-------------------------------------------------------------------------------------------------------------------------------------
	
	class Key extends CellSprite
	{
		int anim;
		int track;
		int key_value;
		
		public Key(SpriteObject obj)
		{
			super(keys);
			super.setLocation(obj.X, obj.Y);
			
			this.anim		= obj.Anim;
			this.track		= Integer.parseInt(cspr.getAnimateName(obj.Anim));
			this.key_value	= Config.getKey(track);
			
			cspr.setCurrentFrame(anim, 0);
			CCD cd = cspr.getFrameBounds(anim, 0);
			
			super.setSize(cd.getWidth(), cd.getHeight());
		}
		
		@Override
		public void update() {
			if (getRoot().isKeyHold(key_value)) {
				cspr.setCurrentFrame(anim, 1);
			} else {
				cspr.setCurrentFrame(anim, 0);
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------------
	
	class Node extends CellSprite 
	{
		public Node() 
		{
			super(nodes);
		}
		
	}

//	-------------------------------------------------------------------------------------------------------------------------------------

	class Effect extends CellSprite
	{
		public Effect() {
			super(effect);
		}
		
		@Override
		public void render(Graphics2D g) {
			super.render(g);
			if (cspr.nextFrame()) {
				removeFromParent();
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------------
	
}
