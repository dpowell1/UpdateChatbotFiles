package faqupdater;

import java.util.List;

import org.jsoup.select.Elements;

import com.google.gson.JsonObject;

import discovery.DiscoveryManager;
import retrieveandrank.RetrieveAndRankManager;
import utils.CredentialsManager;
import utils.HtmlParser;

public class UpdateFaq {
	private DiscoveryManager discovery;
	private RetrieveAndRankManager rnr;
	private final CredentialsManager cred;
	
	public UpdateFaq() {
		cred = CredentialsManager.getInstance();
		discovery = new DiscoveryManager(cred);
		rnr = new RetrieveAndRankManager(cred);
	}
	
	public void updateAllDocs() {
		//First, download all content from Retrieve and Rank
		//including 'exports' (zip including quesitons and answers)
		//and config zip file
		//Documents must be uploaded before importing 'exports'
		
	}
	
	public void updateDiscoveryFaqDocuments() {
		HtmlParser parser = new HtmlParser();
		Elements elems = parser.getFaqs("https://us.etrade.com/frequently-asked-questions");
		List<JsonObject> jsonFaqs = parser.formatElements(elems);
		this.deleteOldFaqsFromDiscovery();
		this.uploadFaqsToDiscovery(jsonFaqs);
	}
	
	private void deleteOldFaqsFromDiscovery() {
//		QueryRequest.Builder queryBuilder = new QueryRequest.Builder(environmentId, collectionId);
//		queryBuilder.query("text:faq");
//		QueryRequest req = queryBuilder.build();
//		QueryResponse resp = discovery.query(req).execute();
//		System.out.println(resp.toString().substring(0, 100));
		//this is how to get doc ids
//		
//		GetCollectionRequest request = new GetCollectionRequest.Builder(environmentId, collectionId).build();
//		GetCollectionResponse response = discovery.getCollection(request).execute();
//		System.out.println(response.);
	}
	
	private void uploadFaqsToDiscovery(List<JsonObject> jsonFaqs) {
		for (JsonObject j : jsonFaqs) {
			
		}
	}
	
}
