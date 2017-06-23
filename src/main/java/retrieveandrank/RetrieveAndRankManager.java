package retrieveandrank;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.RetrieveAndRank;
import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.model.SolrConfigs;

import utils.CredentialsManager;

public class RetrieveAndRankManager {
	private HttpSolrClient solrClient;
	private RetrieveAndRank service;
	private CredentialsManager cred;
	private String username;
	private String password;
	private String solrUrl;
	private String rankerId;
	private String clusterId;
	private String collectionName;
	
	public RetrieveAndRankManager(CredentialsManager cred) {
		this.cred = cred;
		service = new RetrieveAndRank();
		service.setEndPoint("https://gateway.watsonplatform.net/retrieve-and-rank/api");
		username = cred.getUsernameRnR();
		password = cred.getPasswordRnR();
		solrUrl = service.getSolrUrl(cred.getClusterIdRnR());
		rankerId = cred.getRankerIdRnR();
		clusterId = cred.getClusterIdRnR();
		collectionName = cred.getCollectionNameRnR();
		solrClient = getSolrClient(solrUrl, clusterId, username, password);
		service.setUsernameAndPassword(username, password);
	}

	public QueryResponse query(String input) {
		SolrQuery query = new SolrQuery(input);
		query.setParam("ranker_id", rankerId);
		query.setRequestHandler("/fcselect");
		
		QueryResponse response = null;
		try {
			response = solrClient.query(collectionName, query);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public void outputClusterConfigToZip() {
		ServiceCall<SolrConfigs> configs = service.getSolrClusterConfigurations(clusterId);
		SolrConfigs execute = configs.execute();
		String configName = execute.getSolrConfigs().get(0);
		ServiceCall<InputStream> call = service.getSolrClusterConfiguration(this.clusterId, "./" + configName);
		InputStream in = call.execute();
		File f = new File("./RetrieveAndRankConfigFiles.zip");
		try {
			Files.copy(in, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private HttpSolrClient getSolrClient(String uri, String clusterId, String username, String password) {
		return new HttpSolrClient(service.getSolrUrl(clusterId), createHttpClient(uri, username, password));
	}

	/**
	 * Creates the {@link HttpClient} to use with the Solrj
	 *
	 * @param url
	 *            the Solr server url
	 * @param username
	 *            the {@link RetrieveAndRank} service username
	 * @param password
	 *            the {@link RetrieveAndRank} service password
	 * @return the {@link HttpClient}
	 */
	private HttpClient createHttpClient(String url, String username, String password) {
		final URI scopeUri = URI.create(url);
		final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(scopeUri.getHost(), scopeUri.getPort()),
				new UsernamePasswordCredentials(username, password));
		// Proxy
		HttpHost proxy = new HttpHost("127.0.0.1", 3128);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

		final HttpClientBuilder builder = HttpClientBuilder.create().setMaxConnTotal(128).setMaxConnPerRoute(32)
				.setDefaultRequestConfig(RequestConfig.copy(RequestConfig.DEFAULT).setRedirectsEnabled(true).build())
				.setDefaultCredentialsProvider(credentialsProvider).addInterceptorFirst(new PreemptiveAuthInterceptor())
				.setRoutePlanner(routePlanner);
		return builder.build();
	}

	private class PreemptiveAuthInterceptor implements HttpRequestInterceptor {
		public void process(final HttpRequest request, final HttpContext context) throws HttpException {
			final AuthState authState = (AuthState) context.getAttribute(HttpClientContext.TARGET_AUTH_STATE);

			if (authState.getAuthScheme() == null) {
				final CredentialsProvider credsProvider = (CredentialsProvider) context
						.getAttribute(HttpClientContext.CREDS_PROVIDER);
				final HttpHost targetHost = (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
				final Credentials creds = credsProvider
						.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
				if (creds == null) {
					throw new HttpException("No credentials provided");
				}
				authState.update(new BasicScheme(), creds);
			}
		}
	}
	
}
