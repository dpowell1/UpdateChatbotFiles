package discovery;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;

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

}
