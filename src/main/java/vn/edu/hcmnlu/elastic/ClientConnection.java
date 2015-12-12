package vn.edu.hcmnlu.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;


public class ClientConnection {
	private static String hostname = "localhost";
	private static int port = 9300;
	public static Client getTransportClient(){
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name","elasticsearch").build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(ClientConnection.hostname, ClientConnection.port));
		return client;
	}
}
