package tepco_epuw;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class ElectricPowerInfoCollector {

	private static final String TEPCO_USAGE_API = "http://tepco-usage-api.appspot.com/";
	private static final String LATEST_INFO = "latest";
	private static final String SUFFIX = ".json";
	private static final String NULL_RESULT = "{"
		+ "\"saving\": false,"
		+ "\"hour\": 0,"
		+ "\"forecast_peak_period\": 0,"
		+ "\"capacity_updated\": \"0000-00-00 00:00:00\","
		+ "\"forecast_peak_updated\": \"0000-00-00 00:00:00\","
		+ "\"month\": 0,"
		+ "\"usage_updated\": \"0000-00-00 00:00:00\","
		+ "\"entryfor\": \"0000-00-00 00:00:00\","
		+ "\"capacity_peak_period\": 0,"
		+ "\"forecast_peak_usage\": 0,"
		+ "\"year\": 0,"
		+ "\"usage\": 0,"
		+ "\"capacity\": 0,"
		+ "\"day\": 0}";
	private static final String SEP = "/";

	private static ElectricPowerInfoCollector instance;
	private ElectricPowerInfoCollector() {}

	public static ElectricPowerInfoCollector getInstance() {
		if (instance == null) {
			instance = new ElectricPowerInfoCollector();
		}
		return instance;
	}

	public String getLatestElectricPowerInfoData() {
		final String data = request(LATEST_INFO);
		if (data == null || data.length() == 0) {
			return NULL_RESULT;
		} else {
			return data;
		}
	}

	public String getElectricPowerInfoData(int year, int month, int day) {
		return getElectricPowerInfoData(year, month, day, -1);
	}
	public String getElectricPowerInfoData(int year, int month, int day, int hour) {
		final StringBuffer sb = new StringBuffer();
		if (year > 0) {
			sb.append(year);
		}
		if (month > 0) {
			sb.append(SEP).append(month);
		}
		if (day > 0) {
			sb.append(SEP).append(day);
		}
		if (hour > 0) {
			sb.append(SEP).append(hour);
		}
		
		final String data = request(sb.toString());
		if (data == null) {
			if (hour > 0) {
				return NULL_RESULT;
			} else {
				return "[]";
			}
		} else {
			return data;
		}
	}

	private String request(String date) {
		String data = null;
		InputStream is = null;
        try {
            is = new URL(TEPCO_USAGE_API + date + SUFFIX).openStream();
            data = IOUtils.toString(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }

        return data;
	}
}
