package finance.exception;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MouseClickStateException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	public MouseClickStateException(Server sv,Player p) {
		super();
		sv.getLogger().info("[Finance] "+p.getName()+"�ƺ������һ�β���ȷ�����");
	}
}
