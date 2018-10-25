package com.horizonx.latestnews.endpoint;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultPollingEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

import com.horizonx.latestnews.component.LatestNewsComponent;
import com.horizonx.latestnews.configuration.LatestNewsConfiguration;
import com.horizonx.latestnews.consumer.LatestNewsConsumer;

@UriEndpoint(scheme="latestnews", title="LatestNews", syntax="latestnews://operationPath", consumerOnly=true, consumerClass=LatestNewsConsumer.class, label="news")
public class LatestNewsEndpoint extends DefaultPollingEndpoint {

	public LatestNewsEndpoint(String uri, String operationPath, LatestNewsComponent component) {
		super(uri, component);
		this.operationPath = operationPath;
	}
	
	private String operationPath;

	@UriParam
	private LatestNewsConfiguration configuration;

	public Producer createProducer() throws Exception {
		throw new UnsupportedOperationException("Latesnews Producer is not implemented");
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		LatestNewsConsumer consumer = new LatestNewsConsumer(this, processor);
        return consumer;
	}

	public boolean isSingleton() {
		return true;
	}

	public String getOperationPath() {
		return operationPath;
	}

	public void setOperationPath(String operationPath) {
		this.operationPath = operationPath;
	}

	public LatestNewsConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(LatestNewsConfiguration configuration) {
		this.configuration = configuration;
	}
	
}