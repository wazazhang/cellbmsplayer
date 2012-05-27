package com.fc.castle.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.cell.CUtil;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.SpriteObject;
import com.cell.gameedit.output.OutputXml;
import com.cell.reflect.IObjectStringParser;
import com.cell.reflect.Parser;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLTableRow;
import com.cell.sql.util.SQLUtil;
import com.cell.util.StringUtil;
import com.cell.xls.XLSRowFactory.ObjectClassFactory;
import com.cell.xls.XLSUtil;
import com.fc.castle.data.ItemData;
import com.fc.castle.data.ItemDatas;
import com.fc.castle.data.ItemDrop;
import com.fc.castle.data.ItemDrops;
import com.fc.castle.data.ShopItem;
import com.fc.castle.data.ShopItems;
import com.fc.castle.data.SkillData;
import com.fc.castle.data.SkillDatas;
import com.fc.castle.data.SoldierData;
import com.fc.castle.data.SoldierDatas;
import com.fc.castle.data.template.BuffTemplate;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.data.template.ExpTab;
import com.fc.castle.data.template.FormualMap;
import com.fc.castle.data.template.GuideData;
import com.fc.castle.data.template.ItemTemplate;
import com.fc.castle.data.template.SkillTemplate;
import com.fc.castle.data.template.UnitTemplate;
import com.fc.castle.gs.config.GameConfig;
import com.fc.castle.gs.scene.ExploreSite;
import com.fc.castle.gs.scene.ExploreSiteChallange;
import com.fc.castle.gs.scene.ExploreSiteRace;
import com.fc.castle.gs.scene.ExploreSiteRandom;
import com.fc.castle.gs.scene.ExploreSiteUnknow;
import com.fc.castle.gs.scene.ExploreSiteVillage;


/**
 * 管理由XLS读入的数据
 * @author zhangwaza
 *
 */
public class DataManager 
{
	final static protected Logger log = LoggerFactory.getLogger("DataManager");
	
	private static DataManager instance;

	public static DataManager getInstance() {
		return instance;
	}
	
	public static DataManager init(
			InputStream xls_config, 
			InputStream xls_unit_template, 
			InputStream xls_skill_template,
			InputStream xls_item_template,
			InputStream xls_event_template,
			InputStream xls_buff_template,
			InputStream xls_exp,
			InputStream xls_guide,
			InputStream scene_xml) throws Exception 
	{
		new DataManager(xls_config, 
				xls_unit_template, 
				xls_skill_template,
				xls_item_template,
				xls_event_template, 
				xls_buff_template,
				xls_exp,
				xls_guide,
				scene_xml);
		return instance;
	}
	
//	------------------------------------------------------------------------------------
	
	private Map<String, String> 				enum_map;
	
	private FormualMap							formual_map;
	private Map<Integer,Map<Integer, Float>> 	formual_map_valuse;
	
	private Map<Integer, UnitTemplate> 			template_units;
	private Map<Integer, SkillTemplate> 		template_skills;
	private Map<Integer, ItemTemplate> 			template_items;
	private Map<Integer, EventTemplate>			template_events;
	private Map<Integer, BuffTemplate>			template_buffs;
	
	private Map<Integer, GuideData>				guidedatas;
	
	private OutputXmlDir						res_scene;
	
	private HashMap<String, ExploreSite>		explore_list;

	private HashMap<Integer, ExpTab> exptab_map = new HashMap<Integer, ExpTab>();

