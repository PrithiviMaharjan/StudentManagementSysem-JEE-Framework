package controller.servlets;

import java.io.IOException;

import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.database.DBController;
import model.StudentModel;
import utils.StringUtils;
import utils.ValidationUtil;

/**
 * This Servlet class handles student registration requests. It extracts student
 * information from the registration form submission, performs basic data
 * validation (to be implemented), and attempts to register the student in the
 * database using a `DBController`. The user is redirected to the login page
 * upon successful registration.
 *
 * @author Prithivi Maharjan (prithivi.maharjan18@gmail.com)
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_REGISTER })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class RegisterStudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final DBController dbController;

	public RegisterStudentServlet() {
		this.dbController = new DBController();
	}

	/**
	 * Handles HTTP POST requests for student registration.
	 *
	 * @param request  The HttpServletRequest object containing registration form
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		// Extract student information from request parameters
		String firstName = request.getParameter(StringUtils.FIRST_NAME);
		String lastName = request.getParameter(StringUtils.LAST_NAME);
		LocalDate dob = LocalDate.parse(request.getParameter(StringUtils.BIRTHDAY));
		String gender = request.getParameter(StringUtils.GENDER);
		String email = request.getParameter(StringUtils.EMAIL);
		String phoneNumber = request.getParameter(StringUtils.PHONE_NUMBER);
		String subject = request.getParameter(StringUtils.SUBJECT);
		String username = request.getParameter(StringUtils.USERNAME);
		String password = request.getParameter(StringUtils.PASSWORD);
		Part imagePart = request.getPart("image");	

		
		// Create a StudentModel object to hold student information
		StudentModel student = new StudentModel(firstName, lastName, dob, gender, email, phoneNumber, subject, username,
				password, imagePart);
		
		
		// Implement data validation here (e.g., check for empty fields, email format,
		// etc.)
		if(!ValidationUtil.isTextOnly(firstName) ||
				!ValidationUtil.isTextOnly(lastName) ||
				!ValidationUtil.isAlphanumeric(username) ||
				!ValidationUtil.isEmail(email) ||
				!ValidationUtil.isNumbersOnly(phoneNumber) ||
				!ValidationUtil.isGenderMatches(gender)) {
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_INCORRECT_DATA);
			request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER).forward(request, response);
		}

		// Call DBController to register the student
		int result = dbController.registerStudent(student);

		if (result == 1) {
			
			// Get the image file name from the student object (assuming it was extracted earlier)
			String fileName = student.getImageUrlFromPart();

			// Check if a filename exists (not empty or null)
			if (!fileName.isEmpty() && fileName != null) {
			  // Construct the full image save path by combining the directory path and filename
			  String savePath = StringUtils.IMAGE_DIR_USER;
			  imagePart.write(savePath + fileName);  // Save the uploaded image to the specified path
			}

			request.setAttribute(StringUtils.MESSAGE_SUCCESS, StringUtils.MESSAGE_SUCCESS_REGISTER);
			response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_LOGIN+ "?success=true");
		} else if (result == 0) {
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_REGISTER);
			request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER).forward(request, response);
		} else {
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER).forward(request, response);
		}
	}
}