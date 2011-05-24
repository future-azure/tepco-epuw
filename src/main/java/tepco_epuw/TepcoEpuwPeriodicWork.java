package tepco_epuw;

import java.util.Calendar;

import hudson.Extension;
import hudson.model.PeriodicWork;
import hudson.model.Hudson;
import hudson.widgets.Widget;

@Extension
public class TepcoEpuwPeriodicWork extends PeriodicWork {
	private static final ElectricPowerInfoCollector collector = ElectricPowerInfoCollector.getInstance();
	private static TepcoElectricPowerUsageWidget smw;

	@Override
	public long getRecurrencePeriod() {
		// request latest electric power usage every 15 minutes
		return 15 * 60 * 1000;
	}
	
	@Override
	public long getInitialDelay() {
        return 0;
    }

	@Override
	protected void doRun() throws Exception {
		if (smw == null) {
			for (Widget w : Hudson.getInstance().getWidgets()) {
				if (w instanceof TepcoElectricPowerUsageWidget) {
					smw = (TepcoElectricPowerUsageWidget) w;
				}
			}
		}

		final String info = collector.getLatestElectricPowerInfoData();
		smw.setCurrent(info);

		final Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		final String today = collector.getElectricPowerInfoData(year, month, day);
		smw.setToday(today);

		if (smw.getYestoday() == null) {
			cal.add(Calendar.DATE, -1);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			day = cal.get(Calendar.DATE);
			final String yestoday = collector.getElectricPowerInfoData(year, month, day);
			smw.setYestoday(yestoday);
		}
	}
}
