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
			 * ����Ƿ�������ͬ��Ʒ���г��У�����Ƿ����йҵ���
			 * ��û�йҵ������һ�۸���Ϊ���������۸�
			 * ���м۸���Ҫ�Լ۸���бȽ�
			 * �۸���ڵ�ǰ�۸��������С�ڵ�ǰ�۸������Ϊ�ҵ�����
			 * ������ǹҵ�����Ҫ�Գ��ۺ�ʵ�ʼ۸���в�ۼ���
			 * ������򵥣���Ҫ�������ѹҵ��������м۸����򣬴ӵ͵��ߵ����ж�������ƥ��
			 * ƥ�䶩����Ҫƥ�������͵�ǰ�۸�
			 * 
			 * �����������С�ڵ�ǰ�۸񵽳��۵����ж�������֮��
			 * ��ô��ǰ�г��ۻή��ƥ�������͵��򵥼۸�
			 * 
			 * ������������ڵ�ǰ�۸񵽳��۵����ж�������֮��
			 * Ҳ�������̫�࣬��ô��������ۺ󽫶����������Ϊ�µĹҵ�����
			 * ͬʱ�г��۸�Ҳ�ή���ö�����ĩβ�۸���Ϊ�ɽ��۸���Ƕ����ķ����۸����Խ��׳ɹ��ļ۸�Ҳ�ǵ�ǰ�����ʼ۸�
			 * 
			 * ͬ����������۸���ڵ�ǰ�۸�Ҳ��Ҫƥ�䣬ƥ��
			 * ���׳ɹ���ˢ�¼۸��Խ��׳ɹ��ļ۸�����Ϊ��ǰ�۸񣬽��׳ɹ�����Ҫ���г����еĽ�����Ŀ����������ÿ���������ѹҵĵ�������ʾˢ��
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
