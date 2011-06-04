/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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