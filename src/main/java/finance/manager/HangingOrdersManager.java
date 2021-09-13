package finance.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import finance.Finance;
import finance.market.HangingOrder;
import finance.market.ItemElem;
import finance.market.OrderType;
import net.milkbowl.vault.economy.Economy;

public class HangingOrdersManager {
	private Economy econ = Finance.getEconomy();
	//this is the manager of orders that release by players.
	private Map<ItemElem,HangingOrder> PlayerOrders;
	public HangingOrdersManager() {
		setPlayerOrders(new HashMap<ItemElem,HangingOrder>());
	}
	public Map<ItemElem,HangingOrder> getPlayerOrders() {
		return PlayerOrders;
	}
	
	public void refreshItemStack(HangingOrder hOrder) {
		double sumReleaserPrice = hOrder.getPrice()*hOrder.getQuantity();
		double truePrice = 0;
		int nowQuantity = hOrder.getQuantity(), sumQuantity = 0;
		if(containsHanging(hOrder)) {
			if(hOrder.getOtype()==OrderType.BUY_IN_PLAYER) {
				for(Map.Entry<ItemElem, HangingOrder> e : PlayerOrders.entrySet()) {
					if(e.getValue().getGood().isSimilar(hOrder.getGood())) {
						double price = e.getValue().getPrice();
						int quantity = e.getValue().getQuantity();//good in ram
						if(nowQuantity-quantity<=0) {
							break;
						}else {
							
							nowQuantity -= quantity;
							
						}
						econ.withdrawPlayer(hOrder.getReleaser(), sumReleaserPrice);
					}
					
					
				}
			}else if(hOrder.getOtype()==OrderType.SELL_TO_PLAYER) {
				
			}
			
			
			
			
		}
	}
	
	@Deprecated
	public void setPlayerOrders(Map<ItemElem,HangingOrder> playerOrders) {
		PlayerOrders = playerOrders;
	}
	
	public boolean containsHanging(HangingOrder hOrder) {
		for(Map.Entry<ItemElem, HangingOrder> e : PlayerOrders.entrySet()) {
			if(e.getKey().getItemstack().isSimilar(hOrder.getGood())) {
				return true;
			}
		}
		return false;
	}
}
