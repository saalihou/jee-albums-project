package sn.esp.dic319.crudusers.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sn.esp.dic319.crudusers.beans.User;
import sn.esp.dic319.crudusers.exceptions.DaoException;

public class UsersRepository {
	private static UsersRepository instance;
	private Connection conn;
	
	public UsersRepository() {
		try {
			this.conn = DBConnectionManager.getInstance();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static UsersRepository getInstance() {
		if (instance == null) {
			instance = new UsersRepository();
		}
		return instance;
	}
	
	public ArrayList<User> list() throws DaoException {
		try {
			ArrayList<User> list = new ArrayList<User>();
			String sql = "SELECT * FROM users";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"));
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la liste des utilisateurs");
		}
	}
	
	public User add(User u) throws DaoException {
		try {
			String sql = "INSERT INTO users(login, password, name) VALUES(?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, u.getLogin());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getName());
			int id = stmt.executeUpdate();
			User createdUser = new User(id, u.getLogin(), u.getPassword(), u.getName());
			return createdUser;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de l'ajout de l'utilisateur");
		}
	}
	
	public User findById(int id) throws DaoException {
		try {
			String sql = "SELECT * FROM users WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User u = new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"));
				return u;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la recuperation de l'utilisateur");
		}
	}
	
	public User findByLogin(String login) throws DaoException {
		try {
			String sql = "SELECT * FROM users WHERE login=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, login);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User u = new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"));
				return u;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la recuperation de l'utilisateur");
		}
	}
	
	public void update(User u) throws DaoException {
		try {
			String sql = "UPDATE users SET login=?, password=?, name=? WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getLogin());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getName());
			stmt.setInt(4, u.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la modification de l'utilisateur");
		}
	}
	
	public void removeById(int id) throws DaoException {
		try {
			String sql = "DELETE FROM users WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la suppression de l'utilisateur");
		}
	}
}
