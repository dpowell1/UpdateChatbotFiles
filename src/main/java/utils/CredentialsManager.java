package utils;

import java.io.InputStreamReader;

import com.google.gson.Gson;

public class CredentialsManager {
	private String usernameConv;
	private String passwordConv;
	private String workspaceIdConv;
	
	private String usernameDisc;
	private String passwordDisc;
	private String collectionIdDisc;
	private String environmentIdDisc;
	
	private String usernameRnR;
	private String passwordRnR;
	private String collectionNameRnR;
	private String rankerIdRnR;
	private String clusterIdRnR;
	private String workspaceIdRnR;
	
	// Singleton pattern
	private CredentialsManager() {}
	
	private static class CredentialsManagerBuilder {
		public static final CredentialsManager instance = new Gson().fromJson(
				new InputStreamReader(CredentialsManager.class.getResourceAsStream("/config.json")),
				CredentialsManager.class
			);
	}
	
	public static CredentialsManager getInstance() {
		return CredentialsManagerBuilder.instance;
	}
	
	/**
	 * Saves the current configuration to the config.json file
	 * so that the new collections will be read next time.
	 */
	public void saveConfiguration() {
		
	}
	
	// Getters and setters
	public String getUsernameConv() {
		return usernameConv;
	}
	public void setUsernameConv(String usernameConv) {
		this.usernameConv = usernameConv;
	}
	public String getPasswordConv() {
		return passwordConv;
	}
	public void setPasswordConv(String passwordConv) {
		this.passwordConv = passwordConv;
	}
	public String getWorkspaceIdConv() {
		return workspaceIdConv;
	}
	public void setWorkspaceIdConv(String workspaceIdConv) {
		this.workspaceIdConv = workspaceIdConv;
	}
	public String getUsernameDisc() {
		return usernameDisc;
	}
	public void setUsernameDisc(String usernameDisc) {
		this.usernameDisc = usernameDisc;
	}
	public String getPasswordDisc() {
		return passwordDisc;
	}
	public void setPasswordDisc(String passwordDisc) {
		this.passwordDisc = passwordDisc;
	}
	public String getCollectionIdDisc() {
		return collectionIdDisc;
	}
	public void setCollectionIdDisc(String collectionIdDisc) {
		this.collectionIdDisc = collectionIdDisc;
	}
	public String getEnvironmentIdDisc() {
		return environmentIdDisc;
	}
	public void setEnvironmentIdDisc(String environmentIdDisc) {
		this.environmentIdDisc = environmentIdDisc;
	}
	public String getUsernameRnR() {
		return usernameRnR;
	}
	public void setUsernameRnR(String usernameRnR) {
		this.usernameRnR = usernameRnR;
	}
	public String getPasswordRnR() {
		return passwordRnR;
	}
	public void setPasswordRnR(String passwordRnR) {
		this.passwordRnR = passwordRnR;
	}
	public String getCollectionNameRnR() {
		return collectionNameRnR;
	}
	public void setCollectionNameRnR(String collectionNameRnR) {
		this.collectionNameRnR = collectionNameRnR;
	}
	public String getRankerIdRnR() {
		return rankerIdRnR;
	}
	public void setRankerIdRnR(String rankerIdRnR) {
		this.rankerIdRnR = rankerIdRnR;
	}
	public String getClusterIdRnR() {
		return clusterIdRnR;
	}
	public void setClusterIdRnR(String clusterIdRnR) {
		this.clusterIdRnR = clusterIdRnR;
	}
	public String getWorkspaceIdRnR() {
		return workspaceIdRnR;
	}
	public void setWorkspaceIdRnR(String workspaceIdRnR) {
		this.workspaceIdRnR = workspaceIdRnR;
	}
	
}