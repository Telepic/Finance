package finance.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import finance.market.Market;
import finance.market.MarketType;

public class MarketManager {
	public Map<String,Market> Markets;
	
	public MarketManager() {
		Markets = new HashMap<String,Market>();
	}
	
	public void addMarket(Market market) {
		Markets.put(market.getDisplayName(), market);
	}
	
	/**
	 * @return Return true if the display_name(title) is in markets list;
	 */
	
	public boolean checkTitle(String title) {
		title=title.replaceAll("&", "��");
		if(Markets.containsKey(title)) {
			return true;
		}else return false;
	}
	
	/**
	 * @return Return true if the name is in markets list;
	 */
	public boolean checkName(String name)
	{
		for(Map.Entry<String, Market> e:Markets.entrySet()) {
			if(name.equals(e.getValue().getName())) {
				return true;
			}
		}
		return false;
	}
	
	public void info(CommandSender sender) {
		sender.sendMessage("��eĿǰ���е��г���");
		if(Markets.isEmpty()) {
			sender.sendMessage("��e��");
		}
		for(Map.Entry<String, Market> e:Markets.entrySet()) {
			if(e.getValue().getType().equals(MarketType.FREE)) {
				sender.sendMessage("��a�г�����" + e.getValue().getName() + "  ��bչʾ����" + e.getKey() + "  ��e���ͣ������г�");
			}else {
				sender.sendMessage("��a�г�����" + e.getValue().getName() + "  ��bչʾ����" + e.getKey() + "  ��e���ͣ�ϵͳ�г�");
			}
		}
	}
	
	public Market getMarketByTitle(String title) {
		return Markets.get(title);
	}
	public Market getMarket(String name) {
		for(Map.Entry<String, Market> e:Markets.entrySet()) {
			if(name.equals(e.getValue().getName())) {
				return e.getValue();
			}
		}
		return null;
	}
}
