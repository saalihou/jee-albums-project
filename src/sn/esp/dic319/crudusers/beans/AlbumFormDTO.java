package sn.esp.dic319.crudusers.beans;

import java.util.ArrayList;

public class AlbumFormDTO {
	private int id;
	private String name = "";
	private boolean isPublic = false;
	
	public AlbumFormDTO(String name) {
		this.setName(name);
	}
	
	public AlbumFormDTO(int id, String name, boolean isPublic) {
		this.id = id;
		this.setName(name);
		this.setPublic(isPublic);
	}
	
	public int getId() {
		return id;
	}

	public AlbumFormDTO(Album album) {
		this(album.getId(), album.getName(), album.isPublic());
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> validate() {
		ArrayList<String> errorMessages = new ArrayList<String>();
		if (isEmpty(this.getName())) {
			errorMessages.add("Vous devez renseigner un nom pour l'album");
		}
		return errorMessages;
	}
	
	private boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