	public DataManager(
			InputStream xls_config, 
			InputStream xls_unit_template, 
			InputStream xls_skill_template,
			InputStream xls_item_template,
			InputStream xls_event_template,
			InputStream xls_buff_template,
			InputStream xls_exp,
			InputStream xls_guide,
			InputStream scene_xml) throws Exception
	{
		Parser.registerObjectStringParser(SoldierDatas.class,	new SoldiersParser());
		Parser.registerObjectStringParser(SkillDatas.class,		new SkillsParser());
		Parser.registerObjectStringParser(ItemDatas.class,		new ItemsParser());
		Parser.registerObjectStringParser(ItemDrops.class,		new ItemDropsParser());
		Parser.registerObjectStringParser(ShopItems.class, 		new ShopItemsParser());
		
		System.out.println("load : " + xls_config);
		
//		InputStream xlsConfig = CIO.getInputStream(xls_config);
		Workbook bookConfig = Workbook.getWorkbook(xls_config);
		try 
		{
			initEnumMap(bookConfig);
			
			initFormualMap(bookConfig);

			this.template_units = initTemplates(
					xls_unit_template, 
					"UnitTemplate",
					UnitTemplate.class);
			
			this.template_skills = initTemplates(
					xls_skill_template, 
					"SkillTemplate",
					SkillTemplate.class);
			
			this.template_items = initTemplates(
					xls_item_template, 
					"ItemTemplate",
					ItemTemplate.class);
			
			this.template_events = initTemplates(
					xls_event_template, 
					"EventTemplate",
					EventTemplate.class);
			
			this.template_buffs = initTemplates(
					xls_buff_template, 
					"BuffTemplate", 
					BuffTemplate.class);
			
			initExpTab(xls_exp);

			initConfig(bookConfig);
			
		} finally {
			bookConfig.close();
		}

		// scene and world
		
		this.guidedatas = initGuideBattleData(xls_guide);
		
		DataManager.instance = this;
		
		this.res_scene = new OutputXmlDir(scene_xml);
		
		this.explore_list = initExploreList();

		
	}
	
	private void initExpTab(InputStream xls_exp) throws Exception
	{
	
			Workbook rwb = Workbook.getWorkbook(xls_exp);
			Sheet st = rwb.getSheet("exp");
			int c = st.getColumns();
			int r = st.getRows();
			String table[][] = new String[r][c];
			for (int i = 0; i<r; i++){
				for (int j = 0; j<c; j++){
					Cell cell = st.getCell(j, i);
					table[i][j] = cell.getContents().trim();
				}
			}
			rwb.close();
			int exp = 0;
			
			if (table!=null){
				for (int i = 1; i<table.length; i++){
					ExpTab set = new ExpTab();
					set.level = Integer.parseInt(table[i][0]);
					set.exp_pre = exp;
					exp += Double.parseDouble(table[i][1]);
					set.exp_next = exp;
					exptab_map.put(set.level, set);
				}
			}
	
	}
	
	private void initConfig(Workbook work_book) throws Exception
	{
		Map<String, String> cfg_map = XLSUtil.readMap(work_book, "GameConfig", 0, "A", "B");
		Properties pop = new Properties();
		pop.putAll(cfg_map);
		GameConfig.load(GameConfig.class, pop, true);
	}
	
	
	
	private void initEnumMap(Workbook work_book) throws Exception
	{
		String[][] e_map = XLSUtil.readArray2D(work_book, "Enum", 0, "A", "E");
		enum_map = new HashMap<String, String>();
		System.out.println("init enums : " + enum_map.size());
		System.out.println("------------------------------");
		for (int i=0; i<e_map.length; i++) {
			{
				String k1 = e_map[i][0];
				String v1 = e_map[i][1];
				if (k1.isEmpty() || v1.isEmpty()) {
					if (!k1.isEmpty()) {
						System.out.println(k1);
					}
				} else {
					System.out.println(k1 + "\t= " + v1);
					if (enum_map.containsKey(k1) && !v1.equals(enum_map.get(k1))) {
						throw new Exception("XLS Enum Error : " +
								"conflict key value \""+k1+"\" = \""+v1+"\" at row " + (i+1));
					}
					enum_map.put(k1, v1);
				}
			}
			{
				String k2 = e_map[i][3];
				String v2 = e_map[i][4];
				if (k2.isEmpty() || v2.isEmpty()) {
					if (!k2.isEmpty()) {
						System.out.println(k2);
					}
				} else {
					System.out.println(k2 + "\t= " + v2);
					if (enum_map.containsKey(k2) && !v2.equals(enum_map.get(k2))) {
						throw new Exception("XLS Enum Error : " +
								"conflict key value \""+k2+"\" = \""+v2+"\" at row " + (i+1));
					}
					enum_map.put(k2, v2);
				}
			}
		}
		System.out.println("------------------------------");
	}

