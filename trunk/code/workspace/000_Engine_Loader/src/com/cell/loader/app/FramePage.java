package com.cell.loader.app;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Vector;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 * 
[main]
update_path			=http://game.lordol.com/lordol_xc_test/update.val
ignore_list			=loader.jar,lordol_res.jar,lordol_ressk.jar,lordol_j2se_ui_sk.jar
l_document			=loader.html
l_app_working_dir	=.
l_font				=宋体
l_envp				=envp

	
[image]
img_bg				=bg.png
img_loading_f		=loading_f.png
img_loading_s 		=loading_s.png
img_loading_b 		=loading_b.png

[text]
l_text_loading		=更新文件中...
l_text_initializing	=初始化中...
l_text_error		=更新错误!
l_text_check		=检查更新中...

[net]
load_retry_count	=5
load_timeout		=10000



<a 	href="l_app=client.exe ./ws_tw_sh/client.properties" target="ws_tw_sh">
		[吞食]上海测试地址
</a>
l_app=前缀
*/

@SuppressWarnings("serial")
public class FramePage extends LoaderFrame
{
	private static final long serialVersionUID = 1L;
	
	String l_document;
	String l_envp[];
	String l_app_working_dir;
	
	
	public FramePage(String update_ini_file)
	{
		super(update_ini_file);
	}
	
	@Override
	protected void onTaskInit(Map<String, String> update_ini)
	{
		this.l_document	= update_ini.get("l_document");
		this.l_app_working_dir	= update_ini.get("l_app_working_dir");
		if (l_app_working_dir == null) {
			l_app_working_dir = ".";
		}
		try{
			String envp = update_ini.get("l_envp");
			if (envp != null) {
				l_envp = envp.split(",");
			}
		} catch (Exception err) {}
	}
	
	@Override
	protected void onTaskOver(Vector<byte[]> datas) throws Exception 
	{
		if (this.l_document != null) {
			System.out.println(l_document);
		}
		new Thread(){
			public void run() {
				if (l_document != null) {
					DocumentPage page = new DocumentPage(l_document);
					add(page);
					validate();
				}
			}
		}.start();
	}

	class DocumentPage extends JPanel implements HyperlinkListener
	{
		JEditorPane html_page ;
		
		public DocumentPage(String path)
		{
			super(new BorderLayout());
			try
			{
				File file = new File(path);
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[fis.available()];
				fis.read(data);
				fis.close();
				String text = new String(data, "UTF-8");
				
				html_page = new JEditorPane();
				html_page.setEditable(false); // 请把editorPane设置为只读，不然显示就不整齐
				html_page.setPage("file:///"+file.getAbsolutePath());
				html_page.setFont(FramePage.this.getFont());
//				html_page.setContentType("text/html");
				html_page.setText(text);
				html_page.addHyperlinkListener(this);
				JScrollPane scroll = new JScrollPane(html_page);
				this.add(scroll, BorderLayout.CENTER);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		// 超链监听器，处理对超级链接的点击事件，但对按钮的点击还捕获不到
		public void hyperlinkUpdate(HyperlinkEvent e) 
		{
			
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
			{
				System.out.println(e.getDescription());
				
				if (e.getDescription().startsWith("l_app="))
				{
					try {
						String l_app = e.getDescription().substring(6).trim();
						System.out.println("open : " +  l_app);
						Process process = Runtime.getRuntime().exec(
								l_app, 
								l_envp, 
								new File(l_app_working_dir));
						System.out.println(process);
						this.setVisible(false);
						new Thread(){
							public void run() {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								System.exit(1);
							}
						}.start();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				} 
				else //if (e instanceof HTMLFrameHyperlinkEvent) 
				{
					System.out.println("open url : " + e.getURL());
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} 
			}
		}  
		
	

		
		
	}
	
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			new FramePage(args[0]);
		} else {
			if (System.getProperty("com.cell.loader.app.config")!=null) {
				System.out.println("get system property : \"com.cell.loader.app.config\"" );
				new FramePage(System.getProperty("com.cell.loader.app.config"));
			} else {
				new FramePage("./update.ini");
			}
		}
	}
	
}
