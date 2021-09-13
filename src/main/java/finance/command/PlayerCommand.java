package finance.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import finance.Finance;
import finance.market.HangingOrder;
import finance.market.Market;
import finance.market.MarketType;
import finance.market.OrderType;

public class PlayerCommand implements CommandExecutor{
	public PlayerCommand(Finance plugin) {
    }
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			//finance open
			if(args[0].equalsIgnoreCase("open")) {
				if(args.length != 2) {
					sender.sendMessage("§c/finance open [市场名] 打开名为[市场名]的市场");
					return true;
				}else{
					if(Finance.getmManager().checkName(args[1])) {
						Market temp = Finance.getmManager().getMarket(args[1]);
						if(!temp.opening) {
							sender.sendMessage("§c该市场已关闭。");
							return true;
						}
						p.closeInventory();
						p.openInventory(temp.getNowMarketGUI());
						sender.sendMessage("§a您已进入" + temp.getDisplayName());
						return true;
					}
				}
			}
			/* 
			 * this is the player trade command.
			 * 
			 * /finance buy [market] [price] [quantity]
			*/
			if(args[0].equalsIgnoreCase("buy")){
				if(!(sender instanceof Player)) {
					sender.sendMessage("§c只有玩家才能执行该指令");
				}else {
					if(Finance.getPerm().playerHas((Player) sender, "finance.buy")) {
						Market temp = null;
						if(!Finance.getmManager().checkName(args[1])) {
							sender.sendMessage("§c市场"+args[1]+"§c不存在");
							return true;
						}else {
							temp = Finance.getmManager().getMarket(args[1]);
							if(temp.getType().equals(MarketType.MAIN)) {
								sender.sendMessage("§c你不能在系统商城上架商品");
								return true;
							}
						}
						double price = 0;
						int quantity = 0;
						try {
							price = Double.valueOf(args[2]);
							quantity = Integer.valueOf(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("§c价格必须为数字且数量必须为整数！");
							return true;
						}
						if(Finance.getEconomy().getBalance(p)<(price*quantity)) {
							sender.sendMessage("§c你的钱不够你出价！");
							return true;
						}
						if(temp.getItemElem(p.getInventory().getItemInMainHand()).getNow_quantity()<quantity) {
							sender.sendMessage("§c市场上的货物数量不够！");
							return true;
						}
						//Other Conditions
						@SuppressWarnings("unused")
						HangingOrder hOrder = new HangingOrder(OrderType.BUY_IN_PLAYER, p, price, quantity, p.getInventory().getItemInMainHand(), temp);
						
					}
					else {
						sender.sendMessage("§c你没有权限这么做");
						return true;
					}
				}
				
			}
			//finance sell [market] [price] [quantity]
			if(args[0].equalsIgnoreCase("sell")) {
				
			}
			/*
			 * /finance cancel [market]
			 * Call back the order that was sent by player.
			 * This need limit the max type of player's order, because the gui's size is limited. 
			 * And I think this will be a better recharged way.(The true reason way I don't want to do this because of lazy.)
			 */
			if(args[0].equalsIgnoreCase("cancel")) {
				
			}
			//this command supply a way to show the index of the server.(need money to query)
			if(args[0].equalsIgnoreCase("query")) {
				
			}
			
//			if(!(sender instanceof Player)) {
//				sender.sendMessage("这个指令只能让玩家使用");
//				return false;
//			}
//			if(args.length > 0) {
//				sender.sendMessage("用法：/finance");
//				return false;
//			}
//			return true;
			return true;
		}else {
			sender.sendMessage("§c只有玩家才能使用这个指令");
			return false;
		}
	}
	void info(CommandSender sender) {
		
	}
}
