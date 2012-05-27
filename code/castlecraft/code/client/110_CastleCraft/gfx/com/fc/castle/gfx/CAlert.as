package com.fc.castle.gfx
{
	import com.cell.ui.component.Alert;
	import com.fc.castlecraft.LanguageManager;

	public class CAlert
	{
		static public function showErrorNetwork() : Alert
		{
			return Alert.showAlertText(
				LanguageManager.getText("error.network"), 
				LanguageManager.getText("error"));
		}
	}
}