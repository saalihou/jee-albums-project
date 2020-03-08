package sn.esp.dic319.crudusers.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sn.esp.dic319.crudusers.beans.Image;
import sn.esp.dic319.crudusers.beans.Album;
import sn.esp.dic319.crudusers.exceptions.DaoException;

public class ImagesRepository {
	private static ImagesRepository instance;
	private AlbumsRepository albumsRepo;
	private Connection conn;

	public ImagesRepository() {
		try {
			this.conn = DBConnectionManager.getInstance();
			this.albumsRepo = AlbumsRepository.getInstance();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static ImagesRepository getInstance() {
		if (instance == null) {
			instance = new ImagesRepository();
		}
		return instance;
	}

	public ArrayList<Image> list() throws DaoException {
		try {
			ArrayList<Image> list = new ArrayList<Image>();
			String sql = "SELECT * FROM images";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Image user = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("width"), rs.getInt("height"), rs.getString("keywords"), rs.getDate("createdDate"),
						rs.getDate("updatedDate"), rs.getString("path"), albumsRepo.findById(rs.getInt("albumId")));
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la liste des images");
		}
	}

	public Image add(Image i) throws DaoException {
		try {
			String sql = "INSERT INTO images(title, description, width, height, keywords, createdDate, updatedDate, path, albumId) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, i.getTitle());
			stmt.setString(2, i.getDescription());
			stmt.setInt(3, i.getWidth());
			stmt.setInt(4, i.getHeight());
			stmt.setString(5, i.getKeywords());
			stmt.setDate(6, i.getCreatedDate());
			stmt.setDate(7, i.getUpdatedDate());
			stmt.setString(8, i.getPath());
			stmt.setInt(9, i.getAlbum().getId());
			int id = stmt.executeUpdate();
			Image createdUser = new Image(id, i.getTitle(), i.getDescription(), i.getWidth(), i.getHeight(), i.getKeywords(), i.getCreatedDate(), i.getUpdatedDate(), i.getPath(), i.getAlbum());
			return createdUser;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de l'ajout de l'image");
		}
	}

	public Image findById(int id) throws DaoException {
		try {
			String sql = "SELECT * FROM images WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Image i = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("width"), rs.getInt("height"), rs.getString("keywords"), rs.getDate("createdDate"),
						rs.getDate("updatedDate"), rs.getString("path"), albumsRepo.findById(rs.getInt("albumId")));
				return i;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la recuperation de l'image");
		}
	}

	public ArrayList<Image> findByAlbum(Album album) throws DaoException {
		ArrayList<Image> images = new ArrayList<Image>();
		try {
			String sql = "SELECT * FROM images WHERE albumId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, album.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Image i = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("width"), rs.getInt("height"), rs.getString("keywords"), rs.getDate("createdDate"),
						rs.getDate("updatedDate"), rs.getString("path"), albumsRepo.findById(rs.getInt("albumId")));
				images.add(i);
			}
			return images;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la recuperation des images");
		}
	}

	public void update(Image i) throws DaoException {
		try {
			String sql = "UPDATE images SET title=?, description=?, width=?, height=?, keywords=?, createdDate=?, updatedDate=?, path=?, albumId=? WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, i.getTitle());
			stmt.setString(2, i.getDescription());
			stmt.setInt(3, i.getWidth());
			stmt.setInt(4, i.getHeight());
			stmt.setString(5, i.getKeywords());
			stmt.setDate(6, i.getCreatedDate());
			stmt.setDate(7, i.getUpdatedDate());
			stmt.setString(8, i.getPath());
			stmt.setInt(9, i.getAlbum().getId());
			stmt.setInt(10, i.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la modification de l'image");
		}
	}

	public void removeById(int id) throws DaoException {
		try {
			String sql = "DELETE FROM images WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la suppression de l'image");
		}
	}
}
