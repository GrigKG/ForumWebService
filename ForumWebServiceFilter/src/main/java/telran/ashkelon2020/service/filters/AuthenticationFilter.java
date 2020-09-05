package telran.ashkelon2020.service.filters;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.forum.dto.exception.UnauthorizedExeption;
import telran.ashkelon2020.forum.dto.exception.UserNotFoundException;
import telran.ashkelon2020.service.security.SecurityService;

@Service
@Order(10)
public class AuthenticationFilter implements Filter {

	@Autowired
	SecurityService securityService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		String token = request.getHeader("Authorization");
		if(checkPathAndMathod(path, method)) {
			String sessionId = request.getSession().getId();
			if(sessionId!=null && token==null) {
				String login =securityService.getUser(sessionId);
				if(login!=null) {
					request = new WrapperRequest(request, login);
							chain.doFilter(request, response);
							return;
				}
			}
			try {
				String login =securityService.getLogin(token);
				request = new WrapperRequest(request, login);
			} catch (UserNotFoundException e) {
				response.sendError(404, e.getMessage());
				return;
			} catch (UnauthorizedExeption e) {
				response.sendError(401);
				return;
			} catch (Exception e) {
				response.sendError(400);
				return;
			}
		}
		chain.doFilter(request, response);
	}
	private boolean checkPathAndMathod(String path, String method) {
	//	if("/account/register".equalsIgnoreCase(path)) return false;
	//	String[] arg = path.split("/");
	//	if(arg[0]=="forum"&&(arg[1]=="post"||arg[1]=="posts")&& "Get".equalsIgnoreCase(method)) return false;
		
		return true;
	}
	private class WrapperRequest extends HttpServletRequestWrapper{
		String user;

		public WrapperRequest(HttpServletRequest request, String user) {
			super(request);
			this.user = user;
		}
		
		@Override
		public Principal getUserPrincipal() {
			return new Principal() {
				
				@Override
				public String getName() {
					return user;
				}
			};
		}
	}
	
}
