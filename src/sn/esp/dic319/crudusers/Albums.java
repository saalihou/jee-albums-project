package sn.esp.dic319.crudusers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import sn.esp.dic319.crudusers.beans.Album;
import sn.esp.dic319.crudusers.beans.AlbumFormDTO;
import sn.esp.dic319.crudusers.beans.Image;
import sn.esp.dic319.crudusers.beans.User;
import sn.esp.dic319.crudusers.beans.UserFormDTO;
import sn.esp.dic319.crudusers.db.AlbumsRepository;
import sn.esp.dic319.crudusers.db.ImagesRepository;
import sn.esp.dic319.crudusers.db.UsersRepository;
import sn.esp.dic319.crudusers.exceptions.DaoException;

/**
 * Servlet implementation class Albums
 */
@WebServlet({"/albums", "/albums/me", "/albums/add", "/albums/edit", "/albums/remove", "/albums/upload"})
public class Albums extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AlbumsRepository albumsRepo;
	private ImagesRepository imagesRepo;
       
	private static final String ALBUMS_LIST_VIEW = "/WEB-INF/albumsList.jsp";
	private static final String ALBUMS_FORM_VIEW = "/WEB-INF/albumsForm.jsp";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Albums() {
        super();
        this.albumsRepo = AlbumsRepository.getInstance();
        this.imagesRepo = ImagesRepository.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getServletPath().equalsIgnoreCase("/albums/add") || request.getServletPath().equalsIgnoreCase("/albums/edit")) {
			String idString = request.getParameter("id");
			// If the request already has a user there is no need to populate it
			if (idString != null && request.getAttribute("album") == null) {
				try {
					Album album = albumsRepo.findById(Integer.parseInt(idString));
					if (album != null) {
						User currentUser = (User) request.getSession().getAttribute("currentUser");
						if (currentUser == null) {
							request.setAttribute("readonly", true);
						} else {
							if (album.getOwner().getId() != currentUser.getId()) {
								request.setAttribute("readonly", true);
							}
						}
						ArrayList<Image> images = imagesRepo.findByAlbum(album);
						request.setAttribute("album", new AlbumFormDTO(album));
						request.setAttribute("images", images);
					}
				} catch (DaoException e) {
					request.setAttribute("errorMessage", e.getMessage());
				}
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(ALBUMS_FORM_VIEW);
			requestDispatcher.forward(request, response);
		} else if (request.getServletPath().equalsIgnoreCase("/albums/me")) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(ALBUMS_LIST_VIEW);
			ArrayList<Album> albums;
			try {
				User currentUser = (User) request.getSession().getAttribute("currentUser");
				albums = albumsRepo.findByOwner(currentUser);
				System.out.println(albums);
				request.setAttribute("albums", albums);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", e.getMessage());
			}
			requestDispatcher.forward(request, response);
		} else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(ALBUMS_LIST_VIEW);
			ArrayList<Album> albums;
			try {
				albums = albumsRepo.findPublic();
				System.out.println(albums);
				request.setAttribute("albums", albums);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", e.getMessage());
			}
			requestDispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getServletPath().equalsIgnoreCase("/albums/remove")) {
			String idString = request.getParameter("id");
			try {
				Album album = albumsRepo.findById(Integer.parseInt(idString));
				if (album != null) {
					albumsRepo.removeById(album.getId());
					response.sendRedirect(request.getContextPath() + "/albums");
				}
			} catch (DaoException e) {
				request.setAttribute("errorMessage", e.getMessage());
				doGet(request, response);
			}
		} else if (request.getServletPath().equalsIgnoreCase("/albums/add") || request.getServletPath().equalsIgnoreCase("/albums/edit")) {
			String idString = request.getParameter("id");
			String name = request.getParameter("name");
			boolean isPublic = request.getParameter("isPublic") != null && request.getParameter("isPublic").equals("on") ? true : false;
			System.out.println(idString);
			AlbumFormDTO albumDto = new AlbumFormDTO(
					idString != null ? Integer.parseInt(idString) : 0,
					name,
					isPublic
				);
			
			try {
				ArrayList<String> errorMessages = albumDto.validate();
				if (errorMessages.size() > 0) {
					request.setAttribute("errorMessages", errorMessages);
					request.setAttribute("album", albumDto);
					doGet(request, response);
					return;
				}
				
				if (idString == null) {
					User currentUser = (User) request.getSession().getAttribute("currentUser");
					Album album = new Album(name, isPublic, currentUser);
					albumsRepo.add(album);
					request.setAttribute("successMessage", "Album ajouté avec succès");
				} else {
					int id = Integer.parseInt(idString);
					Album album = albumsRepo.findById(id);
					if (album == null) {
						request.setAttribute("errorMessage", "Cet album n'existe pas");
					} else {
						album.setName(name);
						album.setPublic(isPublic);
						albumsRepo.update(album);
						request.setAttribute("successMessage", "Album modifié avec succès");
					}
				}
			} catch (DaoException e) {
				request.setAttribute("album", albumDto);
				request.setAttribute("errorMessage", e.getMessage());
			}
			doGet(request, response);
		}
	}

}
