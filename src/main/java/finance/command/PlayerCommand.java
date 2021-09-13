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
					sender.sendMessage("��c/finance open [�г���] ����Ϊ[�г���]���г�");
					return true;
				}else{
					if(Finance.getmManager().checkName(args[1])) {
						Market temp = Finance.getmManager().getMarket(args[1]);
						if(!temp.opening) {
							sender.sendMessage("��c���г��ѹرա�");
							return true;
						}
						p.closeInventory();
						p.openInventory(temp.getNowMarketGUI());
						sender.sendMessage("��a���ѽ���" + temp.getDisplayName());
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
					sender.sendMessage("��cֻ����Ҳ���ִ�и�ָ��");
				}else {
					if(Finance.getPerm().playerHas((Player) sender, "finance.buy")) {
						Market temp = null;
						if(!Finance.getmManager().checkName(args[1])) {
							sender.sendMessage("��c�г�"+args[1]+"��c������");
							return true;
						}else {
							temp = Finance.getmManager().getMarket(args[1]);
							if(temp.getType().equals(MarketType.MAIN)) {
								sender.sendMessage("��c�㲻����ϵͳ�̳��ϼ���Ʒ");
								return true;
							}
						}
						double price = 0;
						int quantity = 0;
						try {
							price = Double.valueOf(args[2]);
							quantity = Integer.valueOf(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("��c�۸����Ϊ��������������Ϊ������");
							return true;
						}
						if(Finance.getEconomy().getBalance(p)<(price*quantity)) {
							sender.sendMessage("��c���Ǯ��������ۣ�");
							return true;
						}
						if(temp.getItemElem(p.getInventory().getItemInMainHand()).getNow_quantity()<quantity) {
							sender.sendMessage("��c�г��ϵĻ�������������");
							return true;
						}
						//Other Conditions
						@SuppressWarnings("unused")
						HangingOrder hOrder = new HangingOrder(OrderType.BUY_IN_PLAYER, p, price, quantity, p.getInventory().getItemInMainHand(), temp);
						
					}
					else {
						sender.sendMessage("��c��û��Ȩ����ô��");
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
//				sender.sendMessage("���ָ��ֻ�������ʹ��");
//				return false;
//			}
//			if(args.length > 0) {
//				sender.sendMessage("�÷���/finance");
//				return false;
//			}
//			return true;
			return true;
		}else {
			sender.sendMessage("��cֻ����Ҳ���ʹ�����ָ��");
			return false;
		}
	}
	void info(CommandSender sender) {
		
	}
}
