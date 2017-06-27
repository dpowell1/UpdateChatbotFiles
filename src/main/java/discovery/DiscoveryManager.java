package discovery;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.collection.GetCollectionRequest;
import com.ibm.watson.developer_cloud.discovery.v1.model.collection.GetCollectionResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryRequest;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryResponse;

import utils.CredentialsManager;

public class DiscoveryManager {
	private Discovery discovery;
	private CredentialsManager cred;
	
	public DiscoveryManager(CredentialsManager cred) {
		this.cred = cred;
		discovery = new Discovery("2016-12-01");
		discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");
		discovery.setUsernameAndPassword(cred.getUsernameDisc(), cred.getPasswordDisc());
	}
	
	public void query(String input) {
		QueryRequest.Builder queryBuilder = new QueryRequest.Builder(
				cred.getEnvironmentIdDisc(), cred.getCollectionIdDisc());
		queryBuilder.query("text:faq");
		QueryRequest req = queryBuilder.build();
		QueryResponse resp = discovery.query(req).execute();
	}
	
	public void getCollectionStats() {
		GetCollectionRequest request = new GetCollectionRequest.Builder(
				cred.getEnvironmentIdDisc(), cred.getCollectionIdDisc()).build();
		GetCollectionResponse response = discovery.getCollection(request).execute();
		System.out.println(response.toString());
	}
	
	public void uploadDocument() {
		
	}

}
