package br.com.junior.repository.filter;

import java.io.Serializable;

public class UserFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String username;

	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
