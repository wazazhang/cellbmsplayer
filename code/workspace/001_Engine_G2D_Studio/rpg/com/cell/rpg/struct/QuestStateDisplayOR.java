package com.cell.rpg.struct;

import java.util.ArrayList;
import java.util.Iterator;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbilitiesList;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.cell.rpg.quest.QuestState;
import com.g2d.annotation.Property;


@Property("任务显示状态，或")
public class QuestStateDisplayOR extends ArrayList<QuestStateDisplayOR.State> implements Abilities
{
	private static final long serialVersionUID = 1L;

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public void addAbility(AbstractAbility element) {
		if (element instanceof State) {
			super.add((State)element);
		}
	}
	
	@Override
	public void clearAbilities() {
		super.clear();
	}

	@Override
	public AbstractAbility[] getAbilities() {
		return super.toArray(new State[super.size()]);
	}

	@Override
	public <T> ArrayList<T> getAbilities(Class<T> type) {
		ArrayList<T> ret = new ArrayList<T>();
		if (State.class.isAssignableFrom(type)) {
			for (State st : this) {
				ret.add(type.cast(st));
			}
		}
		return ret;
	}

	@Override
	public int getAbilitiesCount() {
		return size();
	}

	@Override
	public <T> T getAbility(Class<T> type) {
		if (State.class.isAssignableFrom(type) && super.isEmpty()) {
			return type.cast(super.get(0));
		}
		return null;
	}

	@Override
	public void removeAbility(AbstractAbility element) {
		super.remove(element);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class[] { State.class };
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (State st : this) {
			sb.append("[" + st.show_in_quest_state.toTinyString() + "," + st.show_in_quest_id + "],");
		}
		return sb.toString();
	}
	
//	--------------------------------------------------------------------------------------------------------
	

	@Property("任务状态")
	public static class State extends AbstractAbility
	{
		@Property("只显示在接了某任务")
		public QuestState 	show_in_quest_state		= QuestState.NA;
		
		@Property("只显示在接了某任务ID")@PropertyAdapter(PropertyType.QUEST_ID)
		public int 			show_in_quest_id		= -1;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
}
