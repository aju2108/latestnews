package com.horizonx.latestnews.consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.horizonx.latestnews.MainApp;
import com.horizonx.latestnews.beans.LatestNews;
import com.horizonx.latestnews.endpoint.LatestNewsEndpoint;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class LatestNewsConsumer extends ScheduledPollConsumer {
	
	private static Logger logger = LoggerFactory.getLogger(LatestNewsConsumer.class);
	
	private LatestNewsEndpoint endpoint;

	public LatestNewsConsumer(LatestNewsEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        this.setDelay(endpoint.getConfiguration().getDelay());
	}

	@Override
	protected int poll() throws Exception {
		
		String operationPath = endpoint.getOperationPath();
		
		if (operationPath.equals("latest-news")) return processLatestNews();
		
		throw new IllegalArgumentException("Incorrect operation: " + operationPath);
	}
	
	private JsonObject performGetRequest(String uri, String apiUrl, String authKey) throws ClientProtocolException, IOException {
		
		HttpClient client = HttpClientBuilder.create().build();			
		HttpUriRequest request = RequestBuilder.get()
				.setUri(apiUrl + uri)
				.setHeader(HttpHeaders.AUTHORIZATION, authKey)
				.build();
		HttpResponse response = client.execute(request);

		if (response.getStatusLine().getStatusCode() != 200)
			throw new RuntimeException("currentsapi API error with return code: " + response.getStatusLine().getStatusCode());
				
		JsonParser parser = new JsonParser();
		InputStreamReader sr = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
		BufferedReader br = new BufferedReader(sr);
		JsonObject json = (JsonObject) parser.parse(br);
		br.close();
		sr.close();
		
		return json;
	}
	
	private int processLatestNews() throws Exception {
		
		String language = endpoint.getConfiguration().getLanguage();
		String filePath = endpoint.getConfiguration().getFilepath();
		String apiUrl = endpoint.getConfiguration().getApiurl();
		String authKey = endpoint.getConfiguration().getAuthkey();
		String uri = String.format("latest-news?language=%s", language);
		
		JsonObject json = performGetRequest(uri, apiUrl, authKey);
		
		JsonArray news = (JsonArray) json.get("news");
		List<LatestNews> newsList = new ArrayList<LatestNews>();
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		for (JsonElement f : news) {
			LatestNews latestNews = gson.fromJson(f, LatestNews.class);
			newsList.add(latestNews);		
		}	
		
		createLatestNewsCSV(newsList, filePath);
		
        return 1;
	}
	
	public void createLatestNewsCSV(List<LatestNews> newsList, String filePath)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		logger.info("Latest News CSV writting time {} ", new Date());
		try (Writer writer = Files.newBufferedWriter(Paths.get(filePath));) {
			StatefulBeanToCsv<LatestNews> beanToCsv = new StatefulBeanToCsvBuilder(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			
			beanToCsv.write(newsList);
		}
	}

}