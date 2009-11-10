package com.cell.rpg.ability;



public class AbilitySceneNPCTeam extends AbilitiesVector
{
	private static final long serialVersionUID = 1L;
	
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				AbilitySceneNPCTeamNode.class,
		};
	}
}
