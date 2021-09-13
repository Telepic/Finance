package finance;

import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import finance.command.AdminCommand;
import finance.command.PlayerCommand;
import finance.exception.Pre_PluginDefectException;
import finance.listener.AdministratorAsyncChatListener;
import finance.listener.MarketGUIListener;
import finance.listener.PlayerHangOrderCreateListener;
import finance.manager.ConfigManager;
import finance.manager.GlobalStateManager;
import finance.manager.HangingOrdersManager;
import finance.manager.MarketManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
/**@author Telepic
 * @category
 * <b>In short, trade between players will be created to be similar to the Futures Trade.
 * But there is something different between futures trade and this system.</b>
 * <p>About the free trade market:
 * <br>The player puts forward an order at any price (whether buying or selling)
 * If it is a purchase order, the order will match the current market price. If the order price is higher than 
 * the market price, all goods from this price to the market price will be traded at the buyer's price (of 
 * course, the buyer's price should be calculated according to the price published by him, and the owner of 
 * those sales orders still gets the price when they publish the sales order. After deducting tax expenses, 
 * the price difference profit will be counted as market profit and stored in the market account. If it is 
 * allowed to set up the market of the company's owner[Company Plugin], the profit can be counted as the 
 * company's profit). If the price of the order is equal to or lower than the current market price, it will 
 * be published in the market until the price of a sales order is lower than or equal to the price of the 
 * purchase order, so that the transaction between the two is successful.

 * <br>Similarly, if it is a sales order, when the price of the sales order is lower than the market price, all 
 * purchase orders from the sales price to the market price will be traded. There is also a price difference 
 * profit, and the logic is the same as above. When the price of the sales order is higher than or equal to 
 * the market price, his order will also be published in the market until the corresponding purchase order price 
 * is lower than or equal to the sales price to make the transaction between the two successful.
 * 
 * <br>I believe that this system and the future development of this environment will be a perfect economic plugin ecosystem.</p>
 */
public class Finance extends JavaPlugin{
	private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    
    private static Server server;
    private static MarketManager mManager;
    private static GlobalStateManager gsManager;
    private static ConfigManager cManager;
    private static HangingOrdersManager hgmanager;
    
	@Override
	public void onEnable() {
		long startLoadingTime = System.currentTimeMillis();
		getLogger().info("Finance插件加载中，请稍候.");
		
		//Register Managers
		setmManager(new MarketManager());
		setGsManager(new GlobalStateManager());
		setcManager(new ConfigManager());
		setHgmanager(new HangingOrdersManager());
		
		//Pre_Plugin
		getLogger().info("检测前置插件是否安装.");
		if(!setupEconomy()||!setupPermissions()) {
			try {
				throw new Pre_PluginDefectException(this.getServer());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}else getLogger().info("前置插件检测完毕.");
		//Support_Plugin(Without developing)
			
		if(!setupCompany()) {
			getLogger().info("公司插件(Company Plugin)未安装.");
			Finance.getcManager().setSetupCompany(false);
		}else Finance.getcManager().setSetupCompany(true);
		
		//...
		
		//Register Listener
		getServer().getPluginManager().registerEvents(new MarketGUIListener(), this);
		getServer().getPluginManager().registerEvents(new AdministratorAsyncChatListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerHangOrderCreateListener(), this);
		
		//Register Commands
		this.getCommand("finance").setExecutor(new PlayerCommand(this));
		this.getCommand("fiadmin").setExecutor(new AdminCommand(this));
		
		
		long endLoadingTime = System.currentTimeMillis() - startLoadingTime;
		getLogger().info("Finance加载完毕，耗时" + endLoadingTime + "ms");
	}
	@Override
	public void onDisable() {
		
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	private boolean setupCompany() {
		if (getServer().getPluginManager().getPlugin("Company") == null) {
            return false;
        }return true;
	}
	
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	public static MarketManager getmManager() {
		return mManager;
	}
	public static void setmManager(MarketManager mManager) {
		Finance.mManager = mManager;
	}
	public static Economy getEconomy() {
		return econ;
	}
	
	public static Permission getPerm() {
		return perms;
	}
	
	public static Chat getChat() {
		return chat;
	}
	
	public static Server getServ() {
		return server;
	}
	
	public static void setServer(Server server) {
		Finance.server = server;
	}
	public static GlobalStateManager getGsManager() {
		return gsManager;
	}
	public static void setGsManager(GlobalStateManager gsManager) {
		Finance.gsManager = gsManager;
	}
	public static ConfigManager getcManager() {
		return cManager;
	}
	public static void setcManager(ConfigManager cManager) {
		Finance.cManager = cManager;
	}
	public static HangingOrdersManager getHgmanager() {
		return hgmanager;
	}
	public static void setHgmanager(HangingOrdersManager hgmanager) {
		Finance.hgmanager = hgmanager;
	}
}
