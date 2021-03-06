package Class.Model
{
	import com.fc.lami.Messages.DeskData;
	[Bindable]
	public class Desk
	{
		private var data : DeskData;
		
		//public var desk_id :  int;

		public var is_started :  Boolean;

		public var player_E_id :  int;

		public var player_W_id :  int;

		public var player_S_id :  int;

		public var player_N_id :  int;
		
		public function Desk(dd : DeskData)
		{
			this.data = dd;
			//desk_id = dd.desk_id;
			is_started = dd.is_started;
			player_E_id = dd.player_E_id;
			player_W_id = dd.player_W_id;
			player_S_id = dd.player_S_id;
			player_N_id = dd.player_N_id;
		}
		
		public function getDeskID() : int
		{
			return data.desk_id;
		}
		
		public function getName() : String
		{
			return data.desk_name;
		}
		
		public function update(dd : DeskData):void
		{
			this.data = dd;
			//desk_id = dd.desk_id;
			is_started = dd.is_started;
			player_E_id = dd.player_E_id;
			player_W_id = dd.player_W_id;
			player_S_id = dd.player_S_id;
			player_N_id = dd.player_N_id;
		}
		
		public function getSeat(player_id:int):int
		{
			var seat:int = -1;
			switch(player_id)
			{
				case player_N_id:seat = 0;break;
				case player_W_id:seat = 1;break;
				case player_S_id:seat = 2;break;
				case player_E_id:seat = 3;break;
			}
			return seat;
		}
		
		public function leaveDesk(player_id:int):void
		{
			switch(player_id)
			{
				case player_N_id:player_N_id = -1;break;
				case player_W_id:player_W_id = -1;break;
				case player_S_id:player_S_id = -1;break;
				case player_E_id:player_E_id = -1;break;
			}
		}
		
		public function sitDown(player_id:int, seat:int):Boolean
		{
			switch (seat)
			{
				case 0: 
					if (player_N_id!=-1)
						return false;
					player_N_id = player_id;
					return true;
				case 1:
					if (player_W_id!=-1)
						return false;
					player_W_id = player_id;
					return true;
				case 2:
					if (player_S_id!=-1)
						return false;
					player_S_id = player_id;
					return true;
				case 3:
					if (player_E_id!=-1)
						return false;
					player_E_id = player_id;
					return true;
			}
			return false;
		}
		
		public function get player_number():int
		{
			var n:int = 0;
			n = (player_N_id!=-1?1:0)+(player_W_id!=-1?1:0)+(player_S_id!=-1?1:0)+(player_E_id!=-1?1:0);
			return n;
		}
	}
}