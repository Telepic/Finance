package finance.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import finance.Finance;
import finance.exception.MouseClickStateException;
import finance.manager.MarketManager;
import finance.market.ItemElem;
import finance.market.Order;

public class MarketGUIListener implements Listener{
	private MarketManager mManager;
	public MarketGUIListener() {
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) throws MouseClickStateException {
		this.mManager = Finance.getmManager();
		if (event.getWhoClicked() instanceof Player == false) { return;} 
		Player p = (Player)event.getWhoClicked();
		if(mManager.checkTitle(event.getInventory().getTitle())){
			int MaxPage = mManager.getMarketByTitle(event.getInventory().getTitle()).getPages().size();
			int nowPage = MaxPage-1;
			Inventory temp = mManager.getMarketByTitle(event.getInventory().getTitle()).getPages().get(nowPage);
			if(event.getRawSlot() == 45 ){ //上一页
				p.closeInventory();
				if(nowPage-1>0) {
					nowPage -= 1;
				}else nowPage = 0;
	    	 	p.openInventory(mManager.getMarketByTitle(event.getInventory().getTitle()).getPages().get(nowPage));    
			}else if(event.getRawSlot() == 53 ){ //下一页
				p.closeInventory();
				if(nowPage+1<MaxPage) {
					nowPage += 1;
				}else nowPage = MaxPage-1;
	    	 	p.openInventory(mManager.getMarketByTitle(event.getInventory().getTitle()).getPages().get(nowPage));   
			}else {
				ItemStack clickItem = temp.getItem(event.getRawSlot());
				ItemElem ie = mManager.getMarketByTitle(event.getInventory().getTitle()).getItemElemByLoreItem(clickItem);
				if(event.isLeftClick()) {
					Order maBuyOrder = new Order(ie, null, p, mManager.getMarketByTitle(event.getInventory().getTitle()));
					Finance.getGsManager().getOrders().put(p, maBuyOrder);
					p.sendMessage("§a订单创建成功！您将购买" + clickItem.getType().name() +
							"\n§c请输入购买数量（输入0取消订单）");
					p.closeInventory();
				}else if (event.isRightClick()) {
					Order maSellOrder = new Order(ie, p, null, mManager.getMarketByTitle(event.getInventory().getTitle()));
					Finance.getGsManager().getOrders().put(p, maSellOrder);
					p.sendMessage("§a订单创建成功！您将出售" + clickItem.getType().name() +
							"\n§c请输入出售数量（输入0取消订单）");
					p.closeInventory();
				}else {
					p.closeInventory();
					p.openInventory(temp);
				}
			}
			event.setCancelled(true);
			p.updateInventory();
		}
	}
}
