package telran.ashkelon2020.service.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.service.security.SecurityService;

@Service
@Order(40)
public class ValidateUserFilter implements Filter {

	@Autowired
	SecurityService securityService;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		if (checkPathAndMathod(path, method)) {
			String user = request.getUserPrincipal().getName();
			String login = path.split("/")[3];
			if (!user.equals(login)) {
				response.sendError(403);
				return;

			}
			
		}
		chain.doFilter(request, response);
	}

	private boolean checkPathAndMathod(String path, String method) {
		String[] arg = path.split("/");
		if(arg.length==3&&arg[0]=="account") {
		if("user".equalsIgnoreCase(arg[1]) && ("Put".equalsIgnoreCase(method)||"Delete".equalsIgnoreCase(method))) return true;
		}
		if(arg[0]=="forum"&&arg[1]=="post"&&arg[3]!="like"
				&& "Post".equalsIgnoreCase(method)
				&& "Delete".equalsIgnoreCase(method)
				&& "Put".equalsIgnoreCase(method)) return true;
		return false;
	}

}
