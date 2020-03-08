package sn.esp.dic319.crudusers.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sn.esp.dic319.crudusers.beans.Album;
import sn.esp.dic319.crudusers.beans.User;
import sn.esp.dic319.crudusers.exceptions.DaoException;

public class AlbumsRepository {
	private static AlbumsRepository instance;
	private UsersRepository usersRepo;
	private Connection conn;
	
	public AlbumsRepository() {
		try {
			this.conn = DBConnectionManager.getInstance();
			this.usersRepo = UsersRepository.getInstance();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static AlbumsRepository getInstance() {
		if (instance == null) {
			instance = new AlbumsRepository();
		}
		return instance;
	}
	
	public ArrayList<Album> list() throws DaoException {
		try {
			ArrayList<Album> list = new ArrayList<Album>();
			String sql = "SELECT * FROM albums";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Album user = new Album(rs.getInt("id"), rs.getString("name"), rs.getBoolean("isPublic"), usersRepo.findById(rs.getInt("ownerId")));
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la liste des albums");
		}
	}
	
	public Album add(Album a) throws DaoException {
		try {
			String sql = "INSERT INTO albums(name, isPublic, ownerId) VALUES(?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, a.getName());
			stmt.setBoolean(2, a.isPublic());
			stmt.setInt(3, a.getOwner().getId());
			int id = stmt.executeUpdate();
			Album createdUser = new Album(id, a.getName(), a.isPublic(), a.getOwner());
			return createdUser;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de l'ajout de l'album");
		}
	}
	
	public Album findById(int id) throws DaoException {
		try {
			String sql = "SELECT * FROM albums WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Album a = new Album(rs.getInt("id"), rs.getString("name"), rs.getBoolean("isPublic"), usersRepo.findById(rs.getInt("ownerId")));
				return a;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la recuperation de l'album");
		}
	}

	public ArrayList<Album> findByOwner(User user) throws DaoException {
		ArrayList<Album> albums = new ArrayList<Album>();
		try {
			String sql = "SELECT * FROM albums WHERE ownerId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Album a = new Album(rs.getInt("id"), rs.getString("name"), rs.getBoolean("isPublic"), usersRepo.findById(rs.getInt("ownerId")));
				albums.add(a);
			}
			return albums;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la recuperation des albums");
		}
	}

	public ArrayList<Album> findPublic() throws DaoException {
		ArrayList<Album> albums = new ArrayList<Album>();
		try {
			String sql = "SELECT * FROM albums WHERE isPublic=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, true);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Album a = new Album(rs.getInt("id"), rs.getString("name"), rs.getBoolean("isPublic"), usersRepo.findById(rs.getInt("ownerId")));
				albums.add(a);
			}
			return albums;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la recuperation des albums");
		}
	}
	
//	public Album findByLogin(String login) throws DaoException {
//		try {
//			String sql = "SELECT * FROM users WHERE login=?";
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			stmt.setString(1, login);
//			ResultSet rs = stmt.executeQuery();
//			if (rs.next()) {
//				Album u = new Album(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"));
//				return u;
//			}
//			return null;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DaoException("Erreur lors de la recuperation de l'utilisateur");
//		}
//	}
	
	public void update(Album a) throws DaoException {
		try {
			String sql = "UPDATE albums SET name=?, isPublic=?, ownerId=? WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, a.getName());
			stmt.setBoolean(2, a.isPublic());
			stmt.setInt(3, a.getOwner().getId());
			stmt.setInt(4, a.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la modification de l'album");
		}
	}
	
	public void removeById(int id) throws DaoException {
		try {
			String sql = "DELETE FROM albums WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la suppression de l'album");
		}
	}
}
