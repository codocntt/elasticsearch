package vn.edu.hcmnlu.elastic;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Service;

@Service
public class MappingOperations {
	
	public void createMappingTemplate(Client client, String name, String contentBuilder) throws IOException {
		client.admin().indices().preparePutMapping(name).setSource(contentBuilder).execute().actionGet();
	}
	
	public void createMappingTemplate(Client client, String name, String mappings, String settings) throws IOException {
		CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(name);
		createIndexRequestBuilder.setSettings(settings);
		createIndexRequestBuilder.addMapping("doc",mappings);
		createIndexRequestBuilder.execute().actionGet();
	}
}