	private void initFormualMap(Workbook work_book) throws Exception
	{
		String[][] s_map = XLSUtil.readArray2D(work_book, "Enum", 0, "G", "M");
		System.out.println("init formual map : " + s_map.length);
		System.out.println("------------------------------");
		for (int r=0; r<s_map.length; r++) {
			if (!s_map[r][0].isEmpty()) {
				for (int c=0; c<s_map[r].length; c++) {
					System.out.print("| " + s_map[r][c] + "\t");
				}
				System.out.println();
			}
		}
		System.out.println("------------------------------");
		
		formual_map = new FormualMap();
		
		ArrayList<float[]> formual_list = new ArrayList<float[]>();
		
		ArrayList<String> defenseTypes = new ArrayList<String>();
		for (int di=0; di<s_map[0].length; di++) {
			defenseTypes.add(s_map[0][di]);
		}
		ArrayList<String> attackTypes = new ArrayList<String>();
		for (int ai=0; ai<s_map.length; ai++) {
			attackTypes.add(s_map[ai][0]);
		}
		
		formual_map_valuse = new HashMap<Integer, Map<Integer,Float>>();
		for (int r=1; r<s_map.length; r++) 
		{
			String attackChar = attackTypes.get(r);
			if (!attackChar.isEmpty() && enum_map.containsKey(attackChar)) 
			{
				int attackType = Parser.parseInteger(enum_map.get(attackChar));
				HashMap<Integer, Float> subMap = new HashMap<Integer, Float>();
				for (int c=1; c<s_map[r].length; c++)
				{
					if (!s_map[r][c].isEmpty()) 
					{
						String defenseChar = defenseTypes.get(c);
						if (!defenseChar.isEmpty() && enum_map.containsKey(defenseChar)) 
						{
							int defenseType = Parser.parseInteger(enum_map.get(defenseChar));
							float rate = Parser.parseFloat(s_map[r][c]);
							subMap.put(defenseType, rate);
							formual_list.add(new float[]{attackType, defenseType, rate});
							System.out.println(
									"["+attackChar+"("+attackType+")]" +
									"["+defenseChar+"("+defenseType+")]" +
									" = " + rate);
						}
					}
				}
				formual_map_valuse.put(attackType, subMap);
			}
		}
		formual_map.ammor_map = new float[formual_list.size()][];
		for (int i= 0; i<formual_map.ammor_map.length; i++) {
			formual_map.ammor_map[i] = formual_list.get(i);
		}
	}
	
	private <T extends SQLTableRow<Integer>> Map<Integer, T> initTemplates(InputStream input, String sheet, Class<T> type) throws Exception 
	{
//		InputStream input = CIO.getInputStream(xls);
		Workbook book = Workbook.getWorkbook(input);
		try {
			Map<Integer, T> ret = readXLStable(book, sheet, Integer.class, type);
			SQLUtil.printTable(type, ret.values(), System.out);
			return ret;
		} finally {
			book.close();
		}
	}

	private Map<Integer, GuideData> initGuideBattleData(InputStream input) throws Exception 
	{
		Workbook book = Workbook.getWorkbook(input);
		try {
			Map<Integer, GuideData> ret = readXLStable(book, "GuideData", Integer.class, GuideData.class);
			SQLUtil.printTable(GuideData.class, ret.values(), System.out);
			return ret;
		} finally {
			book.close();
		}
	}
	
	public GuideData getGuideData(int step)
	{
		return guidedatas.get(step);
	}
	
//	----------------------------------------------------------------------------

	
	
	public UnitTemplate getUnitTemplate(int type)
	{
		return template_units.get(type);
	}
	
	public Collection<UnitTemplate> getAllUnits()
	{
		return template_units.values();
	}

	public SoldierDatas createSoldiers(int[] types)
	{
		return createSoldiers(Arrays.asList(getTemplates(UnitTemplate.class, types)));
	}
	
	public SoldierDatas createSoldiers(Collection<UnitTemplate> units)
	{
		SoldierDatas soldiers = new SoldierDatas();
		for (UnitTemplate ut : units) {
			soldiers.datas.add(new SoldierData(ut.getPrimaryKey()));
		}
		return soldiers;
	}
	
//	--------------------------------------------------------------------------------
	
	public SkillTemplate getSkillTemplate(int type)
	{
		return template_skills.get(type);
	}
	
	public Collection<SkillTemplate> getAllSkills()
	{
		return template_skills.values();
	}
	
	public SkillDatas createSkills(int[] types)
	{
		return createSkills(Arrays.asList(getTemplates(SkillTemplate.class, types)));
	}
	
	public SkillDatas createSkills(Collection<SkillTemplate> skills)
	{
		SkillDatas ret = new SkillDatas();
		for (SkillTemplate st : skills) {
			ret.datas.add(new SkillData(st.getPrimaryKey()));
		}
		return ret;
	}

//	--------------------------------------------------------------------------------
	
	public ItemTemplate getItemTemplate(int type)
	{
		return template_items.get(type);
	}
	
