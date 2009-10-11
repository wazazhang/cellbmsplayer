package com.cell.rpg.ability;

import java.io.Serializable;
import java.util.ArrayList;

import com.g2d.studio.swing.AbilityPanel;

/**
 * 一组能力的集合，如果子类实现该接口，则在
 * {@link AbilityPanel}里就可以添加集合成员
 * @author WAZA
 * @see AbilityPanel
 */
public interface Abilities extends Serializable
{
	public void clearAbilities();
	
	public void addAbility(AbstractAbility element);

    public void removeAbility(AbstractAbility element);

    public AbstractAbility[] getAbilities();
	
	public <T extends AbstractAbility> T getAbility(Class<T> type);

	public <T extends AbstractAbility> ArrayList<T> getAbilities(Class<T> type) ;
	
	public Class<?>[] getSubAbilityTypes();
	
	public int size();
}
