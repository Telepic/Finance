package finance.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import finance.Finance;
import finance.event.PlayerHangOrderEvent;
import finance.manager.HangingOrdersManager;
import finance.market.ItemElem;
import finance.market.OrderType;

public class PlayerHangOrderCreateListener implements Listener{
	private HangingOrdersManager homanager = Finance.getHgmanager();
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerHangingOrderCreate(PlayerHangOrderEvent e) {
		if(e.getHangingOrder().getOtype()==OrderType.BUY_IN_PLAYER) {
			/*
			 * 检查是否已有相同物品在市场中（检查是否已有挂单）
			 * 若没有挂单，则第一价格则为订单发布价格
			 * 若有价格需要对价格进行比较
			 * 价格大于当前价格的卖单和小于当前价格的买单作为挂单处理
			 * 如果不是挂单就需要对出价和实际价格进行差价计算
			 * 如果是买单，需要对所有已挂的卖单进行价格排序，从低到高的所有订单进行匹配
			 * 匹配订单需要匹配数量和当前价格
			 * 
			 * 如果该买单数量小于当前价格到出价的所有订单数量之和
			 * 那么当前市场价会降到匹配完后最低的买单价格
			 * 
			 * 如果买单数量大于当前价格到出价的所有订单数量之和
			 * 也就是买的太多，那么会计算完差价后将多余的数量作为新的挂单发布
			 * 同时市场价格也会降到该订单的末尾价格（因为成交价格就是订单的发布价格，所以交易成功的价格也是当前的最适价格）
			 * 
			 * 同理，如果卖单价格低于当前价格也需要匹配，匹配
			 * 交易成功后刷新价格，以交易成功的价格设置为当前价格，交易成功后需要对市场已有的交易项目进行数量和每档数量，已挂的单进行显示刷新
			 */
			
			
			
			
			
//			if(e.getHangingOrder().getPrice()<e.getHangingOrder().getOwner().getItemElem(e.getHangingOrder().getGood()).getNowPrice()) {
//				Finance.getHgmanager().getPlayerOrders().put(new ItemElem(e.getHangingOrder().getPrice(), e.getHangingOrder().getQuantity(), e.getHangingOrder().getGood()), e.getHangingOrder());
//			}else {
//				//Market need list the price of the good;
//				//because we need the best price;
//				homanager.refreshItemStack(e.getHangingOrder());
//			}
		}else if(e.getHangingOrder().getOtype()==OrderType.SELL_TO_PLAYER) {
			
		}
	}

	public HangingOrdersManager getHomanager() {
		return homanager;
	}
}
