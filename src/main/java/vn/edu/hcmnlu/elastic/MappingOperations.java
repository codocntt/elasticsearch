package vn.edu.hcmnlu.elastic;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;

public class MappingOperations {
	
	private final Client client;
	
	public MappingOperations(Client client) {
		this.client = client;
	}

	public void createMappingTemplate(String name, String contentBuilder) throws IOException {
		client.admin().indices().preparePutMapping(name).setSource(contentBuilder).execute().actionGet();
	}
	
	public void createMappingTemplate(String name, String mappings, String settings) throws IOException {
		CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(name);
		createIndexRequestBuilder.setSettings(settings);
		createIndexRequestBuilder.addMapping("doc",mappings);
		createIndexRequestBuilder.execute().actionGet();
	}
}