	public Collection<ItemTemplate> getAllItems()
	{
		return template_items.values();
	}
	
	public ItemDatas createItems(int[] types)
	{
		return createItems(Arrays.asList(getTemplates(ItemTemplate.class, types)));
	}
	
	public ItemDatas createItems(Collection<ItemTemplate> items)
	{
		ItemDatas ret = new ItemDatas();
		for (ItemTemplate st : items) {
			ret.datas.add(new ItemData(st.getPrimaryKey(), 1));
		}
		return ret;
	}
	
//	--------------------------------------------------------------------------------

	public BuffTemplate getBuffTemplate(int type) 
	{
		return template_buffs.get(type);
	}

	public BuffTemplate[] getBuffTemplates(int[] types)
	{
		BuffTemplate[] ret = new BuffTemplate[types.length];
		for (int i=types.length-1; i>=0; --i) {
			ret[i] = template_buffs.get(types[i]);
			if (ret[i] == null) {
				return null;
			}
		}
		return ret;
	}
	
	public LinkedHashMap<Integer, BuffTemplate> getAllBuffTemplate()
	{
		return new LinkedHashMap<Integer, BuffTemplate>(template_buffs);
	}
	
//	--------------------------------------------------------------------------------
	
	public EventTemplate getEventTemplate(int type)
	{
		return template_events.get(type);
	}
	
	public EventTemplate[] getEventTemplates(int[] types)
	{
		return getTemplates(EventTemplate.class, types);
	}
	
	
	
//	--------------------------------------------------------------------------------

//	public static HashMap<Integer, ExploreSite> initExploreList(WorldSet scene_set){
//
//		HashMap<Integer, ExploreSite> el = new HashMap<Integer, ExploreSite>();
//		
//		for (WorldSet.SpriteObject spr:scene_set.Sprs.values()){
//			SprData sd = getSprData(spr);
//			if (sd.explore_type!=null && !sd.explore_type.isEmpty()){
//				if (el.get(sd.id)==null){
//					if (sd.explore_type.equals("race")){
//						ExploreSiteRace es = new ExploreSiteRace(sd.id, sd.sub_type);
//						es.UnitName = spr.UnitName;
//						es.map_id = sd.map_id;
//						el.put(sd.id, es);
//					}else if (sd.explore_type.equals("village")){
//						ExploreSiteVillage es = new ExploreSiteVillage(sd.id);
//						es.UnitName = spr.UnitName;
//						es.map_id = sd.map_id;
//						el.put(sd.id, es);
//					}else if (sd.explore_type.equals("random")){
//						ExploreSiteRandom es = new ExploreSiteRandom(sd.id, sd.sub_type);
//						es.UnitName = spr.UnitName;
//						es.map_id = sd.map_id;
//						el.put(sd.id, es);
//					}else if (sd.explore_type.equals("unknow")){
//						ExploreSiteUnknow es = new ExploreSiteUnknow(sd.id, 1);
//						es.UnitName = spr.UnitName;
//						es.map_id = sd.map_id;
//						el.put(sd.id, es);
//					}else if (sd.explore_type.equals("challange")){
//						ExploreSiteChallange es = new ExploreSiteChallange(sd.id, 1);
//						es.UnitName = spr.UnitName;
//						es.map_id = sd.map_id;
//						el.put(sd.id, es);
//					}
//				}else{
//					// ID重复
//				}
//			}
//		}
//		return el;
//	}
	
	
//	public ExploreStates createExploreStates()
//	{
//		ExploreStates ess = new ExploreStates();
//		ess.states = new ExploreState[explore_list.size()];
//		int i = 0;
//		for (ExploreSite es:explore_list.values()){
//			ExploreData ed = es.getExploreData();
//			ExploreState state = new ExploreState();
//			state.id = ed.id;
//			state.last_time = 0;
//			ess.states[i++] = state;
//		}
//		return ess;
//	}
	
