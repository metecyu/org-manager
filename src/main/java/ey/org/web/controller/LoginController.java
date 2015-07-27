package ey.org.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ey.orgclient.pub.FuncH;
import ey.orgclient.pub.OrgH;
import ey.orgclient.pub.RoleH;
import ey.orgclient.pub.UserH;
import ey.orgclient.pub.model.OrgRole;
import ey.orgclient.pub.model.OrgUser;

/**
 * @Title: LoginController.java
 * @Package com.ydsn.web.controller
 * @Description:  登陆controller控制业务层
 * @author   yzp
 * @date 2014-5-15 下午3:59:43
 * @version V1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	

	@Resource
	private UserH userH;
	@Resource
	private OrgH orgH; 
	@Resource
	private RoleH roleH; 
	@Resource
	private FuncH funcH; 
	
	
	@RequestMapping("/login")
	/**
	 * 密码正确 且 是超级管理员
	 * @Title: login
	 * @author yzp
	 * @date 2014-9-16 上午11:43:33
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String login(HttpServletRequest request){
		//System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径
		String loginId = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		OrgUser orgUser = userH.findByLonginId(loginId);
		boolean isAdmin = false;
		if(orgUser==null){
			request.setAttribute("msg", "1");
			return "/login";	
		}
		// 只有admin角色的用户可以登录
		List<OrgRole> roleList = roleH.findByUserid(orgUser.getId());
		for(OrgRole org:roleList){
			if("admin".equals(org.getId())){
				isAdmin =true;
			}
		}	
		
		if(orgUser!=null && orgUser.getPassword().equals(password) && isAdmin){
			HttpSession session=request.getSession();
			session.setAttribute("user", orgUser);
		    return "redirect:/index.jsp";
		}else{
			request.setAttribute("msg", "1");
			return "/login";	
		}
	}
	@RequestMapping("/loginWithShrio")
	/**
	 * 密码正确 且 是超级管理员
	 * @Title: login
	 * @author yzp
	 * @date 2014-9-16 上午11:43:33
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String loginWithShrio(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		//System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径
		String loginId = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		OrgUser orgUser = userH.findByLonginId(loginId);
		Subject subject = SecurityUtils.getSubject();  
		String error = null;
        UsernamePasswordToken token = new UsernamePasswordToken(loginId, password);  
        try {  
            subject.login(token);  
            boolean isHaveAdmin = subject.hasRole("admin");
            System.out.println(loginId + "是否admin用户："+isHaveAdmin);
        } catch (UnknownAccountException e) {  
            error = "用户名/密码错误";  
        } catch (IncorrectCredentialsException e) {  
            error = "用户名/密码错误";  
        } catch (AuthenticationException e) {  
            //其他错误，比如锁定，如果想单独处理请单独catch处理  
            error = "其他错误：" + e.getMessage();  
        }  
        if(error != null) {//出错了，返回登录页面  
        	request.setAttribute("error", error);  
        	return "/login";  
        } else {//登录成功  
        	//request.getRequestDispatcher("/index.jsp").forward(request, response);  
        	return "redirect:/index.jsp";
        }  
        //return "/login";	
	}
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request){
		HttpSession session=request.getSession();
		session.removeAttribute("user");
		return "/login";
	}
	@RequestMapping("/loginoutWithShrio")
	public String loginoutWithShrio(HttpServletRequest request){
		SecurityUtils.getSubject().logout();
		return "/login";
	}
	
}
