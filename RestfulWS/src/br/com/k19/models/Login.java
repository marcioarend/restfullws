package br.com.k19.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login {

	private int id;
	private String sessionId;
	private String role;
	private String username;
	private String password;

	public Login(){
		
	};
	
	
	
	public Login(int id, String sessionId, String role, String username,
			String password) {
		super();
		this.id = id;
		this.sessionId = sessionId;
		this.role = role;
		this.username = username;
		this.password = password;
	}



	@XmlElement(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@XmlElement(name="role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@XmlElement(name="username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@XmlElement(name="sessionId") 
	public String getSessionId() {
		return sessionId;
	}



	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
