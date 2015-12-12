package vn.edu.hcmnlu.elastic;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;

public class IndicesOperations {
	private final Client client;

	public IndicesOperations(Client client) {
		this.client = client;
	}
	
	public boolean checkIndexExists(String name){
		IndicesExistsResponse response = client.admin().indices().
										prepareExists(name)
										.execute().actionGet();
		return response.isExists();
	}
	
	public void createIndex(String name){
		client.admin().indices().prepareCreate(name).execute().actionGet();
	}
	
	/*public void createIndex(String name, String documentType, XContentBuilder contentBuilder){
		createIndex(name);
		new MappingOperations().createMappingTemplate(client, name, documentType, contentBuilder);
	}*/
	
	public void deleteIndex(String name){
		client.admin().indices().prepareDelete(name).execute().actionGet();
	}
	
	public void closeIndex(String name){
		client.admin().indices().prepareClose(name).execute().actionGet();
	}
	
	public void openIndex(String name){
		client.admin().indices().prepareOpen(name).execute().actionGet();
	}
	
	public void refreshIndex(String name){
		client.admin().indices().prepareRefresh(name).execute().actionGet();
	}
	
	public static void main(String[] args) {
		Client client = ClientConnection.getTransportClient();
		IndicesOperations indices = new IndicesOperations(client);
		System.out.println(indices.checkIndexExists("demo"));
	}
	
}
