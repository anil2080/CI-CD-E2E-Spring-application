package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static ConfigReader instance = null;
	private Properties prop = null;

	private ConfigReader() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir" + "\\src\\test\\java\\config\\config.properties"));
		prop.load(fis);
	}

	public static synchronized ConfigReader getInstance() throws IOException {
		if (instance == null)
			instance = new ConfigReader();
		return instance;
	}
	
	public String getValue(String propKey) {
		return this.prop.getProperty(propKey);
	}

}