	public ExploreSite getExploreSite(String unitName)
	{
		return explore_list.get(unitName);
	}
	
	
	public <T> T[] getTemplates(Class<T> compomentType, int[] types)
	{
		if (types != null) 
		{
			T[] ret = CUtil.newArray(compomentType, types.length);
			
			for (int i=0; i<types.length; i++) 
			{
				int type = types[i];
				
				if (compomentType.equals(UnitTemplate.class)) {
					ret[i] = compomentType.cast(getUnitTemplate(type));
				}
				else if (compomentType.equals(SkillTemplate.class)) {
					ret[i] = compomentType.cast(getSkillTemplate(type));
				}
				else if (compomentType.equals(ItemTemplate.class)) {
					ret[i] = compomentType.cast(getItemTemplate(type));
				}
				
				else if (compomentType.equals(EventTemplate.class)) {
					ret[i] = compomentType.cast(getEventTemplate(type));
				}
				else if (compomentType.equals(BuffTemplate.class)) {
					ret[i] = compomentType.cast(getBuffTemplate(type));
				}
				if (ret[i] == null) {
					return null;
				}
			}
			return ret;
		}
		return null;
	}
	
	

//	----------------------------------------------------------------------------
	
	/**获得［攻击方式］［护甲类型］的攻击加成*/
	public float getAttackRate(int attackType, int defenseType)
	{
		return formual_map_valuse.get(attackType).get(defenseType);
	}
	
	public FormualMap getFormualMap() 
	{
		return formual_map;
	}
	
//	----------------------------------------------------------------------------

	
	
	public ExpTab getExpTab(int lv){
		return exptab_map.get(lv);
	}
	

//	----------------------------------------------------------------------------

	
	private <K, V extends SQLTableRow<?>> Map<K, V> readXLStable(Workbook work_book, String sheet, Class<K> kt, Class<V> vt) throws Exception
	{
		System.out.println("init " + vt.getSimpleName());
		Map<K, V> ret = XLSUtil.readTable(work_book,
				sheet, 1, 1, 1,
				SQLColumn.getSQLColumns(vt),
				kt, vt, 
				new CCXLSFactory<V>(vt));
		System.out.println("	size : " + ret.size());
		return ret;
	}

	private class CCXLSFactory<V extends SQLTableRow<?>> extends ObjectClassFactory<V>
	{
		public CCXLSFactory(Class<V> type) {
			super(type);
		}
		
		@Override
		public String convertField(Sheet st, SQLColumn column, String text) 
		{
			Field field = column.getLeafField();
			
			if (field.getType().isArray()) 
			{
				String[] splits = CUtil.splitString(text, ",", true);
				for (int i=0; i<splits.length; i++) {
					String convert = enum_map.get(splits[i]);
					if (convert != null) {
						splits[i] = convert;
					}
				}
				return CUtil.arrayToString(splits);
			} 
			else if (field.getType().isPrimitive())
			{
				String convert = enum_map.get(text);
				if (convert != null) {
					return convert;
				}
			}
			return null;
		}
	}
	
