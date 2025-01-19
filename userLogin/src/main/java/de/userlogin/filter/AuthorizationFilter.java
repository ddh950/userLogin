package de.userlogin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static de.userlogin.jsf.Login.INDEX_XHTML_URL;
import static de.userlogin.jsf.Login.USER_IS_LOGGED_IN;

import java.io.IOException;


 //  filter for all requests that contain the .xhtml extension.
 
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public static final String JAVAX_FACES_RESOURCE_URL = "javax.faces.resource";

    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

   
     // executed for each HttpRequest matches the WebFilter(urlPatterns = {"*.xhtml"}).
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);

        if (isPrivatePage(url) && !userIsLoggedIn(session)) {
            redirectToLogin((HttpServletResponse) response);
        } else {
            chain.doFilter(request, response);
        }
    }


    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(INDEX_XHTML_URL);
    }

    
    		// Check if the HttpSession has the USER_IS_LOGGED_IN attribute.
     
    private boolean userIsLoggedIn(HttpSession session) {
        boolean sessionHasAttributeUserLoggedIn = session != null && session.getAttribute(USER_IS_LOGGED_IN) != null;
        return sessionHasAttributeUserLoggedIn && session.getAttribute(USER_IS_LOGGED_IN).equals("true");
    }

   
 // Check if the requested page is the index page or a resource.
     
    private boolean isPrivatePage(String url) {
        return !(url.contains(INDEX_XHTML_URL) || url.contains(JAVAX_FACES_RESOURCE_URL));
    }


    public void destroy() {
        // do nothing
    }
}
