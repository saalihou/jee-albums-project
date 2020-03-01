package sn.esp.dic319.crudusers.beans;

public class User {
	private int id;
	private String login;
	private String password;
	private String name;
	private static int maxId = 0;
	
	public User() {
		maxId++;
		this.id = maxId;
	}
	
	public User(String login, String password, String name) {
		this();
		this.setLogin(login);
		this.setPassword(password);
		this.setName(name);
	}
	
	public User(int id, String login, String password, String name) {
		this();
		this.setLogin(login);
		this.setPassword(password);
		this.setName(name);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
