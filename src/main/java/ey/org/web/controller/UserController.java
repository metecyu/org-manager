package ey.org.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ey.org.bean.Pager;
import ey.org.model.Org;
import ey.org.model.User;
import ey.org.service.OrgService;
import ey.org.service.UserService;
import ey.org.utils.FinalValue;
import ey.org.utils.ParamUtil;
import ey.org.utils.ResponseUtil;

/**
 * 用户管理 Controller
 * @ClassName: OrgController
 * @author yzp
 * @date 2014-8-28 上午11:06:54
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource 
	private OrgService orgService;
	@Resource 
	private UserService userService;
	

	/**
	 * 查看组织列表
	 * @Title: beginViewOrg
	 * @author yzp
	 * @date 2014-8-28 上午11:08:06
	 * @throws 
	 * @Description:
	 */
	@RequestMapping("/beginViewUser")
	public String beginViewUser(HttpServletRequest request){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"),FinalValue.ROOT_ORG_ID);
		
		String userCurrentPage = ParamUtil.getParamValue(request.getParameter("currentPage"),"1");
		Org org = orgService.findById(orgid);		
		List<Org> orgList = orgService.getAllOrgList();
		List<User> pageLevUserList=userService.getPageUserList(orgid, Integer.parseInt(userCurrentPage));
		List<User> levUserList=userService.getUserList(orgid);
		//处理 空 的情况
		if(pageLevUserList.size()==0){
			userCurrentPage="-1";
		}
		Pager userPager=new Pager();
		userPager.setMaxRows(10);
		userPager.setTotalCount(userService.getPageCount(orgid));
		userPager.setCurrentPage(Integer.parseInt(userCurrentPage));
		
		request.setAttribute("userPager", userPager);
		request.setAttribute("pageLevUserList", pageLevUserList);
		request.setAttribute("orgList", orgList);
		request.setAttribute("levUserList", levUserList);
		request.setAttribute("org", org);
		request.setAttribute("orgid", orgid);		
		return "./moudle/user/viewUser";
	}
	
	/**
	 * 用户排序
	 * @Title: beginSortUser
	 * @author (wmc)
	 * @date 2015-1-8 下午2:52:12
	 * @param request
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/beginSortUser")
	public String beginSortUser(HttpServletRequest request){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"),FinalValue.ROOT_ORG_ID);
		//String userCurrentPage = ParamUtil.getParamValue(request.getParameter("currentPage"),"1");
		Org org = orgService.findById(orgid);		
		List<Org> orgList = orgService.getAllOrgList();
		//List<User> pageLevUserList=userService.getPageUserList(orgid, Integer.parseInt(userCurrentPage));
		List<User> levUserList=userService.getUserList(orgid);
		//处理 空 的情况
		/*if(pageLevUserList.size()==0){
			userCurrentPage="-1";
		}
		Pager userPager=new Pager();
		userPager.setMaxRows(10);
		userPager.setTotalCount(userService.getPageCount(orgid));
		userPager.setCurrentPage(Integer.parseInt(userCurrentPage));
		
		request.setAttribute("userPager", userPager);
		request.setAttribute("pageLevUserList", pageLevUserList);*/
		request.setAttribute("orgList", orgList);
		request.setAttribute("levUserList", levUserList);
		request.setAttribute("org", org);
		request.setAttribute("orgid", orgid);
		return "./moudle/user/sortUser2";
	}
	
	/**
	 * 跳转至新建组织界面
	 * @Title: beginAddOrg
	 * @author yzp
	 * @date 2014-9-4 上午11:01:28
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/beginAddUser")
	public String beginAddUser(HttpServletRequest request){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		request.setAttribute("orgid", orgid);
		return "./moudle/user/addUser";
	}
	/**
	 * 添加部门
	 * @Title: addOrg
	 * @author yzp
	 * @date 2014-9-1 下午1:50:00
	 * @return String   0：失败  ; 1：成功 
	 * @throws 
	 * @Description: 添加组织
	 */
	@RequestMapping("/addUser")
	public void addUser(HttpServletRequest request,HttpServletResponse response){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		String username = ParamUtil.getParamValue(request.getParameter("username"), "");
		String loginid = ParamUtil.getParamValue(request.getParameter("loginid"), "");
		String pwd = ParamUtil.getParamValue(request.getParameter("pwd"), "");
		String email= ParamUtil.getParamValue(request.getParameter("email"), "");
		String gender= ParamUtil.getParamValue(request.getParameter("gender"), "");
		String mobile= ParamUtil.getParamValue(request.getParameter("mobile"), "");
		String ret = "0";
		
		SimpleHash hash= new SimpleHash("MD5", pwd, "", 2);
		pwd = hash.toHex();
		try {
			User org = userService.addUser(orgid, username, loginid, pwd, email, gender,mobile);
			ret = "1";
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseUtil.writeResponseStr(response, ret);		
	}
	/**
	 * 跳转至编辑组织界面
	 * @Title: beginEditOrg
	 * @author yzp
	 * @date 2014-9-4 上午11:00:55
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/beginEditUser")
	public String beginEditUser(HttpServletRequest request){
		String userid = ParamUtil.getParamValue(request.getParameter("userid"), "");
		User user = userService.findById(userid);
		request.setAttribute("user", user);
		return "./moudle/user/editUser";
	}
	
	/**
	 * 更新组织信息
	 * @Title: updateOrg
	 * @author yzp
	 * @date 2014-9-4 上午11:01:43
	 * @return String   0：失败  ; 1：成功 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	 
	@RequestMapping("/updateUser")
	public void updateUser(HttpServletRequest request,HttpServletResponse response){
		String userid = ParamUtil.getParamValue(request.getParameter("userid"), "");
		String username = ParamUtil.getParamValue(request.getParameter("username"), "");
		String loginid = ParamUtil.getParamValue(request.getParameter("loginid"), "");
		// String pwd = ParamUtil.getParamValue(request.getParameter("pwd"), "");
		String mobile= ParamUtil.getParamValue(request.getParameter("mobile"), "");
		String email= ParamUtil.getParamValue(request.getParameter("email"), "");
		String gender=ParamUtil.getParamValue(request.getParameter("gender"), "");
		String ret = "0";
		
		User org = userService.updateUser(userid, username, loginid, "noUpdatePassword", email,gender, mobile); 
		ret = "1";
		
		ResponseUtil.writeResponseStr(response, ret);		
	}
	/**
	 * 删除部门
	 * @Title: deleteOrg
	 * @author yzp
	 * @date 2014-9-4 上午11:02:59
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/deleteUser")
	public void deleteUser(HttpServletRequest request,HttpServletResponse response){
		String userid = ParamUtil.getParamValue(request.getParameter("userid"), "");
		String ret = "0";
		User user = userService.deleteUser(userid); 
		ret = "1";
		ResponseUtil.writeResponseStr(response, ret);		
	}
	/**
	 * 是否唯一登陆名称
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午5:16:23
	 * return String 1:唯一，0：不唯一 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/isUniCode")
	public void isUniCode(HttpServletRequest request,HttpServletResponse response){
		String loginid = ParamUtil.getParamValue(request.getParameter("loginid"), "");
		String userid = ParamUtil.getParamValue(request.getParameter("userid"), "");
		String ret = "0";//不唯一
		boolean isUni = this.userService.isUniCode(loginid,userid);
		if(isUni){
			ret = "1";// 唯一
		}
		ResponseUtil.writeResponseStr(response, ret);		
	}
	
	/**
	 * 批量删除
	 * @Title: batchDeleteRole
	 * @author yzp
	 * @date 2014-9-4 上午9:11:59
	 * @return String   1：成功  ; 0：不成功 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/batchDeleteUser")
	public void batchDeleteUser(HttpServletRequest request,HttpServletResponse response){
		String[] roleids = request.getParameterValues("ids[]");
		String ret = "0";
		/**/
		for(String userid:roleids){		 
			userService.deleteUser(userid);
		}
		// 
		ret = "1";
		ResponseUtil.writeResponseStr(response, ret);		
	}
	
/*	public void moveUser(HttpServletRequest request,HttpServletResponse response){
		String userid1=ParamUtil.getParamValue(request.getParameter("userid1"), "");
		String userid2=ParamUtil.getParamValue(request.getParameter("userid2"), "");
		String ret="0";
		userService.moveUser(userid1, userid2);
		ret="1";
		ResponseUtil.writeResponseStr(response, ret);
	}*/
	
	@RequestMapping("/sortUser")
	public void sortUser(HttpServletRequest request,HttpServletResponse response){
		String[] userids=request.getParameterValues("ids[]");
		String orgid=request.getParameter("orgid");
		String ret="0";
		int sort=1;
		for(String userid:userids){
			userService.updateSortUser(userid, orgid, String.valueOf(sort));
			sort++;
		}
		ResponseUtil.writeResponseStr(response, ret);
	}
}
