package finance.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import finance.market.Market;
import finance.market.Order;

public class GlobalStateManager {
	private Map<CommandSender,Market> MarketRemoveList;
	private Map<Player,Order> orders;
	public GlobalStateManager() {
		this.setMarketRemoveList(new HashMap<CommandSender,Market>());
		this.setOrders(new HashMap<Player,Order>());
	}
	public Map<Player,Order> getOrders() {
		return orders;
	}
	public void setOrders(Map<Player,Order> orders) {
		this.orders = orders;
	}
	public Map<CommandSender,Market> getMarketRemoveList() {
		return MarketRemoveList;
	}
	public void setMarketRemoveList(Map<CommandSender,Market> marketRemoveList) {
		MarketRemoveList = marketRemoveList;
	}
}
