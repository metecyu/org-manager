package ey.org.web.filter;

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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class UserRightFilter implements Filter {
	
	/**
	 * 单点登录
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession session = req.getSession();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(session.getServletContext());
		
			Object u = session.getAttribute("user");
			String requestUrl = req.getRequestURI();
			
			if (requestUrl.endsWith("/login.jsp")||requestUrl.endsWith("/login.do")) {				
				chain.doFilter(request, response);				
			} else if (requestUrl.endsWith(".css")
					|| requestUrl.endsWith(".js")
					|| requestUrl.endsWith(".jpg")
					|| requestUrl.endsWith(".JPG")
					|| requestUrl.endsWith(".jpeg")
					|| requestUrl.endsWith(".JPEG")
					|| requestUrl.endsWith(".bmp")
					|| requestUrl.endsWith(".BMP")
					|| requestUrl.endsWith(".gif")
					|| requestUrl.endsWith(".GIF")
					|| requestUrl.endsWith(".png")
					|| requestUrl.endsWith(".PNG")
					|| requestUrl.endsWith(".avi")
					|| requestUrl.endsWith(".AVI")
					|| requestUrl.endsWith(".wmv")
					|| requestUrl.endsWith(".WMV")
					|| requestUrl.endsWith(".wma")
					|| requestUrl.endsWith(".WMA")
					|| requestUrl.endsWith(".mpeg")
					|| requestUrl.endsWith(".MPEG")
					|| requestUrl.endsWith(".rm") || requestUrl.endsWith(".RM")
					|| requestUrl.endsWith(".ram")
					|| requestUrl.endsWith(".RAM")
					|| requestUrl.endsWith(".swf")
					|| requestUrl.endsWith(".SWF")
					|| requestUrl.contains("/ext/beijinHandle.do")) {// 若是图片、视频、css、javascript，则不做过滤
					chain.doFilter(request, response);
			} else if( u!=null){
				chain.doFilter(request, response);
			}else{
				res.sendRedirect(req.getContextPath() + "/login.jsp");
			}
		
	}


	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}

}
