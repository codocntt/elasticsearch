package vn.edu.hcmnlu.elastic;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Service;

@Service
public class IndicesOperations {

	public boolean checkIndexExists(Client client, String name){
		IndicesExistsResponse response = client.admin().indices().
										prepareExists(name)
										.execute().actionGet();
		return response.isExists();
	}
	
	public void createIndex(Client client, String name){
		client.admin().indices().prepareCreate(name).execute().actionGet();
	}
	
	/*public void createIndex(String name, String documentType, XContentBuilder contentBuilder){
		createIndex(name);
		new MappingOperations().createMappingTemplate(client, name, documentType, contentBuilder);
	}*/
	
	public void deleteIndex(Client client, String name){
		client.admin().indices().prepareDelete(name).execute().actionGet();
	}
	
	public void closeIndex(Client client ,String name){
		client.admin().indices().prepareClose(name).execute().actionGet();
	}
	
	public void openIndex(Client client, String name){
		client.admin().indices().prepareOpen(name).execute().actionGet();
	}
	
	public void refreshIndex(Client client, String name){
		client.admin().indices().prepareRefresh(name).execute().actionGet();
	}
	
}
