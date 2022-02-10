package CRM.servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.utils.Authentification;



@WebFilter(urlPatterns = {""})
public class LoginFilter extends HttpFilter implements Filter {
       
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if(req.getMethod().equalsIgnoreCase("GET")) {
			chain.doFilter(request, response);
		
		}else {
			if(Authentification.isAuthentificated(req) == null) {
			resp.setCharacterEncoding("UTF-8");
			resp.setStatus(405);
			resp.getWriter().write("Le token d'authentification n'est pas bon");
			}else {
				chain.doFilter(request, response);
			}
		}
	}
}
