package tepco_epuw;

import hudson.Extension;
import hudson.widgets.Widget;

@Extension
public class TepcoElectricPowerUsageWidget extends Widget {
	private String current;
	private String today;
	private String yestoday;
	
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getYestoday() {
		return yestoday;
	}
	public void setYestoday(String yestoday) {
		this.yestoday = yestoday;
	}
}
