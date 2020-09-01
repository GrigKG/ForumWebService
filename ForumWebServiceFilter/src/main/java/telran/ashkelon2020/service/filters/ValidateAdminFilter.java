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
@Order(50)
public class ValidateAdminFilter implements Filter {

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
			String user = request.getUserPrincipal().getName();
			if (!securityService.checkHaveRole(user, "Administrator")) {
				response.sendError(403);
				return;
			}
			
		}
		chain.doFilter(request, response);
	}

	private boolean checkPathAndMathod(String path, String method) {
		String[] arg = path.split("/");
		if(arg.length!=5||arg[0]!="account") return false;
		if("user".equalsIgnoreCase(arg[1]) && "role".equalsIgnoreCase(arg[3]) &&("Put".equalsIgnoreCase(method)||"Delete".equalsIgnoreCase(method))) return true;
		return false;
	}

}
