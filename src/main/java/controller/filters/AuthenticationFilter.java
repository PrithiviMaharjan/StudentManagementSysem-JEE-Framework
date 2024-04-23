package controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.StringUtils;

public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

	    // Cast request and response objects to HttpServletRequest and HttpServletResponse for type safety
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;

	    // Get the requested URI
	    String uri = req.getRequestURI();

	    // Allow access to static resources (CSS) and the index page without checking login
	    if (uri.endsWith(".css") || uri.endsWith(".png") || uri.endsWith(".jpg")) {
	        chain.doFilter(request, response);
	        return;
	    }
	    
	    if(uri.endsWith(StringUtils.URL_INDEX) || uri.endsWith("/")) {
			request.getRequestDispatcher(StringUtils.SERVLET_URL_HOME).forward(request, response);
//	        res.sendRedirect(req.getContextPath() + StringUtils.SERVLET_URL_HOME);
	        return;
    	}

	    // Separate flags for login, login/logout servlets, and register page/servlet for better readability
	    boolean isLogin = uri.endsWith(StringUtils.PAGE_URL_LOGIN);
	    boolean isLoginServlet = uri.endsWith(StringUtils.SERVLET_URL_LOGIN);
	    boolean isLogoutServlet = uri.endsWith(StringUtils.SERVLET_URL_LOGOUT);
	    boolean isHomeServlet = uri.endsWith(StringUtils.SERVLET_URL_HOME);
	 
	    boolean isRegisterPage = uri.endsWith(StringUtils.PAGE_URL_REGISTER);
	    boolean isRegisterServlet = uri.endsWith(StringUtils.SERVLET_URL_REGISTER);

	    // Check if a session exists and if the username attribute is set to determine login status
	    HttpSession session = req.getSession(false); // Don't create a new session if one doesn't exist
	    boolean isLoggedIn = session != null && session.getAttribute(StringUtils.USERNAME) != null;

	    // Redirect to login if user is not logged in and trying to access a protected resource
	    if (!isLoggedIn && !isHomeServlet && !(isLogin || isLoginServlet || isRegisterPage || isRegisterServlet)) {
	        res.sendRedirect(req.getContextPath() + StringUtils.PAGE_URL_LOGIN);
	    } else if (isLoggedIn && !isHomeServlet && !(!isLogin || isLogoutServlet)) { // Redirect logged-in users to the index page if trying to access login page again
	        res.sendRedirect(req.getContextPath() + StringUtils.URL_INDEX);
	    } else {
	        // Allow access to the requested resource if user is logged in or accessing unprotected resources
	        chain.doFilter(request, response);
	    }

	}

	@Override
	public void destroy() {
	}
}
