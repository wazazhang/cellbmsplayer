package com.fc.lami
{
	import com.net.client.Client;
	import com.net.client.ServerSession;
	import com.net.client.minaimpl.ServerSessionImpl;
	import com.net.client.sfsimpl.SFSSessionImpl;

	public class LamiClient extends Client
	{
		public function LamiClient(ss : ServerSession)
		{
			super(ss);
//			super(new SFSSessionImpl(new MessageCodec()));
		}
	}
}