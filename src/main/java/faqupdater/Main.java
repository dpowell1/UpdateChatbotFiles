package faqupdater;

public class Main {
	
	public static void main(String[] args) {
		// Proxy
		String host = "127.0.0.1";
		String port = "3128";
		System.setProperty("http.proxyHost", host);
		System.setProperty("http.proxyPort", port);
		System.setProperty("https.proxyHost", host);
		System.setProperty("https.proxyPort", port);
		
		UpdateFaq uf = new UpdateFaq();
	}
	
}
