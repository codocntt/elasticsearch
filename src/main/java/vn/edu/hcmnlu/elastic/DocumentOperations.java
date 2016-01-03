package vn.edu.hcmnlu.elastic;

import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Service;

import vn.edu.hcmnlu.bean.User;

import com.google.gson.Gson;

@Service
public class DocumentOperations {
	
	public void insertDocument(Client client, String index, String type, Map<String,Object> p){
		client.prepareIndex(index, type).setSource(p).execute().actionGet();
	}
	
	public void updateDocument(Client client, String index, String type, User p){
		//client.prepareUpdate(index, type, p.id).setDoc(new Gson().toJson(p)).execute().actionGet();
	}
	
	public void deleteDocument(Client client, String index, String type, String id){
		client.prepareDelete(index, type, id).execute().actionGet();
	}
	
	public User getDocument(Client client, String index, String type, String id){
		GetResponse response = client.prepareGet(index, type, id).execute().actionGet();
		Map<String, Object> source = response.getSource();
		String data = new Gson().toJson(source);
		return new Gson().fromJson(data, User.class);
	}
}
