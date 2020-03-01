package sn.esp.dic319.crudusers.beans;

import java.util.ArrayList;

public class UserFormDTO {
	private String login = "";
	private String password = "";
	private String passwordConfirm = "";
	private String name = "";
	
	public UserFormDTO(String login, String password, String passwordConfirm, String name) {
		this.setLogin(login);
		this.setPassword(password);
		this.setPasswordConfirm(passwordConfirm);
		this.setName(name);
	}
	
	public UserFormDTO(User user) {
		this(user.getLogin(), user.getPassword(), user.getPassword(), user.getName());
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
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> validate() {
		ArrayList<String> errorMessages = new ArrayList<String>();
		if (isEmpty(this.getLogin())) {
			errorMessages.add("Vous devez renseigner un login");
		}
		if (isEmpty(this.getPassword())) {
			errorMessages.add("Vous devez renseigner un mot de passe");
		}
		if (isEmpty(this.getName())) {
			errorMessages.add("Vous devez renseigner vos prénoms et nom");
		}
		if (isEmpty(this.getPasswordConfirm()) || !this.getPasswordConfirm().equals(this.getPassword())) {
			errorMessages.add("La confirmation de mot de passe est différente du mot de passe");
		}
		return errorMessages;
	}
	
	private boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
}
