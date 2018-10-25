package com.horizonx.latestnews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Camel Application
 */
public class MainApp {
	
	private static Logger logger = LoggerFactory.getLogger(MainApp.class);

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
		logger.info("Starting Apache Camel App for fetching latest News.");
		org.apache.camel.main.Main main = new org.apache.camel.main.Main();
		main.enableHangupSupport();
		main.addRouteBuilder(new MyRouteBuilder());
		main.run();
    }

}

