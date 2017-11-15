package filters;

import businesslogic.UserService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 463849
 */
public class AdminFilter implements Filter {
    
    private FilterConfig filterConfig = null;
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        try {
            // this code executes before the servlet
            // ...
            
            // ensure user is authenticated
            HttpSession session = ((HttpServletRequest)request).getSession();
            UserService us = new UserService();
            String username = (String) session.getAttribute("username");
            
            if (us.get(username).getRole().getRoleID() == 1) {
                // yes, go onwards to the servlet or next filter
                chain.doFilter(request, response);
            } else {
                // get out of here!
                ((HttpServletResponse)response).sendRedirect("home");
            }
            
            // this code executes after the servlet
            // ...
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        
    }
    
}
