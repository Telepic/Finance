package finance.listener;

import java.util.ArrayList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import finance.Finance;
import finance.market.Market;
import finance.market.Order;
import finance.market.OrderType;

public class AdministratorAsyncChatListener implements Listener {
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerMarketRemove(AsyncPlayerChatEvent e) {
		if(!Finance.getGsManager().getMarketRemoveList().isEmpty()) {
			if(Finance.getGsManager().getMarketRemoveList().containsKey(e.getPlayer())) {
				e.setCancelled(true);
				if(e.getMessage().equalsIgnoreCase("confirm")) {
					Market temp = Finance.getGsManager().getMarketRemoveList().get(e.getPlayer());
					temp.clear();
					Finance.getmManager().Markets.remove(temp.getDisplayName());
					Finance.getGsManager().getMarketRemoveList().remove(e.getPlayer());
					e.getPlayer().sendMessage("§b删除成功!");
				}else if (e.getMessage().equalsIgnoreCase("cancel")) {
					Finance.getGsManager().getMarketRemoveList().remove(e.getPlayer());
					e.getPlayer().sendMessage("§b您已取消删除!");
					return;
				}else {
					e.getPlayer().sendMessage("§c您的输入有误，输入confirm确认，输入cancel取消");
				}
			}else return;
		}else if(!Finance.getGsManager().getOrders().isEmpty()) {
			if(Finance.getGsManager().getOrders().containsKey(e.getPlayer())) {
				Order o = Finance.getGsManager().getOrders().get(e.getPlayer());
				e.setCancelled(true);
				if(e.getMessage().equalsIgnoreCase("0")) {
					Finance.getGsManager().getOrders().remove(e.getPlayer());
					e.getPlayer().sendMessage("§b订单已取消");
					return;
				}
				if(!o.isConfirm()) {
					int quantity = 0;
					try {
						quantity = Integer.valueOf(e.getMessage());
					}catch(NumberFormatException ex) {
						e.getPlayer().sendMessage("§c您的输入有误，请输入整数！或者输入0取消");
						return;
					}
					if(o.getOtype()==OrderType.SELL_TO_SERVER) {//玩家出售，系统购买 (系统增加)
						o.setQuantity(quantity);
						o.setPrice(o.getGood().getSumPrice(quantity));
						e.getPlayer().sendMessage("§a您将要出售 " + o.getGood().getItemstack().getType().name()
								+ "\n§a数量为 " + Math.abs(quantity) + "§a件"
								+ "\n§a共计获得 " + o.getPrice() 
								+ "\n§c请输入1确认，输入0取消订单");
						o.setConfirm(true);
					}else if(o.getOtype()==OrderType.BUY_IN_SERVER) {//玩家购买，系统出售
						o.setQuantity(quantity);
						quantity=0-quantity;
						o.setPrice(o.getGood().getSumPrice(quantity));
						e.getPlayer().sendMessage("§a您将要购买 " + o.getGood().getItemstack().getType().name()
								+ "\n§a数量为 " + Math.abs(quantity) + "§a件"
								+ "\n§a共计花费 " + o.getPrice() 
								+ "\n§c请输入1确认，输入0取消订单");
						o.setConfirm(true);
					}else {
						//玩家与玩家的订单//未开发
					}
				}else {
					if(e.getMessage().equalsIgnoreCase("0")) {
						Finance.getGsManager().getOrders().remove(e.getPlayer());
						e.getPlayer().sendMessage("§b订单已取消");
						return;
					}else if(e.getMessage().equalsIgnoreCase("1")){						
						if(o.getBuyer()==null) {//玩家出售，系统购买 (系统增加) 
							int quantity_in_player = 0;
							for(ItemStack item : e.getPlayer().getInventory().getContents()) {
								if(item.isSimilar(o.getGood().getItemstack())) {
									quantity_in_player+=item.getAmount();
								}
							}if(quantity_in_player<o.getQuantity()) {
								e.getPlayer().sendMessage("§c您背包中没有足够多的物品！");
								Finance.getGsManager().getOrders().remove(e.getPlayer());
								return;
							}else {
								for(ItemStack item : e.getPlayer().getInventory().getContents()) {
									if(item.isSimilar(o.getGood().getItemstack())) {
										item.setAmount(0);
									}
								}
								Finance.getEconomy().depositPlayer(e.getPlayer(), o.getPrice());		
								o.getMarket().refresh(o.getGood());
								e.getPlayer().sendMessage("§a交易成功！");
							}
						}else if(o.getOtype()==OrderType.BUY_IN_SERVER) {//玩家购买，系统出售
							int free = 0;
							for(ItemStack item : e.getPlayer().getInventory().getContents()) {
								if(item != null) {
									free++;
								}
							}free=36-free;
							if(o.getGood().getItemstack().getMaxStackSize()*free<o.getQuantity()) {
								e.getPlayer().sendMessage("§c你的背包容量不够，请清理背包后重试！");
								Finance.getGsManager().getOrders().remove(e.getPlayer());
								return;
							}
							if(o.getPrice()>Finance.getEconomy().getBalance(e.getPlayer())) {
								e.getPlayer().sendMessage("§c您的余额不足！");
								Finance.getGsManager().getOrders().remove(e.getPlayer());
							}else if(o.getGood().getNow_quantity()-o.getQuantity()<0) {
								e.getPlayer().sendMessage("§c市场存量不足！");
								Finance.getGsManager().getOrders().remove(e.getPlayer());
							}else {
								int sum = 0;
								ArrayList<ItemStack> items = new ArrayList<ItemStack>();
								for(ItemStack item:e.getPlayer().getInventory().getContents()) {
									if(item != null) {
										continue;
									}
									if(o.getQuantity()-sum>o.getGood().getItemstack().getMaxStackSize()) {
										ItemStack temp = new ItemStack(o.getGood().getItemstack());
										temp.setAmount(o.getGood().getItemstack().getMaxStackSize());
										items.add(temp);
										sum += o.getGood().getItemstack().getMaxStackSize();
									}else{
										ItemStack temp = new ItemStack(o.getGood().getItemstack());
										temp.setAmount(o.getQuantity()-sum);
										items.add(temp);
										break;
									}
								}
								ItemStack[] plin = (ItemStack[])items.toArray(new ItemStack[items.size()]); 
								e.getPlayer().getInventory().addItem(plin);
								o.getMarket().refresh(o.getGood());
								Finance.getEconomy().withdrawPlayer(e.getPlayer(), o.getPrice());
								e.getPlayer().sendMessage("§a交易成功！");
							}
						}
					}
				}
			}else return;
		}else return;
	}
}
