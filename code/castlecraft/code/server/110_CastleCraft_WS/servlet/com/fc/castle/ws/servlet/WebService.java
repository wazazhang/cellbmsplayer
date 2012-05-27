package com.fc.castle.ws.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.security.MD5;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.Request;
import com.fc.castle.data.message.Messages.GetUnitTemplateRequest;
import com.fc.castle.data.message.Messages.LoginRequest;
import com.fc.castle.data.message.Messages.LoginResponse;
import com.fc.castle.data.message.Response;
import com.fc.castle.ws.WSHttpRequest;
import com.fc.castle.ws.impl.game.WSGameManager;

public class WebService extends WSHttpRequest 
{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(WebService.class);
	
	private static ConcurrentHashMap<Integer, String> logined_pid = new ConcurrentHashMap<Integer, String>();
	

	@Override
	protected boolean onReceivedMessage(Request data, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		if (data instanceof LoginRequest) 
		{
			return true;
		} 
		else if (data != null)
		{
			// 已认证的登录，记录其登录信息已作为安全验证
			// 单节点只需要在内存里保存验证资料
			// 如果分布式，则考虑使用 Memcached
			String pid = request.getHeader("FC-PID");
			String key = request.getHeader("FC-KEY");
			if (pid != null && key != null) {
				data.player_id = Integer.parseInt(pid);
				if (key.equals(logined_pid.get(data.player_id))) {
					return true;
				}
			}
			log.info("Access denied : pid="+pid + " | key=" + key);
			return false;
		} 
		else
		{
			return false;
		}
	}
	
	@Override
	protected boolean onSendMessage(Response data, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		// 已认证的登录，记录其登录信息已作为安全验证
		if (data instanceof LoginResponse) 
		{
			LoginResponse login = (LoginResponse)data;
			if (login.result == LoginResponse.RESULT_SUCCEED) 
			{
				if (login.login_account != null) {
					login.login_key = MD5.getMD5(request.getRemoteAddr());
					logined_pid.put(login.player_data[0].player_id, login.login_key);
//					request.getRemotePort()
					
					System.out.println(request.getRemoteAddr() + " -> " + login.login_key);
				} else {
					return false;
				}
			}
			return true;
		}
		else 
		{
			return (data != null);
		}
	}
}
