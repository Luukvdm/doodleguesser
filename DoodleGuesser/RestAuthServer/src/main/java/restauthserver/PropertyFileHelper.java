package restauthserver;

import fontysmultipurposelibrary.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileHelper {

	private PropertyFileHelper() {}

	public static String getDbConnectionString() {
		Properties properties = new Properties();
		String fileName = "/config.properties";

		InputStream in = PropertyFileHelper.class.getClass().getResourceAsStream(fileName);
		try {
			properties.load(in);
		} catch (IOException e) {
			Logger.getInstance().log(e);
		}

		return properties.getProperty("ConnectionString");
	}
}
