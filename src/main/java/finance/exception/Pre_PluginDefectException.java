package finance.exception;

import org.bukkit.Server;

public class Pre_PluginDefectException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pre_PluginDefectException(Server sv,String msg) {
		super(msg);
		sv.getLogger().info(msg);
	}
	
	public Pre_PluginDefectException(Server sv) {
		super();
		sv.getLogger().info("[Finance] ǰ�ò��ȱʧ������ϸ�Ķ����˵��");
	}
}
