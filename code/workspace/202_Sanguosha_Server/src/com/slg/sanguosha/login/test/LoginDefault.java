package com.slg.sanguosha.login.test;

import java.util.ArrayList;

import com.cell.CIO;
import com.net.server.ClientSession;
import com.slg.entity.Player;
import com.slg.login.Login;
import com.slg.login.User;

public class LoginDefault implements Login
{
	@Override
	public User login(ClientSession session, String uid, String validate) {
		return new DefaultUser(uid);
	}
	
	private class DefaultUser implements User
	{
		private String name;
		
		private ArrayList<Player> player_list;
		
		public DefaultUser(String name) {
			this.name = name;
			player_list = new ArrayList<Player>();
			Player p = new Player();
			p.name = this.name;
			player_list.add(p);
		}
		
		@Override
		public String getUID() {
			return name;
		}
		
//		@Override
//		public String getName() {
//			return name;
//		}

		
		@Override
		public void save() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Player getDefaultPlayer() {
			if (player_list.size()>0){
				return player_list.get(0);
			}else{
				return null;
			}
		}

//		@Override
//		public Player getPlayer(int index) {
//			if (player_list.size()>index){
//				return player_list.get(index);
//			}else{
//				return null;
//			}
//		}
		
	}
}
