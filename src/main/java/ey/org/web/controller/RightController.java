package ey.org.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ey.org.mgmodel.RoleOrgMG;
import ey.org.mgmodel.UserRoleMG;
import ey.org.model.Func;
import ey.org.model.Org;
import ey.org.model.User;
import ey.org.service.FuncService;
import ey.org.service.OrgService;
import ey.org.service.RightService;
import ey.org.service.RoleService;
import ey.org.service.UserService;
import ey.org.utils.FinalValue;
import ey.org.utils.ParamUtil;
import ey.org.utils.ResponseUtil;
import ey.org.webmodel.FuncWB;
import ey.org.webmodel.OrgWB;

/**
 * 权限管理 Controller
 * @ClassName: OrgController
 * @author yzp
 * @date 2014-8-28 上午11:06:54
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Controller
@RequestMapping("/right")
public class RightController {
	@Resource 
	private OrgService orgService;
	@Resource 
	private RoleService roleService;
	@Resource 
	private UserService userService;
	@Resource 
	private RightService rightService;
	@Resource 
	private FuncService funcService;
	
	
	/**
	 * 跳转到分配角色页面
	 * @Title: beginAssignRole
	 * @author yzp
	 * @date 2014-8-28 上午11:08:06
	 * @throws 
	 * @Description:
	 */
	@RequestMapping("/beginAssignRole")
	public String beginAssignRole(HttpServletRequest request){
		String userid = ParamUtil.getParamValue(request.getParameter("userid"), "");
		User user = userService.findById(userid);		
		List<Org> orgList = orgService.getAllOrgList();
		List<RoleOrgMG> roleList = roleService.getAllRoleList();
		
		request.setAttribute("orgList", orgList);
		request.setAttribute("roleList", roleList);
	
		request.setAttribute("userid", user.getId());		
		request.setAttribute("orgid", FinalValue.ROOT_ORG_ID);
		return "./moudle/right/assignUserRole";
	}
	
	
	/**
	 * 分配用户角色
	 * @Title: addOrg
	 * @author yzp
	 * @date 2014-9-1 下午1:50:00
	 * @return String   0：失败  ; 1：成功 
	 * @throws 
	 * @Description: 添加组织
	 */
 	@RequestMapping("/assignUserRole")
	public void assignUserRole(HttpServletRequest request,HttpServletResponse response){
		String userid = ParamUtil.getParamValue(request.getParameter("userid"), "");
		String[] roleids = request.getParameterValues("ids[]");  
		String ret = "-1";
		int ret2 = rightService.assignUserRole(userid, roleids);
		// User org = userService.addUser(orgid, username, loginid, pwd);
		ret = ""+ret2;
		ResponseUtil.writeResponseStr(response, ret);		
	}
 	
 	
	/**
	 * 查看用户已分配角色列表
	 * @Title: beginViewUserRole
	 * @author yzp
	 * @date 2014-9-5 下午3:02:25
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	
	@RequestMapping("/beginViewUserRole")
	public String beginViewUserRole(HttpServletRequest request){
		String userid = ParamUtil.getParamValue(request.getParameter("userid"), "");
		User user = userService.findById(userid);		
		List<UserRoleMG> roleList = rightService.getUserRoleList(userid);
		
		request.setAttribute("roleList", roleList);
		request.setAttribute("userid", user.getId());
		request.setAttribute("roleListSize", roleList.size());		
		
		return "./moudle/right/viewUserRole";
	}
	
	
	/**
	 * deleteUserRole
	 * @Title: deleteUserRole
	 * @author yzp
	 * @date 2014-9-1 下午1:50:00
	 * @return String   0：失败  ; 1：成功 
	 * @throws 
	 * @Description: 添加组织
	 */
 	@RequestMapping("/deleteUserRole")
	public void deleteUserRole(HttpServletRequest request,HttpServletResponse response){
		String relid = ParamUtil.getParamValue(request.getParameter("relid"), "");
	
		String ret = "0";
		rightService.deleteUserRole(relid);
		// User org = userService.addUser(orgid, username, loginid, pwd);
		ret = "1";
		ResponseUtil.writeResponseStr(response, ret);		
	}
 	
 	
 	// ===================== 角色功能 ===================== 
	/**
	 * 跳转到分配角色功能页面
	 * @Title: beginAssignRoleFunc
	 * @author yzp
	 * @date 2014-9-9 下午1:30:10
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/beginAssignRoleFunc")
	public String beginAssignRoleFunc(HttpServletRequest request){
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "admin");
		String openOrgStr = ParamUtil.getParamValue(request.getParameter("openOrgStr"), "");
		
		List<Org> orgList = orgService.getAllOrgList();
		List<RoleOrgMG> roleList = roleService.getAllRoleList();
		
		request.setAttribute("orgList", orgList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("orgid", FinalValue.ROOT_ORG_ID);
		request.setAttribute("roleid", roleid);
		
		List<Func> funcListTemp = funcService.getAllFuncList();
		List<Func> roleFuncList  = funcService.getFuncList(roleid);
		List<FuncWB> funcList = funcService.turnToWB(funcListTemp,roleFuncList);
		request.setAttribute("funcList", funcList);
		// request.setAttribute("roleFuncList", roleFuncList);
		request.setAttribute("openOrgStr", openOrgStr);
		
		return "./moudle/right/assignRoleFunc";
	}
	/**
	 * 跳转到分配角色数据页面
	 * @Title: beginAssignRoleFunc
	 * @author yzp
	 * @date 2014-9-9 下午1:30:10
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/beginAssignRoleData")
	public String beginAssignRoleData(HttpServletRequest request){
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "admin");
		String openOrgStr = ParamUtil.getParamValue(request.getParameter("openOrgStr"), "");
		
		List<Org> orgList = orgService.getAllOrgList();
		List<RoleOrgMG> roleList = roleService.getAllRoleList();
		
		request.setAttribute("orgList", orgList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("orgid", FinalValue.ROOT_ORG_ID);
		request.setAttribute("roleid", roleid);
		
		//List<Func> funcListTemp = funcService.getAllFuncList();
		List<Org> roleOrgListTemp  = orgService.getRoleOrgList(roleid);
		List<OrgWB> roleOrgList = orgService.turnToWB(orgList,roleOrgListTemp);
		request.setAttribute("roleOrgList", roleOrgList);
		request.setAttribute("openOrgStr", openOrgStr);
		
		return "./moudle/right/assignRoleData";
	}
	
	/**
	 * 更新角色功能
	 * @Title: updateRoleFunc
	 * @author yzp
	 * @date 2014-9-1 下午1:50:00
	 * @return String   0：失败  ; 1：成功 
	 * @throws 
	 * @Description: 
	 */
 	@RequestMapping("/updateRoleFunc")
	public void updateRoleFunc(HttpServletRequest request,HttpServletResponse response){
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "");
		String[] funcids = request.getParameterValues("ids[]");  
		String ret = "-1";
		int ret2 = rightService.updateRoleFunc(roleid, funcids);
		// User org = userService.addUser(orgid, username, loginid, pwd);
		ret = ""+ret2;
		ResponseUtil.writeResponseStr(response, ret);		
	}
 	
 	/**
	 * 更新角色功能
	 * @Title: updateRoleFunc
	 * @author yzp
	 * @date 2014-9-1 下午1:50:00
	 * @return String   0：失败  ; 1：成功 
	 * @throws 
	 * @Description: 
	 */
 	@RequestMapping("/updateRoleData")
	public void updateRoleData(HttpServletRequest request,HttpServletResponse response){
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "");
		String[] orgids = request.getParameterValues("ids[]");  
		String ret = "-1";
		int ret2 = rightService.updateRoleData(roleid, orgids);
		// User org = userService.addUser(orgid, username, loginid, pwd);
		ret = ""+ret2;
		ResponseUtil.writeResponseStr(response, ret);		
	}

}
