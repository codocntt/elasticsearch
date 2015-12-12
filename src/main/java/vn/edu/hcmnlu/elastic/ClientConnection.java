package vn.edu.hcmnlu.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import vn.edu.hcmnlu.constants.Constants;

public class ClientConnection {
	
	private String clusterName;
	private int clusterPort;

	public Client getTransportClient(){
		Settings settings = ImmutableSettings.settingsBuilder().put(Constants.CLUSTER_KEY, this.clusterName).build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(Constants.HOST_NAME, this.clusterPort));
		return client;
	}

	public String getClusterName() {
		return clusterName;
	}

	public int getClusterPort() {
		return clusterPort;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public void setClusterPort(int clusterPort) {
		this.clusterPort = clusterPort;
	}
	
	
	
}
