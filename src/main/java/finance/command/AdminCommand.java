package finance.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import finance.Finance;
import finance.market.Market;
import finance.market.MarketType;

public class AdminCommand implements CommandExecutor{
	public AdminCommand(Finance plugin) {
    }

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//fiadmin
		if(sender.hasPermission("Finance.admin")) {
			//market
			if(args[0].equalsIgnoreCase("market")) {
				
				//create [name] [type]
				if(args[1].equalsIgnoreCase("create")) {//��
					if(args.length != 5) {
						sender.sendMessage("��c����һ���г�/fiadmin market create [����]  [չʾ��]  [����] \n����[����]Ϊmain��free��mainΪϵͳ�г���freeΪ�����г�");
						return true;
					}else {
						MarketType temp;
						if(args[4].equalsIgnoreCase("main")) {
							temp = MarketType.MAIN;
						}else if(args[4].equalsIgnoreCase("free")) {
							temp = MarketType.FREE;
						}else {
							sender.sendMessage("��c����һ���г�/fiadmin market create [����]  [չʾ��]  [����] \n����[����]Ϊmain��free��mainΪϵͳ�г���freeΪ�����г�");
							return true;
						}if(Finance.getmManager().checkTitle(args[3])) {
							sender.sendMessage("��cչʾ���ظ�");
							return true;
						}
						if(Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("��c�г����ظ�");
							return true;
						}else {
							Market market = new Market(args[2],args[3], temp);
							Finance.getmManager().addMarket(market);
							sender.sendMessage("��a�����ɹ���");
							return true;
						}
						
					}
				}
				
				//info
				if(args[1].equalsIgnoreCase("info")) {
					Finance.getmManager().info(sender);
					return true;
				}
				
				//add
				if(args[1].equalsIgnoreCase("add")) {//��
					if(args.length != 5) {
						sender.sendMessage("��c�����е���Ʒ��ӵ��г�/fiadmin market add [�г���(����չʾ��)] [���] [��׼����] ");
						return true;
					}else {
						Player p = null;
						if(!(sender instanceof Player)) {
							sender.sendMessage("��c����̨����ִ�и�ָ��");
							return true;
						}else {p = (Player)sender;}
						if(p.getItemInHand().getTypeId() == 0) {
							sender.sendMessage("��c���ϱ��������Ʒ��");
							return true;
						}
						double sprice = 0;
						int squ = 0;
						Market temp;
						if(!Finance.getmManager().checkName(args[2])) {
							p.sendMessage("��c�Ҳ������г�");
							return true;
						}else {
							temp = Finance.getmManager().getMarket(args[2]);
						}
						if(!Finance.getmManager().getMarket(args[2]).getType().equals(MarketType.MAIN)) {
							p.sendMessage("��cֻ��ϵͳ�г�����ʹ�ø�����");
							return true;
						}
						else {
							try {
								sprice = Double.valueOf(args[3]);
								squ = Integer.valueOf(args[4]);
							}catch(NumberFormatException e) {
								p.sendMessage("��c����ı�� ��׼��������Ϊ���֣��ұ�׼��������Ϊ����");
								return true;
							}
						}
						
						ItemStack onHand = new ItemStack(p.getItemInHand());
						if(temp.check(onHand)) {
							sender.sendMessage("��c����Ʒ���г����Ѵ��ڣ�");
							return true;
						}
						temp.addItem(onHand,sprice,squ);
						sender.sendMessage("��a��ӳɹ����������ö�ʱ��������\n����/fiadmin market set�鿴����");
						return true;
					}
				}
				
				//set
				if(args[1].equalsIgnoreCase("set")) {//��
					if(args.length != 5) {
						sender.sendMessage("��6/fiadmin market set [�г���(����չʾ��)] [����] [��ֵ]"
								+ "��6����������Ʒ��ϵͳ�г���һЩ����"
								+ "\n��e������way��cycle��quantity"
								+ "\n��eway��Ϊtiming(��ʱˢ��)��interval(���ˢ��) "
								+ "\n��ecycleΪˢ�¼����Ĭ����СʱΪ��λ"
								+ "\n��equantityΪ������������ÿ�β������Ʒ������"
								);
						return true;
					}else {
						int quantity = 0;
						double index = 0;
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("��c�Ҳ������г�");
							return true;
						}else {
						}
						if(!(args[4].equalsIgnoreCase("way")||args[3].equalsIgnoreCase("cycle")||args[4].equalsIgnoreCase("quantity"))) {
							sender.sendMessage("\n��c����ֻ��way��cycle��quantity");
						}
						if(!Finance.getmManager().getMarket(args[2]).getType().equals(MarketType.MAIN)) {
							sender.sendMessage("��cֻ��ϵͳ�г�����ʹ�ø�����");
							return true;
						}
						Player p = null;
						if(!(sender instanceof Player)) {
							sender.sendMessage("��c����̨����ִ�и�ָ��");
							return true;
						}else {p = (Player)sender;}
						if(!Finance.getmManager().getMarket(args[2]).check(p.getItemInHand())) {
							sender.sendMessage("��c����Ʒ���г��в�����");
							return true;
						}
						if(p.getItemInHand().getTypeId() == 0) {
							sender.sendMessage("��c���ϱ��������Ʒ��");
							return true;
						}
						if(args[3].equalsIgnoreCase("cycle")) {
							try {
								index = Double.valueOf(args[4]);
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_cycle(index);
								sender.sendMessage("��a���óɹ���");
								return true;
							}catch(NumberFormatException e) {
								sender.sendMessage("��cˢ��ʱ�����Ϊ���֣�");
								return true;
							}
						}
						if(args[3].equalsIgnoreCase("quantity")) {
							try {
								quantity = Integer.valueOf(args[4]);
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_quantity(quantity);
								sender.sendMessage("��a���óɹ���");
								return true;
							}catch(NumberFormatException e) {
								sender.sendMessage("��c������������Ϊ������");
								return true;
							}
						}
						if(args[3].equalsIgnoreCase("way")) {
							if(args[4].equalsIgnoreCase("timing")) {
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_way(true);
								sender.sendMessage("��a���óɹ���");
								return true;
							}else if(args[4].equalsIgnoreCase("interval")) {
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_way(false);
								sender.sendMessage("��a���óɹ���");
								return true;
							}else {
								sender.sendMessage("��4way��Ϊtiming(��ʱˢ��)��interval(���ˢ��) ");
								return true;
							}
						}
					}
				}
				
				//close 
				if(args[1].equalsIgnoreCase("close")) {//��
					if(args.length != 3) {
						sender.sendMessage("��6��ʱ�ر��г�/fiadmin market close [�г���] ");
						return true;
					}else {
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("��c�Ҳ������г�");
							return true;
						}else {
							Finance.getmManager().getMarket(args[2]).opening = false;
							sender.sendMessage("��c�г� "+ Finance.getmManager().getMarket(args[2]).getDisplayName() +" ��c�ѹرգ�");
							return true;
						}
					}
				}
				
