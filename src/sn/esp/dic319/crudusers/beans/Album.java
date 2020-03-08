package sn.esp.dic319.crudusers.beans;

public class Album {
	private int id;
	private String name;
	private User owner;
	private boolean isPublic = false;
	
	public Album(String name, User owner) {
		super();
		this.name = name;
		this.owner = owner;
	}
	
	public Album(String name, boolean isPublic, User owner) {
		super();
		this.name = name;
		this.owner = owner;
		this.isPublic = isPublic;
	}
	
	public Album(int id, String name, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
	}
	
	public Album(int id, String name, boolean isPublic, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.setPublic(isPublic);
		this.owner = owner;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
