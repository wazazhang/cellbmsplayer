package Class
{
	import com.net.client.Client;
	import com.net.client.ServerSession;
	import com.net.client.minaimpl.ServerSessionImpl;
	import com.net.client.sfsimpl.SFSSessionImpl;

	public class SanguoshaClient extends Client
	{
		public function SanguoshaClient(ss : ServerSession)
		{
			super(ss);
//			super(new SFSSessionImpl(new MessageCodec()));
		}
	}
}