				//open
				if(args[1].equalsIgnoreCase("open")) {//��
					if(args.length != 3) {
						sender.sendMessage("��6���г�/fiadmin market open [�г���] ");
						return true;
					}else {
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("��c�Ҳ������г�");
							return true;
						}else {
							Finance.getmManager().getMarket(args[2]).opening = true;
							sender.sendMessage("��a�г� "+ Finance.getmManager().getMarket(args[2]).getDisplayName() +" ��a�ѿ�����");
							return true;
						}
					}
				}
				//remove
				if(args[1].equalsIgnoreCase("remove")) {//��
					if(args.length != 3) {
						sender.sendMessage("��cɾ���г�/fiadmin market remove [�г���] ");
						return true;
					}else {
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("��c�Ҳ������г�");
							return true;
						}else {
							if(sender instanceof Player) {
								Finance.getGsManager().getMarketRemoveList().put(sender, Finance.getmManager().getMarket(args[2]));
								sender.sendMessage("��c��ȷ���Ƿ�ɾ���г� " + Finance.getmManager().getMarket(args[2]).getDisplayName() + "��c��"
										+ "��c\n������confirmȷ�ϣ�����cancelȡ��");
							}else {
								Market temp = Finance.getmManager().getMarket(args[2]);
								temp.getNowMarketGUI().clear();
								Finance.getmManager().Markets.remove(temp.getDisplayName());
								sender.sendMessage("��bɾ���ɹ��� ");
							}
							return true;
						}
					}
				}
				
			}
			//index
			
			//reload
		}else {
			sender.sendMessage("��c��û��Ȩ����ô��");
		}
		return true;
	}
    
}
