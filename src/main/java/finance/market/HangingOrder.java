package finance.market;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import finance.event.PlayerHangOrderEvent;

public class HangingOrder {
	private OrderType otype;
	private Player releaser;
	private double price;
	private int quantity;
	private ItemStack good;
	private Market owner;
	
	/**
	 * @category This will call the PlayerHangOrderEvent when it be inited.
	 */
	
	public HangingOrder(OrderType otype,Player releaser,double price,int quantity,ItemStack good,Market owner) {
		setOtype(otype);
		setReleaser(releaser);
		setPrice(price);
		setQuantity(quantity);
		setGood(good);
		setOwner(owner);
		PlayerHangOrderEvent e = new PlayerHangOrderEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(e);
	}


	public OrderType getOtype() {
		return otype;
	}


	public void setOtype(OrderType otype) {
		this.otype = otype;
	}


	public Player getReleaser() {
		return releaser;
	}


	public void setReleaser(Player releaser) {
		this.releaser = releaser;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public ItemStack getGood() {
		return good;
	}


	public void setGood(ItemStack good) {
		this.good = good;
	}


	public Market getOwner() {
		return owner;
	}


	public void setOwner(Market owner) {
		this.owner = owner;
	}
}
