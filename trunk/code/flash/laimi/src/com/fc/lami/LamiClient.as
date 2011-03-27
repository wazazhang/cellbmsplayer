package com.fc.lami
{
	import com.net.client.Client;
	import com.net.client.minaimpl.ServerSessionImpl;
	public class LamiClient extends Client
	{
		public function LamiClient()
		{
			
			super(new ServerSessionImpl(new MessageCodec()));
		}
	}
}