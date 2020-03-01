package sn.esp.dic319.crudusers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.esp.dic319.crudusers.beans.User;
import sn.esp.dic319.crudusers.beans.UserFormDTO;
import sn.esp.dic319.crudusers.db.UsersRepository;
import sn.esp.dic319.crudusers.exceptions.DaoException;

/**
 * Servlet implementation class UserForm
 */
@WebServlet({"/users", "/users/add", "/users/edit", "/users/remove"})
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersRepository usersRepo;
	private static ArrayList<User> users = new ArrayList<User>();
	
	private static final String USERS_FORM_VIEW = "/WEB-INF/usersForm.jsp";
	private static final String USERS_LIST_VIEW = "/WEB-INF/usersList.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Users() {
        super();
        this.usersRepo = UsersRepository.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getServletPath().equalsIgnoreCase("/users/add") || request.getServletPath().equalsIgnoreCase("/users/edit")) {
			String idString = request.getParameter("id");
			// If the request already has a user there is no need to populate it
			if (idString != null && request.getAttribute("user") == null) {
				try {
					User user = usersRepo.findById(Integer.parseInt(idString));
					if (user != null) {
						request.setAttribute("user", new UserFormDTO(user));
					}
				} catch (DaoException e) {
					request.setAttribute("errorMessage", e.getMessage());
				}
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(USERS_FORM_VIEW);
			requestDispatcher.forward(request, response);
		} else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(USERS_LIST_VIEW);
			ArrayList<User> users;
			try {
				users = usersRepo.list();
				request.setAttribute("users", users);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				request.setAttribute("errorMessage", e.getMessage());
			}
			requestDispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getServletPath().equalsIgnoreCase("/users/remove")) {
			String idString = request.getParameter("id");
			try {
				User user = usersRepo.findById(Integer.parseInt(idString));
				if (user != null) {
					usersRepo.removeById(user.getId());
					response.sendRedirect(request.getContextPath() + "/users");
				}
			} catch (DaoException e) {
				request.setAttribute("errorMessage", e.getMessage());
				doGet(request, response);
			}
		} else {
			String idString = request.getParameter("id");
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			String passwordConfirm = request.getParameter("passwordConfirm");
			String name = request.getParameter("name");
			
			UserFormDTO userDto = new UserFormDTO(login, password, passwordConfirm, name);
			
			try {
				ArrayList<String> errorMessages = userDto.validate();
				if (errorMessages.size() > 0) {
					request.setAttribute("errorMessages", errorMessages);
					request.setAttribute("user", userDto);
					doGet(request, response);
					return;
				}
				
				if (idString == null) {
					User user = new User(login, password, name);
						usersRepo.add(user);
						request.setAttribute("successMessage", "Utilisateur ajouté avec succès");
				} else {
					int id = Integer.parseInt(idString);
					User user = usersRepo.findById(id);
					if (user == null) {
						request.setAttribute("errorMessage", "Cet utilisateur n'existe pas");
					} else {
						user.setLogin(login);
						user.setPassword(password);
						user.setName(name);
						usersRepo.update(user);
						request.setAttribute("successMessage", "Utilisateur modifié avec succès");
					}
				}
			} catch (DaoException e) {
				request.setAttribute("user", userDto);
				request.setAttribute("errorMessage", e.getMessage());
			}
			doGet(request, response);
		}
	}
	
	private User getUserById(int id) {
		Optional<User> optionalUser = users.stream().filter(u -> u.getId() == id).findFirst();
		if (!optionalUser.isPresent()) {
			return null;
		} else {
			return optionalUser.get();
		}
	}

}
