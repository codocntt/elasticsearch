package vn.edu.hcmnlu.elastic;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndicesOperations {
	
	private ClientConnection clientConnection;
	private String name;
	
	
	private static final Logger logger = LoggerFactory.getLogger(IndicesOperations.class);
	
	
	public boolean checkIndexExists(){
		IndicesExistsResponse response = clientConnection.getTransportClient().admin().indices().
										prepareExists(name)
										.execute().actionGet();
		return response.isExists();
	}
	
	public void createIndex(){
		if(!checkIndexExists()){
			clientConnection.getTransportClient().admin().indices().prepareCreate(name).execute().actionGet();
			logger.info("Create indice");
		}
	}
	
	/*public void createIndex(String name, String documentType, XContentBuilder contentBuilder){
		createIndex(name);
		new MappingOperations().createMappingTemplate(client, name, documentType, contentBuilder);
	}*/
	
	public void deleteIndex(){
		clientConnection.getTransportClient().admin().indices().prepareDelete(name).execute().actionGet();
	}
	
	public void closeIndex(){
		clientConnection.getTransportClient().admin().indices().prepareClose(name).execute().actionGet();
	}
	
	public void openIndex(){
		clientConnection.getTransportClient().admin().indices().prepareOpen(name).execute().actionGet();
	}
	
	public void refreshIndex(){
		clientConnection.getTransportClient().admin().indices().prepareRefresh(name).execute().actionGet();
	}
	

	public void setClientConnection(ClientConnection clientConnection) {
		this.clientConnection = clientConnection;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
