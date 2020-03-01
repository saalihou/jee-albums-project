package sn.esp.dic319.crudusers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sn.esp.dic319.crudusers.beans.User;
import sn.esp.dic319.crudusers.db.UsersRepository;
import sn.esp.dic319.crudusers.exceptions.DaoException;

/**
 * Servlet implementation class Auth
 */
@WebServlet({"/auth", "/auth/login", "/auth/logout"})
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_FORM_VIEW = "/WEB-INF/loginForm.jsp";
	private UsersRepository usersRepo;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auth() {
        super();
        this.usersRepo = UsersRepository.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getServletPath().equalsIgnoreCase("/auth/logout")) {
			HttpSession session = request.getSession();
			session.removeAttribute("currentUser");
			response.sendRedirect(request.getContextPath());
			return;
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOGIN_FORM_VIEW);
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			User user;
				user = this.usersRepo.findByLogin(login);
			if (user != null && user.getPassword().equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", user);
				response.sendRedirect(request.getContextPath());
				return;
			} else {
				request.setAttribute("errorMessage", "Informations de connexion invalides");
			}
		} catch (DaoException e) {
			request.setAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