	/**
	 * 解析格式 
	 * 1, 2, 3, ...
	 * @author zhangwaza
	 */
	private class SoldiersParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			int[] types = StringUtil.getIntegerArray(str, "\\s*,\\s*");
			if (types == null || types.length == 0) {
				return new SoldierDatas();
			}
			return createSoldiers(types);
		}
	}
	
	/**
	 * 解析格式 
	 * 1, 2, 3, ...
	 * @author zhangwaza
	 */
	private class SkillsParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			int[] types = StringUtil.getIntegerArray(str, "\\s*,\\s*");
			if (types == null || types.length == 0) {
				return new SkillDatas();
			}
			return createSkills(types);
		}
	}

	/**
	 * 解析格式 
	 * 100102 x1, 100103 x2, 100104 x3, ...
	 * @author zhangwaza
	 */
	private class ItemsParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (str.isEmpty()) {
				return new ItemDatas();
			}
			String[] strs = str.split("\\s*,\\s*");
			if (strs == null || strs.length == 0) {
				return new ItemDatas();
			}
			ItemDatas ret = new ItemDatas();
			for (int i = strs.length - 1; i >= 0; --i) {
				String[] tc = strs[i].split("\\s+");
				int type  = Integer.parseInt(tc[0]);
				int count = Integer.parseInt(tc[1].substring(1));
				ret.datas.add(new ItemData(type, count));
				if (getItemTemplate(type) == null) {
					return null;
				}
			}
			return ret;
		}
	}
	
	/**
	 * 解析格式 
	 * 100102 x1 100%, 100103 x1 50%, ...
	 * @author zhangwaza
	 */
	private class ItemDropsParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (str.isEmpty()) {
				return new ItemDrops();
			}
			String[] strs = str.split("\\s*,\\s*");
			if (strs == null || strs.length == 0) {
				return new ItemDrops();
			}
			ItemDrops ret = new ItemDrops();
			for (int i = strs.length - 1; i >= 0; --i) {
				String[] tc = strs[i].split("\\s+");
				int type  = Integer.parseInt(tc[0]);
				int count = Integer.parseInt(tc[1].substring(1));
				float pct = Float.parseFloat(tc[2].substring(0, tc[2].length()-1));
				ret.datas.add(new ItemDrop(type, count, pct));
				if (getItemTemplate(type) == null) {
					return null;
				}
			}
			return ret;
		}
	}
	
	/**
	 * 解析格式 
	 * 100102 x1, 100103 x2, 100104 x3, ...
	 * @author zhangwaza
	 */
	private class ShopItemsParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (str.isEmpty()) {
				return new ShopItems();
			}
			String[] strs = str.split("\\s*,\\s*");
			if (strs == null || strs.length == 0) {
				return new ShopItems();
			}
			ShopItems ret = new ShopItems();
			for (int i = strs.length - 1; i >= 0; --i) {
				String[] tc = strs[i].split("\\s+");
				int type  = Integer.parseInt(tc[0]);
				int count = Integer.parseInt(tc[1].substring(1));
				ret.datas.add(new ShopItem(type, count));
				if (getItemTemplate(type) == null) {
					return null;
				}
			}
			return ret;
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	public WorldSet getSceneSet(String name)
	{
		return res_scene.getWorldTable().get(name);
	}

	/**
	 * 如何将编辑器资源解析成单位
	 * @author WAZA
	 */
	private class OutputXmlDir extends OutputXml
	{
		public OutputXmlDir(InputStream is) throws Exception 
		{
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(is);
				this.init(doc);
			} catch (SAXParseException err) {
				System.out.println("** Parsing error" + ", line "
						+ err.getLineNumber() + ", uri " + err.getSystemId());
				System.out.println(" " + err.getMessage());
				throw err;
			} catch (SAXException e) {
				Exception x = e.getException();
				((x == null) ? e : x).printStackTrace();
				throw e;
			}
		}

		public byte[] loadRes(String path, AtomicReference<Float> percent) {
			return null;
		}
	
	}


	private HashMap<String, ExploreSite> initExploreList() throws Exception
	{
		WorldSet ws = getSceneSet("world");
		
		HashMap<String, ExploreSite> ret = new HashMap<String, ExploreSite>();
		
		for (WorldSet.SpriteObject spr:ws.Sprs.values())
		{
			if (ret.containsKey(spr.UnitName)) {
				throw new Exception("Init Scene Error : duplicate unit name as ["+spr.UnitName+"]");
//				log.error("Init Scene Error : duplicate unit name as ["+spr.UnitName+"]");
			}else{
				ExploreSite es = createExploreSite(spr);
				if (es != null) {
					ret.put(spr.UnitName, es);
				}
			}
		}
		ws = getSceneSet("home");
		for (WorldSet.SpriteObject spr:ws.Sprs.values())
		{
			if (ret.containsKey(spr.UnitName)) {
				throw new Exception("Init Scene Error : duplicate unit name as ["+spr.UnitName+"]");
//				log.error("Init Scene Error : duplicate unit name as ["+spr.UnitName+"]");
			}else{
				ExploreSite es = createExploreSite(spr);
				if (es != null) {
					ret.put(spr.UnitName, es);
				}
			}
		}
		return ret;
	}

	private ExploreSite createExploreSite(SpriteObject spr) throws Exception
	{
		com.cell.util.Properties unitData = new com.cell.util.Properties(spr.Data, "=");
		
		String explore_type = unitData.getString("explore_type");
		
		if (explore_type != null)
		{
			if ("race".equalsIgnoreCase(explore_type)) {
				return new ExploreSiteRace(spr.UnitName, unitData);
			}
			
			if ("challange".equalsIgnoreCase(explore_type)) {
				return new ExploreSiteChallange(spr.UnitName, unitData);
			}
			
			if ("random".equalsIgnoreCase(explore_type)) {
				return new ExploreSiteRandom(spr.UnitName, unitData);
			}
			
			if ("village".equalsIgnoreCase(explore_type)) {
				return new ExploreSiteVillage(spr.UnitName, unitData);
			}
			
			return new ExploreSiteUnknow(spr.UnitName, unitData);
		}
		return null;
	}
	

	
	public Map<String, ExploreSite> getAllExploreSite()
	{
		return explore_list;
	}


}
