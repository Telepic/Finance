package finance.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Market {
	private static short max = 45;
	public boolean opening = true;
	private String name;
	private MarketType type;
	private ArrayList<Inventory> guis;//This will be needed to change.
	private String displayName;
	private Inventory nowPage;
	private Map<ItemStack,ItemElem> ies;//展示物品和真实物品与信息
	
	public Market(String name,String displayname,MarketType type) {
		this.setName(name);
		this.setType(type);
		displayname=displayname.replaceAll("&", "§");
		this.setDisplayName(displayname);
		ies = new HashMap<ItemStack,ItemElem>();
		initGUI();
	}
	
	public void initGUI() {
		this.guis = new ArrayList<Inventory>();
		Inventory first = Bukkit.createInventory(null ,54 , displayName);
		ItemStack pre = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack next = new ItemStack(Material.STAINED_GLASS_PANE);
		pre.setDurability((short) 14);
		next.setDurability((short) 14);
		ItemMeta nextim = next.getItemMeta();
		ItemMeta preim = pre.getItemMeta();
		String nextpage = "§e下一页";
		String prepage = "§c上一页";
		nextim.setDisplayName(nextpage);
		preim.setDisplayName(prepage);
		next.setItemMeta(nextim);
		pre.setItemMeta(preim);
		first.setItem(45, pre);
		first.setItem(53, next);
		guis.add(first);
		nowPage = first;
	}
	
	public void addItemtoGUI(ItemStack lore) {
		int maxSize = guis.size()*max;
		if(ies.size()>maxSize) {
			Inventory newPage = Bukkit.createInventory(null ,54 , displayName);
			ItemStack pre = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemStack next = new ItemStack(Material.STAINED_GLASS_PANE);
			pre.setDurability((short) 14);
			next.setDurability((short) 14);
			ItemMeta nextim = next.getItemMeta();
			ItemMeta preim = pre.getItemMeta();
			String nextpage = "§e下一页";
			String prepage = "§c上一页";
			nextim.setDisplayName(nextpage);
			preim.setDisplayName(prepage);
			next.setItemMeta(nextim);
			pre.setItemMeta(preim);
			newPage.setItem(45, pre);
			newPage.setItem(53, next);
			guis.add(newPage);
			nowPage = newPage;
		}
		
		nowPage.addItem(lore);
	}
	
	public double getSumPrice(ItemElem ie,int quantity) {//买入系统减少，卖出系统增多
		return ie.getStanderd_price()*((quantity*(ie.getNow_quantity()+ie.getNow_quantity()+quantity)/2)/ie.getStanderd_quantity());
	}
	
	public ItemStack getKeyByValue(ItemElem ie) {
		for(Map.Entry<ItemStack, ItemElem> e : ies.entrySet()) {
			if(e.getValue().getItemstack().isSimilar(ie.getItemstack())) {
				return e.getKey();
			}
		}
		//Bukkit.getServer().getLogger().info("it's null");
		return null;
	}
	
	public boolean refresh(ItemElem ie) {
		if(this.getType().equals(MarketType.MAIN)) {//Main market lore set.
			ItemStack loref = getKeyByValue(ie);
			for(Inventory in : guis) {
				if(in.contains(loref)) {
					ies.remove(loref);
					in.remove(loref);
					ItemStack lore = new ItemStack(ie.getItemstack());
					ItemMeta im = lore.getItemMeta(); 
					List<String> lores = new ArrayList<String>();
					Date now = new Date();
					lores.add("§c当前市场价格" + ie.getNowPrice());
					lores.add("§c当前市场存货" + ie.getNow_quantity());
					lores.add("§e刷新时间" + now.toString());
					lores.add("§a左键点击向市场购买→");
					lores.add("§b右键点击向市场出售←");
					im.setLore(lores); 
					lore.setItemMeta(im); 
					ies.put(lore, ie);
					in.addItem(lore);
					return true;
				}
			}
			//Bukkit.getServer().getLogger().info("it's null");
			return false;
		}else {//Free market lore set.
			ItemStack loref = getKeyByValue(ie);
			for(Inventory in : guis) {
				if(in.contains(loref)) {
					ies.remove(loref);
					in.remove(loref);
					ItemStack lore = new ItemStack(ie.getItemstack());
					ItemMeta im = lore.getItemMeta(); 
					List<String> lores = new ArrayList<String>();
					Date now = new Date();
					lores.add("§c当前市场价格" + ie.getNowPrice());
					lores.add("§e刷新时间" + now.toString());
					lores.add("§a挂单价格与数量（五档）");
					lores.add("§a");
					im.setLore(lores); 
					lore.setItemMeta(im); 
					ies.put(lore, ie);
					in.addItem(lore);
					return true;
				}
			}
			//Bukkit.getServer().getLogger().info("it's null");
			return false;
		}
	}
	
	public void addItem(ItemStack itemstack,double sta_pri,int sta_quan) {
		ItemStack lore = new ItemStack(itemstack);
		ItemElem ie = new ItemElem(sta_pri, sta_quan, new ItemStack(itemstack));
		ItemMeta im = lore.getItemMeta(); 
		List<String> lores = new ArrayList<String>();
		Date now = new Date();
		lores.add("§c当前市场价格" + ie.getNowPrice());
		lores.add("§c当前市场存货" + ie.getNow_quantity());
		lores.add("§e刷新时间" + now.toString());
		lores.add("§a左键点击向市场购买→");
		lores.add("§b右键点击向市场出售←");
		im.setLore(lores); 
		lore.setItemMeta(im); 
		ies.put(lore, ie);
		addItemtoGUI(lore);
	}

	public ItemElem getItemElemByLoreItem(ItemStack is) {
		for(Map.Entry<ItemStack, ItemElem> e:ies.entrySet()) {
			if(e.getKey().isSimilar(is)) {
				return e.getValue();
			}
		}
		return null;
	}
	
	public ItemElem getItemElem(ItemStack itemstack) {
		for(Map.Entry<ItemStack, ItemElem> e:ies.entrySet()) {
			if(e.getValue().getItemstack().isSimilar(itemstack)) {
				return e.getValue();
			}
			
		}
		return null;
	}
	
	public boolean check(ItemStack is) {
		for(Map.Entry<ItemStack,ItemElem> e : ies.entrySet()) {
			if(e.getValue().getItemstack().isSimilar(is)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void clear() {
		for(Inventory in :guis) {
			in.clear();
		}
		this.guis.clear();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MarketType getType() {
		return type;
	}

	public void setType(MarketType type) {
		this.type = type;
	}

	public Inventory getNowMarketGUI() {
		return nowPage;
	}

	public ArrayList<Inventory> getPages(){
		return guis;
	}
	
	@Deprecated
	public void setNowMarketGUI(Inventory marketGUI) {
		nowPage = marketGUI;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
