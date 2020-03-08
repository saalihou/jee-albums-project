package sn.esp.dic319.crudusers.beans;

import java.sql.Date;

public class Image {
	private int id;
	private String title;
	private String description;
	private int width;
	private int height;
	private String keywords;
	private Date createdDate;
	private Date updatedDate;
	private String path;
	private Album album;

	public Image(int id, String title, String description, int width, int height, String keywords, Date createdAt,
			Date updatedAt, String path, Album album) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.width = width;
		this.height = height;
		this.keywords = keywords;
		this.createdDate = createdAt;
		this.updatedDate = updatedAt;
		this.path = path;
		this.album = album;
	}

	public Image(String title, String description, int width, int height, String keywords, Date createdAt,
			Date updatedAt, String path, Album album) {
		super();
		this.title = title;
		this.description = description;
		this.width = width;
		this.height = height;
		this.keywords = keywords;
		this.createdDate = createdAt;
		this.updatedDate = updatedAt;
		this.path = path;
		this.album = album;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdAt) {
		this.createdDate = createdAt;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedAt) {
		this.updatedDate = updatedAt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	
	
	
}
