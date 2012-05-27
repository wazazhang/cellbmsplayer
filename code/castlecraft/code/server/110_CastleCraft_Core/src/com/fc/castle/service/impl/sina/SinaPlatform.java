package com.fc.castle.service.impl.sina;

import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.fc.castle.service.PlatformService;

public class SinaPlatform extends PlatformService
{
//	客户端的验证授权（Resource Owner Password Credentials）
//	基本流程
//	
//	1．调用
//	https://api.weibo.com/oauth2/access_token?
//	client_id=YOUR_CLIENT_ID&
//	client_secret=YOUR_CLIENT_SECRET&
//	grant_type=password&
//	username=USER_NAME&
//	password=PASSWORD
//	返回值 { "access_token":"SlAV32hkKG", "expires_in":3600 }
//	
//	2. 使用获得的OAuth2.0 Access Token调用API
//	
//	注：客户端的验证授权需要申请

	@Override
	public void login(String user, String password, LoginListener listener) 
	{			
		try {
			// 创建URL对象
			URL myURL = new URL("https://api.weibo.com/oauth2/access_token?" +
					"client_id=2729823448&" +
					"client_secret=ade263cdc79d15736fdcafc36a02afac&" +
					"grant_type=password&" +
					"username="+user+"&" +
					"password="+password);
			
			System.out.println(myURL.getQuery());
			
			// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
			HttpsURLConnection httpsConn = (HttpsURLConnection)myURL.openConnection();
			
			// 取得该连接的输入流，以读取响应内容
			InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream());

			// 读取服务器的响应内容并显示
			int respInt = insr.read();
			while (respInt != -1) {
				System.out.print((char) respInt);
				respInt = insr.read();
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			listener.onError(user, e.getMessage());
		}

	}
	
	public static void main(String[] args)
	{
		SinaPlatform sina = new SinaPlatform();
		
		sina.login("wazazhang@gmail.com", "1122343466", new PlatformService.LoginListener() {
			@Override
			public void onError(String user, String reson) {
				
			}
			@Override
			public void onComplete(String user, String response_text) {
				
			}
		});
	}
}
