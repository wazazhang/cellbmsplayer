package com.g2d.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;

@SuppressWarnings("serial")
public class TextEditor extends JPanel
{
	JTextPane text_pane = new JTextPane();
	
	public TextEditor() 
	{
		super(new BorderLayout());
		
		super.add(new JScrollPane(text_pane), BorderLayout.CENTER);
		
	}
	
	public JTextPane getTextPane() {
		return text_pane;
	}
	
	public void setText(String text) {
		text_pane.setText(text);
	}
	
	public void insertText(String text, int index) {
		try {
			
			text_pane.getStyledDocument().insertString(0, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public String getText() {
		return text_pane.getText();
	}
	
	
}
