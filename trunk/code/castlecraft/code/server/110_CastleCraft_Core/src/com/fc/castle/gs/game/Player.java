package com.fc.castle.gs.game;

import java.sql.Date;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CMath;
import com.cell.CUtil;
import com.fc.castle.data.ExploreData;
import com.fc.castle.data.ExploreState;
import com.fc.castle.data.ItemData;
import com.fc.castle.data.ItemDatas;
import com.fc.castle.data.ItemDrop;
import com.fc.castle.data.ItemDrops;
import com.fc.castle.data.PlayerData;
import com.fc.castle.data.SkillData;
import com.fc.castle.data.SkillDatas;
import com.fc.castle.data.SoldierData;
import com.fc.castle.data.SoldierDatas;
import com.fc.castle.data.message.Messages.BattleStartResponse;
import com.fc.castle.data.message.Messages.CommitBattleResultRequest;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.data.template.ExpTab;
import com.fc.castle.data.template.ItemTemplate;
import com.fc.castle.data.template.SkillTemplate;
import com.fc.castle.data.template.UnitTemplate;
import com.fc.castle.gs.scene.ExploreSite;
import com.fc.castle.service.DataManager;
import com.fc.castle.service.PersistanceManager;

public class Player
{
	private static final Logger log = LoggerFactory.getLogger(GameManager.class);
	
	private PlayerData player;

//	private Scene scene;
	
	public Player(PlayerData data)
	{
		this.player = data;
		if (player == null) {
			throw new NullPointerException("Player is Null");
		}
	}
	
	public Player(int player_id)
	{
		this.player = PersistanceManager.getInstance().getPlayer(player_id);
		if (player == null) {
			throw new NullPointerException("Player["+player_id+"] is Null");
		}
	}
	
	public int getID() {
		return player.player_id;
	}
	
	public PlayerData getData() {
		return player;
	}

	@Override
	public String toString() 
	{
		return player.player_name+"("+player.player_id+")";
	}
	
//	-------------------------------------------------------------------------------------------------------------
	
	public ExploreState getOrCreateExploreState(String scene_unit_name)
	{
		if (player.exploreStates == null) {
			player.exploreStates = new HashMap<String, ExploreState>();
		}
		ExploreState es = player.exploreStates.get(scene_unit_name);
		if (es == null) {
			ExploreSite site = DataManager.getInstance().getExploreSite(scene_unit_name);
			if (site != null) {
				es = new ExploreState();
				es.UnitName = scene_unit_name;
				es.last_time = null;
				es.explore_count = 0;
				player.exploreStates.put(scene_unit_name, es);
			}
		}
		return es;
	}
	
	public ExploreState getExploreState(String scene_unit_name)
	{
		if (player.exploreStates == null) {
			return null;
		} else {
			return player.exploreStates.get(scene_unit_name);
		}
	}
	
	/**验证这些士兵是否能出征*/
	public boolean tryBattleSoldiers(SoldierDatas datas)
	{
		if (datas.datas.isEmpty()) {
			return true;
		}
		if (datas.datas.size() > player.battle_soldier_count) {
			return false;
		}
		for (SoldierData ssd : datas.datas) {
			SoldierData fsd = null;
			for (SoldierData dsd : player.soldiers.datas) {
				if (ssd.unitType == dsd.unitType) {
					fsd = dsd;
					break;
				}
			}
			if (fsd == null) {
				return false;
			}
		}
		return true;
	}
	
	/**验证这些技能是否能出征*/
	public boolean tryBattleSkills(SkillDatas datas)
	{
		if (datas.datas.isEmpty()) {
			return true;
		}
		if (datas.datas.size() > player.battle_skill_count) {
			return false;
		}
		for (SkillData ssd : datas.datas) {
			SkillData fsd = null;
			for (SkillData dsd : player.skills.datas) {
				if (ssd.skillType == dsd.skillType) {
					fsd = dsd;
					break;
				}
			}
			if (fsd == null) {
				return false;
			}
		}
		return true;
	}
	
