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
				if(args[1].equalsIgnoreCase("create")) {//√
					if(args.length != 5) {
						sender.sendMessage("§c创建一个市场/fiadmin market create [名字]  [展示名]  [类型] \n其中[类型]为main或free，main为系统市场，free为自由市场");
						return true;
					}else {
						MarketType temp;
						if(args[4].equalsIgnoreCase("main")) {
							temp = MarketType.MAIN;
						}else if(args[4].equalsIgnoreCase("free")) {
							temp = MarketType.FREE;
						}else {
							sender.sendMessage("§c创建一个市场/fiadmin market create [名字]  [展示名]  [类型] \n其中[类型]为main或free，main为系统市场，free为自由市场");
							return true;
						}if(Finance.getmManager().checkTitle(args[3])) {
							sender.sendMessage("§c展示名重复");
							return true;
						}
						if(Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("§c市场名重复");
							return true;
						}else {
							Market market = new Market(args[2],args[3], temp);
							Finance.getmManager().addMarket(market);
							sender.sendMessage("§a创建成功！");
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
				if(args[1].equalsIgnoreCase("add")) {//√
					if(args.length != 5) {
						sender.sendMessage("§c将手中的物品添加到市场/fiadmin market add [市场名(不是展示名)] [标价] [标准件数] ");
						return true;
					}else {
						Player p = null;
						if(!(sender instanceof Player)) {
							sender.sendMessage("§c控制台不能执行该指令");
							return true;
						}else {p = (Player)sender;}
						if(p.getItemInHand().getTypeId() == 0) {
							sender.sendMessage("§c手上必须持有物品！");
							return true;
						}
						double sprice = 0;
						int squ = 0;
						Market temp;
						if(!Finance.getmManager().checkName(args[2])) {
							p.sendMessage("§c找不到该市场");
							return true;
						}else {
							temp = Finance.getmManager().getMarket(args[2]);
						}
						if(!Finance.getmManager().getMarket(args[2]).getType().equals(MarketType.MAIN)) {
							p.sendMessage("§c只有系统市场才能使用该命令");
							return true;
						}
						else {
							try {
								sprice = Double.valueOf(args[3]);
								squ = Integer.valueOf(args[4]);
							}catch(NumberFormatException e) {
								p.sendMessage("§c输入的标价 标准件数必须为数字，且标准件数必须为整数");
								return true;
							}
						}
						
						ItemStack onHand = new ItemStack(p.getItemInHand());
						if(temp.check(onHand)) {
							sender.sendMessage("§c该物品在市场中已存在！");
							return true;
						}
						temp.addItem(onHand,sprice,squ);
						sender.sendMessage("§a添加成功！建议设置定时补货周期\n输入/fiadmin market set查看详情");
						return true;
					}
				}
				
				//set
				if(args[1].equalsIgnoreCase("set")) {//√
					if(args.length != 5) {
						sender.sendMessage("§6/fiadmin market set [市场名(不是展示名)] [属性] [数值]"
								+ "§6设置手中物品在系统市场的一些属性"
								+ "\n§e属性有way、cycle和quantity"
								+ "\n§eway分为timing(定时刷新)和interval(间隔刷新) "
								+ "\n§ecycle为刷新间隔，默认以小时为单位"
								+ "\n§equantity为补货数量，即每次补充该商品的数量"
								);
						return true;
					}else {
						int quantity = 0;
						double index = 0;
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("§c找不到该市场");
							return true;
						}else {
						}
						if(!(args[4].equalsIgnoreCase("way")||args[3].equalsIgnoreCase("cycle")||args[4].equalsIgnoreCase("quantity"))) {
							sender.sendMessage("\n§c属性只有way、cycle和quantity");
						}
						if(!Finance.getmManager().getMarket(args[2]).getType().equals(MarketType.MAIN)) {
							sender.sendMessage("§c只有系统市场才能使用该命令");
							return true;
						}
						Player p = null;
						if(!(sender instanceof Player)) {
							sender.sendMessage("§c控制台不能执行该指令");
							return true;
						}else {p = (Player)sender;}
						if(!Finance.getmManager().getMarket(args[2]).check(p.getItemInHand())) {
							sender.sendMessage("§c该物品在市场中不存在");
							return true;
						}
						if(p.getItemInHand().getTypeId() == 0) {
							sender.sendMessage("§c手上必须持有物品！");
							return true;
						}
						if(args[3].equalsIgnoreCase("cycle")) {
							try {
								index = Double.valueOf(args[4]);
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_cycle(index);
								sender.sendMessage("§a设置成功！");
								return true;
							}catch(NumberFormatException e) {
								sender.sendMessage("§c刷新时间必须为数字！");
								return true;
							}
						}
						if(args[3].equalsIgnoreCase("quantity")) {
							try {
								quantity = Integer.valueOf(args[4]);
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_quantity(quantity);
								sender.sendMessage("§a设置成功！");
								return true;
							}catch(NumberFormatException e) {
								sender.sendMessage("§c补充数量必须为整数！");
								return true;
							}
						}
						if(args[3].equalsIgnoreCase("way")) {
							if(args[4].equalsIgnoreCase("timing")) {
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_way(true);
								sender.sendMessage("§a设置成功！");
								return true;
							}else if(args[4].equalsIgnoreCase("interval")) {
								Finance.getmManager().getMarket(args[2]).getItemElem(p.getItemInHand()).setSupply_way(false);
								sender.sendMessage("§a设置成功！");
								return true;
							}else {
								sender.sendMessage("§4way分为timing(定时刷新)和interval(间隔刷新) ");
								return true;
							}
						}
					}
				}
				
				//close 
				if(args[1].equalsIgnoreCase("close")) {//√
					if(args.length != 3) {
						sender.sendMessage("§6临时关闭市场/fiadmin market close [市场名] ");
						return true;
					}else {
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("§c找不到该市场");
							return true;
						}else {
							Finance.getmManager().getMarket(args[2]).opening = false;
							sender.sendMessage("§c市场 "+ Finance.getmManager().getMarket(args[2]).getDisplayName() +" §c已关闭！");
							return true;
						}
					}
				}
				
				//open
				if(args[1].equalsIgnoreCase("open")) {//√
					if(args.length != 3) {
						sender.sendMessage("§6打开市场/fiadmin market open [市场名] ");
						return true;
					}else {
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("§c找不到该市场");
							return true;
						}else {
							Finance.getmManager().getMarket(args[2]).opening = true;
							sender.sendMessage("§a市场 "+ Finance.getmManager().getMarket(args[2]).getDisplayName() +" §a已开启！");
							return true;
						}
					}
				}
				//remove
				if(args[1].equalsIgnoreCase("remove")) {//√
					if(args.length != 3) {
						sender.sendMessage("§c删除市场/fiadmin market remove [市场名] ");
						return true;
					}else {
						if(!Finance.getmManager().checkName(args[2])) {
							sender.sendMessage("§c找不到该市场");
							return true;
						}else {
							if(sender instanceof Player) {
								Finance.getGsManager().getMarketRemoveList().put(sender, Finance.getmManager().getMarket(args[2]));
								sender.sendMessage("§c请确认是否删除市场 " + Finance.getmManager().getMarket(args[2]).getDisplayName() + "§c？"
										+ "§c\n请输入confirm确认，输入cancel取消");
							}else {
								Market temp = Finance.getmManager().getMarket(args[2]);
								temp.getNowMarketGUI().clear();
								Finance.getmManager().Markets.remove(temp.getDisplayName());
								sender.sendMessage("§b删除成功！ ");
							}
							return true;
						}
					}
				}
				
			}
			//index
			
			//reload
		}else {
			sender.sendMessage("§c你没有权限这么做");
		}
		return true;
	}
    
}
