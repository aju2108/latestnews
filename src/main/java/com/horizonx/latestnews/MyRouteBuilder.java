package com.horizonx.latestnews;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() throws Exception{

    	PropertiesComponent properties = new PropertiesComponent();
		properties.setLocation("classpath:latestnews.properties");
		getContext().addComponent("properties", properties);
		
    	from("latestnews://latest-news?delay={{delay}}&language={{language}}&filepath={{file_path}}&authkey={{auth_key}}&apiurl={{api_url}}")
    	
    	.split(body())
    	
		.log(body().toString());
    }

}
