package uk.ac.cardiff.raptormua.model;

public class RaptorUser {
	
	private String username;
	public enum UserType{USER,SYSTEM};
	public enum PermissonRole{VIEW,ADMIN};
	private UserType userType;
	private PermissonRole permissonRole;
	
	private Reports savedReports;
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setPermissonRole(PermissonRole permissonRole) {
		this.permissonRole = permissonRole;
	}
	public PermissonRole getPermissonRole() {
		return permissonRole;
	}
	

}
