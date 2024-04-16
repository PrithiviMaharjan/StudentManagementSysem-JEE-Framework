package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DBController;
import model.StudentModel;
import utils.StringUtils;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_HOME })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBController controller = new DBController();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<StudentModel> students = controller.getAllStudentsInfo();
		request.setAttribute(StringUtils.STUDENT_LISTS, students);
		request.getRequestDispatcher(StringUtils.URL_INDEX).forward(request, response);
	}
}