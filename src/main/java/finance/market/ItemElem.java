package finance.market;

import org.bukkit.inventory.ItemStack;

public class ItemElem{
	private double standerd_price;
	private int standerd_quantity;
	private ItemStack itemstack;
	private boolean supply_way = false;//true is timing,false is interval
	private double supply_cycle = 0;
	private int supply_quantity = 0;
	private int now_quantity;
	
	public ItemElem(double standerd_price,int standerd_quantity,ItemStack itemstack) {
		this.setStanderd_price(standerd_price);
		this.setStanderd_quantity(standerd_quantity);
		this.setItemstack(itemstack);
		this.setNow_quantity(standerd_quantity);
	}


	public double getStanderd_price() {
		return standerd_price;
	}


	public void setStanderd_price(double standerd_price) {
		this.standerd_price = standerd_price;
	}


	public int getStanderd_quantity() {
		return standerd_quantity;
	}


	public void setStanderd_quantity(int standerd_quantity) {
		this.standerd_quantity = standerd_quantity;
	}


	public ItemStack getItemstack() {
		return itemstack;
	}


	public void setItemstack(ItemStack itemstack) {
		this.itemstack = itemstack;
	}


	public boolean getSupply_way() {
		return supply_way;
	}


	public void setSupply_way(boolean supply_way) {
		this.supply_way = supply_way;
	}


	public double getSupply_cycle() {
		return supply_cycle;
	}


	public void setSupply_cycle(double supply_cycle) {
		this.supply_cycle = supply_cycle;
	}


	public double getSupply_quantity() {
		return supply_quantity;
	}


	public void setSupply_quantity(int supply_quantity) {
		this.supply_quantity = supply_quantity;
	}
	
	public double getNowPrice() {
		double truep = (double) Math.round(standerd_price*standerd_quantity/now_quantity);
		return truep;
	}

	public double getSumPrice(int quantity) {
		double sum = 0;
		if(quantity>0) {
			for(int i = 1;i<=Math.abs(quantity);i++) {
				sum+=getNowPrice();
				now_quantity+=1;
			}
		}else {
			for(int i = 1;i<=Math.abs(quantity);i++) {
				sum+=getNowPrice();
				now_quantity-=1;
			}
		}
		return sum;
	}
	
	public int getNow_quantity() {
		return now_quantity;
	}


	public void setNow_quantity(int now_quantity) {
		this.now_quantity = now_quantity;
	}
}
