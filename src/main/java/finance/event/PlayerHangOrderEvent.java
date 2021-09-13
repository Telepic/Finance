package finance.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import finance.market.HangingOrder;

public class PlayerHangOrderEvent extends Event implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
    private HangingOrder order;
    private boolean cancelled;
    
    public PlayerHangOrderEvent(HangingOrder hOrder) {
    	setOrder(hOrder);
    }
    
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public HangingOrder getHangingOrder() {
		return order;
	}

	public void setOrder(HangingOrder order) {
		this.order = order;
	}

	public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

}
