package finance.market;

import org.bukkit.entity.Player;

public class Order {
	private Player Buyer,Seller;//买家付钱卖家出货
	private double price;
	private int quantity;
	private ItemElem good;
	private boolean confirm;
	private Market market;
	private OrderType otype;
	public Order(ItemElem good,Player Seller,Player Buyer,Market market) {
		setBuyer(Buyer);
		setSeller(Seller);
		setGood(good);
		setConfirm(false);
		setMarket(market);
		setPrice(0);
		setQuantity(0);
		if(Seller == null) {
			setOtype(OrderType.BUY_IN_SERVER);
		}else if(Buyer == null) {
			setOtype(OrderType.SELL_TO_SERVER);
		}else {
			
		}
	}
	
	public Player getSeller() {
		return Seller;
	}

	public void setSeller(Player seller) {
		Seller = seller;
	}

	public Player getBuyer() {
		return Buyer;
	}

	public void setBuyer(Player buyer) {
		Buyer = buyer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ItemElem getGood() {
		return good;
	}

	public void setGood(ItemElem good) {
		this.good = good;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public OrderType getOtype() {
		return otype;
	}

	public void setOtype(OrderType otype) {
		this.otype = otype;
	}
}
