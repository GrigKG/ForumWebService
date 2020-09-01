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

import telran.ashkelon2020.forum.dto.exception.ForbiddenException;
import telran.ashkelon2020.forum.dto.exception.UserNotFoundException;
import telran.ashkelon2020.service.security.SecurityService;

@Service
@Order(30)
public class HaveRolesFilter implements Filter {

	@Autowired
	SecurityService securityService;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		if(checkPathAndMathod(path, method)) {
			try {
				if (securityService.isBanned(request.getUserPrincipal().getName())) {
					response.sendError(403, "BANN");
					return;
				}
			} catch (ForbiddenException e) {
				response.sendError(403, "BANN");
				return;
			} catch (UserNotFoundException e) {
				response.sendError(403, "BANN");
				return;
			}
			
		}
		chain.doFilter(request, response);
	}

	private boolean checkPathAndMathod(String path, String method) {
		String[] arg = path.split("/");
		if((arg.length==2||arg.length==3)&&arg[0]=="account") {
			if("login".equalsIgnoreCase(arg[1]) && "Post".equalsIgnoreCase(method)) return true;
			if("user".equalsIgnoreCase(arg[1]) && "Put".equalsIgnoreCase(method)) return true;
		}
		if(arg[0]=="forum"&&arg[1]=="post"
				&& "Post".equalsIgnoreCase(method)
				&& "Delete".equalsIgnoreCase(method)
				&& "Put".equalsIgnoreCase(method)) return true;
		return false;
	}

}
