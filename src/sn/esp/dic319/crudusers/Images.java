package sn.esp.dic319.crudusers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import sn.esp.dic319.crudusers.beans.Image;
import sn.esp.dic319.crudusers.db.AlbumsRepository;
import sn.esp.dic319.crudusers.db.ImagesRepository;
import sn.esp.dic319.crudusers.exceptions.DaoException;

/**
 * Servlet implementation class Images
 */
@WebServlet("/images/*")
@MultipartConfig
public class Images extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AlbumsRepository albumsRepo;
	private ImagesRepository imagesRepo;

	public static final File UPLOAD_LOCATION = new File(System.getenv("UPLOAD_LOCATION"));

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Images() {
        super();
        this.albumsRepo = AlbumsRepository.getInstance();
        this.imagesRepo = ImagesRepository.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File file = new File(UPLOAD_LOCATION, filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part image = request.getPart("image");
		String fileName = image.getSubmittedFileName();
		
		InputStream initialStream = image.getInputStream();
	
		String extension = fileName.substring(fileName.lastIndexOf("."));
	    File targetFile = File.createTempFile("image-", extension, UPLOAD_LOCATION);
		OutputStream outStream = new FileOutputStream(targetFile);
	    
	    byte[] buffer = new byte[8 * 1024];
	    int bytesRead;
	    while ((bytesRead = initialStream.read(buffer)) != -1) {
	        outStream.write(buffer, 0, bytesRead);
	    }
	    IOUtils.closeQuietly(initialStream);
	    IOUtils.closeQuietly(outStream);
		System.out.println(targetFile.getAbsolutePath());
		
		try {
			Image i = new Image(request.getParameter("title"), request.getParameter("description"), 0, 0, request.getParameter("keywords"), new Date(Date.from(Instant.now()).getTime()), new Date(Date.from(Instant.now()).getTime()), targetFile.getName(), albumsRepo.findById(Integer.parseInt(request.getParameter("albumId"))));
			imagesRepo.add(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
		}
		
		request.setAttribute("successMessage", "Image envoyée avec succès");
		response.sendRedirect(request.getHeader("referer"));
	}

}