	/**获取防守军队*/
	public SoldierDatas getDefenseSoldiers()
	{
		if (player.organizeDefense != null && player.organizeDefense.soldiers != null) {
			if (tryBattleSoldiers(player.organizeDefense.soldiers)) {
				return player.organizeDefense.soldiers;
			}
		}
		SoldierDatas ret = new SoldierDatas();
		int count = Math.min(player.battle_soldier_count, player.soldiers.datas.size());
		for (int i=0; i<count; i++) {
			SoldierData sd = player.soldiers.datas.get(i);
			if (sd != null) {
				ret.datas.add(sd);
			}
		}
		return ret;
	}
	
	/**获取防守技能*/
	public SkillDatas getDefenseSkills()
	{
		if (player.organizeDefense != null && player.organizeDefense.skills != null) {
			if (tryBattleSkills(player.organizeDefense.skills)) {
				return player.organizeDefense.skills;
			}
		}
		SkillDatas ret = new SkillDatas();
		int count = Math.min(player.battle_skill_count, player.skills.datas.size());
		for (int i=0; i<count; i++) {
			SkillData sd = player.skills.datas.get(i);
			if (sd != null) {
				ret.datas.add(sd);
			}
		}
		return ret;
	}
	
	
	public HashMap<String, ExploreData> getActiveSceneExplores()
	{
		if (player.exploreStates == null) {
			player.exploreStates = new HashMap<String, ExploreState>(1);
		}
		HashMap<String, ExploreData> explores = new HashMap<String, ExploreData>();
		
		for (ExploreSite es : DataManager.getInstance().getAllExploreSite().values())
		{
			if (es.isVisible(player)) 
			{
				ExploreData ed = es.getData();
				EventTemplate et = makeExploreEvent(es) ;
				ExploreState est = player.exploreStates.get(es.getUnitName());
				if (est != null) {
					ed.last_time = est.last_time;
					ed.last_explorer = est.last_explorer;
				}
				ed.explore_name = et.name;
				ed.refreshTime = et.refreshTime;
				explores.put(es.getUnitName(), ed);
			}
		}
		
		return explores;
	}
	
	public EventTemplate makeExploreEvent(ExploreSite site) 
	{
		if (site.getEvents().length > 0) {
			ExploreState save = player.exploreStates.get(site.getUnitName());
			if (save != null) {
				return site.getEvents()[CMath.cycNum(0, save.explore_count, site.getEvents().length)];
			} else {
				return site.getEvents()[0];
			}
		}
		return null;
	}
	
	public void addExp(int value)
	{
		if (DataManager.getInstance().getExpTab(player.level+1)==null){
			return;
		}
		player.experience += value;
		ExpTab et = DataManager.getInstance().getExpTab(player.level);
		while (player.experience>=et.exp_next){
			player.level++;
			et = DataManager.getInstance().getExpTab(player.level);
			if (et==null){
				return;
			}
		}
//		if (DataManager.getInstance().getExpTab(player.level+1)==null){
//			player.exp = DataManager.getInstance().getExpTab(player.level).exp_pre;
//		}
//		ExpTab ct = DataManager.getInstance().getExpTab(player.level);
		player.cur_exp = (int) (player.experience-et.exp_pre);
		player.next_exp = et.exp_next-et.exp_pre;
	}
	
	public void addCoin(int value)
	{
		player.coin += value;
	}

	
	
	public ItemData getItem(int index) 
	{
		return player.items.datas.get(index);
	}
	
	
	public void addDropItems(ItemDrops drops)
	{
		if (drops != null) {
			for (ItemDrop drop : drops.datas) {
				ItemTemplate tt = DataManager.getInstance().getItemTemplate(drop.itemType);
				if (tt != null) {
					if (CUtil.getRandom(CUtil.getRandom(), 0, 100) <= drop.percent) {
						addItem(drop.itemType, drop.count);
						log.info("Player["+toString()+"] add drop item["+tt.toString()+"]");
					}
				} else {
					log.error("can not find drop item " + drop.itemType);
				}
			}
		}
	}
	
