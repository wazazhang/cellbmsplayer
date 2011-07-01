package Interface
{
	import Component.Card_Cpt;
	
	import mx.collections.ArrayCollection;
	import mx.core.IUIComponent;
	public interface ICardOwner
	{
		function cardClick(card:Card_Cpt):void 
	}
}