package com.horizonx.latestnews.component;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import com.horizonx.latestnews.configuration.LatestNewsConfiguration;
import com.horizonx.latestnews.endpoint.LatestNewsEndpoint;

public class LatestNewsComponent extends UriEndpointComponent {

	public LatestNewsComponent() {
		super(LatestNewsEndpoint.class);
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		
		LatestNewsEndpoint endpoint = new LatestNewsEndpoint(uri, remaining, this);		
		LatestNewsConfiguration configuration = new LatestNewsConfiguration();
		
		// use the built-in setProperties method to clean the camel parameters map
		setProperties(configuration, parameters);
		
		endpoint.setConfiguration(configuration);		
		return endpoint;
	}
}