	public void addItems(ItemDatas datas)
	{
		if (datas != null) {
			for (ItemData data : datas.datas) {
				ItemTemplate tt = DataManager.getInstance().getItemTemplate(data.itemType);
				if (tt != null) {
					addItem(data.itemType, data.count);
					log.info("Player["+toString()+"] add an item["+tt.toString()+"]");
				} else {
					log.error("can not find item " + data.itemType);
				}
			}
		}
	}
	
	
	/**
	 * @param item
	 * @return 该道具位置
	 */
	public int addItem(int itemType, int count)
	{
		int size = player.items.datas.size();
		// 先堆叠
		for (int i=0; i<size; i++) {
			ItemData oitem = player.items.datas.get(i);
			if (oitem != null && oitem.itemType == itemType) {
				oitem.count += count;
				return i;
			}
		}
		// 找到空位插入
		for (int i=0; i<size; i++) {
			ItemData oitem = player.items.datas.get(i);
			if (oitem == null || oitem.count <= 0) {
				player.items.datas.set(i, new ItemData(itemType, count));
				return i;
			}
		}
		// 新建栏位
		player.items.datas.add(new ItemData(itemType, count));
		return player.items.datas.size()-1;
	}
	
	
	/**
	 * @param indexOfItem
	 * @param count
	 * @return <pre>
	 * 返回值等于count：使用正常，
	 * 返回值小于count：没有足够的道具
	 * 返回值等于0：使用失败，
	 */
	public int useItem(int indexOfItem, int count) 
	{
		if (indexOfItem < player.items.datas.size()) {
			ItemData oitem = player.items.datas.get(indexOfItem);
			if (oitem != null) {
				if (oitem.count >= count) {
					oitem.count -= count;
					useItemFunction(oitem, count);
					return count;
				} else {
					return oitem.count;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 道具功能
	 */
	private void useItemFunction(ItemData data, int count)
	{
		try {
			ItemTemplate tt = DataManager.getInstance().getItemTemplate(data.itemType);
			if (tt.getUnits != null) {
				for (SoldierData sd : tt.getUnits.datas) {
					addSoldier(sd);
				}
			}
			if (tt.getSkills != null) {
				for (SkillData sd : tt.getSkills.datas) {
					addSkill(sd);
				}
			}
		} catch (Exception e) {
			log.error("useItemFunction: " + e.getMessage(), e);
		}
	}
	
	public void addSoldier(SoldierData sd)
	{
		UnitTemplate ut = DataManager.getInstance().getUnitTemplate(sd.unitType);
		if (ut != null) {
			// 如果有同样的则合并
			for (int i = player.soldiers.datas.size() - 1; i >= 0; i--) {
				SoldierData src_sd = player.soldiers.datas.get(i);
				if (src_sd.unitType == sd.unitType) {
					player.soldiers.datas.set(i, sd);
					return;
				}
			}
			player.soldiers.datas.add(sd);
		}
	}
	
	public void addSkill(SkillData sd)
	{
		SkillTemplate st = DataManager.getInstance().getSkillTemplate(sd.skillType);
		if (st != null) {
			// 如果有同样的则合并
			for (int i = player.skills.datas.size() - 1; i >= 0; i--) {
				SkillData src_sd = player.skills.datas.get(i);
				if (src_sd.skillType == sd.skillType) {
					player.skills.datas.set(i, sd);
					return;
				}
			}
			player.skills.datas.add(sd);
		}
	}
	
	
	
	
	
	// TODO 以后版本需要对战报有效性做判断
	// playerdata.lastBattle
	// request.log
	//
	public boolean validateBattle(CommitBattleResultRequest request, BattleStartResponse response)
	{
		
		return true;
	}
}
