package Class.Model
{
	import com.fc.lami.Messages.DeskData;
	import com.fc.lami.Messages.PlayerData;
	import com.fc.lami.Messages.RoomData;
	
	import flash.utils.Dictionary;
	
	import mx.charts.chartClasses.DataDescription;
	import mx.collections.ArrayCollection;

	[Bindable]
	public class Room
	{
		
		private var player_list : Dictionary = new Dictionary();
		private var desk_list : Array = new Array();
		
		private var desk_number:int;
		public var roomData:RoomData;
		
		public function Room(room_data:RoomData)
		{
			this.roomData = room_data;
			for each(var p:PlayerData in room_data.players){
				player_list[p.player_id] = p;
			}
			desk_number = room_data.desks.length;
			for (var i:int = 0; i<desk_number; i++){
				desk_list[i] = new Desk(room_data.desks[i]);
			}
		}
		

		public function updateToPlayerList(player:PlayerData):void
		{
			player_list[player.player_id] = player;
		}
		
		public function getPlayerFromPlayerList(id:int):PlayerData
		{
			return player_list[id];
		}
		
		public function removePlayer(player_id:int):void
		{
			delete player_list[player_id];
		}
		
		public function enterRoom(player:PlayerData):void
		{
			player_list[player.player_id] = player;
		}
		
		public function getDesk(desk_id:int):Desk
		{
			return desk_list[desk_id];
		}
		
		public function getPlayerDeskId(pid:int):int
		{
			for each(var d:Desk in desk_list){
				if (d.getSeat(pid)!=-1){
					return d.desk_id;
				}
			}
			return -1;
		}
		
		public function playerLeaveDesk(pid:int):void
		{
			for each(var d:Desk in desk_list){
				if (d.getSeat(pid)!=-1){
					d.leaveDesk(pid);
				}
			}
		}
		
		public function playerEnterDesk(pid:int, desk_id:int, seat:int):Boolean
		{
			if (desk_list[desk_id]!=null){
				return desk_list[desk_id].sitDown(pid, seat);
			}
			return false;
		}
		
		public function getPlayerList():Dictionary
		{
			return player_list;
		}
	}
}