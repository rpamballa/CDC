/**
 * 
 */
package com.rpamballa.cdc.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

/**
 * @author revanthpamballa
 * 
 */
public enum PropConfig {

	INSTANCE;
	private Configuration instance = null;

	private static final Logger LOG = org.slf4j.LoggerFactory
			.getLogger(PropConfig.class);

	public Configuration get() throws IOException {
		if (instance == null) {
			try {
				instance = new PropertiesConfiguration(this.getClass()
						.getClassLoader().getResource(Constants.CONFIG_FILE));
			} catch (ConfigurationException ex) {
				throw new IOException("Could not load properties file "
						+ Constants.CONFIG_FILE, ex);
			}
		}
		return instance;
	}

	public void setProperty(final Properties prop) {
		instance = ConfigurationConverter.getConfiguration(prop);
	}

	public Properties getProperty() throws IOException {
		get();
		return ConfigurationConverter.getProperties(instance);
	}

	public String getProperty(final String key) throws IOException {
		get();
		final String result = instance.getString(key);
		if (StringUtils.isEmpty(result)) {
			throw new IOException(key + " not set");
		}
		return result;
	}

}
