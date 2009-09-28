package com.cell.rpg.ability;

import java.io.Serializable;

import com.g2d.annotation.Property;


public class AbilitySceneNPCTeam extends AbilitiesVector
{
	private static final long serialVersionUID = 1L;
	
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				AbilitySceneNPCTeamNode.class,
		};
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (AbilitySceneNPCTeamNode a : getAbilities(AbilitySceneNPCTeamNode.class)) {
			if (a.xls_primary_key!=null) {
				sb.append(a.xls_primary_key.desc+",");
			}else{
				sb.append("null,");
			}
		}
		return sb.toString();
	}
	
//	public static void main(String[] args)
//	{
//		AbilitySceneNPCTeam team = new AbilitySceneNPCTeam();
//		if (team instanceof Abilities)
//		{
//		System.out.println("ok");	
//		}
//	}
}
