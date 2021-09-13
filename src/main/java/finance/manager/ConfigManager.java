package finance.manager;

import finance.api.Language;

public class ConfigManager {
	private String version;
	private Language language;
	private double server_economy_index;
	private boolean setupCompany;
	private int checkCycle;
	public boolean isSetupCompany() {
		return setupCompany;
	}
	public void setSetupCompany(boolean setupCompany) {
		this.setupCompany = setupCompany;
	}
	public double getServer_economy_index() {
		return server_economy_index;
	}
	public void setServer_economy_index(double server_economy_index) {
		this.server_economy_index = server_economy_index;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getCheckCycle() {
		return checkCycle;
	}
	public void setCheckCycle(int checkCycle) {
		this.checkCycle = checkCycle;
	}
	
}
