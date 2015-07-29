package ey.org.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ey.org.bean.Pager;
import ey.org.mgmodel.RoleOrgMG;
import ey.org.model.Org;
import ey.org.model.Role;
import ey.org.service.OrgService;
import ey.org.service.RoleService;
import ey.org.utils.FinalValue;
import ey.org.utils.ParamUtil;
import ey.org.utils.ResponseUtil;

/**
 * 角色管理 Controller
 * @ClassName: OrgController
 * @author yzp
 * @date 2014-8-28 上午11:06:54
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	@Resource 
	private OrgService orgService;
	@Resource 
	private RoleService roleService;

	/**
	 * 查看组织角色列表
	 * @Title: beginViewOrg
	 * @author yzp
	 * @date 2014-8-28 上午11:08:06
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description:
	 */
	@RequestMapping("/beginViewRole")
	public String beginViewRole(HttpServletRequest request){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"),FinalValue.ROOT_ORG_ID);
		
		String roleCurrentPage = ParamUtil.getParamValue(request.getParameter("currentPage"),"1");
		Org org = orgService.findById(orgid);		
		List<Org> orgList = orgService.getAllOrgList();
		
		//List<RoleOrgMG> roleList = roleService.getRoleList(orgid);
		
		List<RoleOrgMG> pageRoleList= roleService.getPageRoleList(orgid, Integer.parseInt(roleCurrentPage));
		if(pageRoleList.size()==0){
			roleCurrentPage="-1";
		}
		Pager rolePager=new Pager();
		rolePager.setMaxRows(10);
		rolePager.setTotalCount(roleService.getPageCount(orgid));
		rolePager.setCurrentPage(Integer.parseInt(roleCurrentPage));
		//分页结束
		request.setAttribute("rolePager", rolePager);
		request.setAttribute("pageRoleList", pageRoleList);
		request.setAttribute("orgList", orgList);
		//request.setAttribute("roleList", roleList);
		request.setAttribute("org", org);
		request.setAttribute("orgid", orgid);		
		return "./moudle/role/viewRole";
	}
	
	/**
	 * 
	 * @Title: beginAddRole
	 * @author yzp
	 * @date 2014-9-3 下午1:21:28
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/beginAddRole")
	public String beginAddRole(HttpServletRequest request){
		String pid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		request.setAttribute("pid", pid);;
		return "./moudle/role/addRole";
	}
	/**
	 * 添加角色
	 * @Title: addRole
	 * @author yzp
	 * @date 2014-9-1 下午1:50:00
	 * @param request
	 * @param response
	 * @return String   0：失败  ; 1：成功 
	 * @throws 
	 * @Description: 添加组织
	 */
	@RequestMapping("/addRole")
	public void addRole(HttpServletRequest request,HttpServletResponse response){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		String roleName = ParamUtil.getParamValue(request.getParameter("roleName"), "");
		String roleSign = ParamUtil.getParamValue(request.getParameter("roleSign"), "");
		String details = ParamUtil.getParamValue(request.getParameter("details"), "");
		String rolegroup = ParamUtil.getParamValue(request.getParameter("rolegroup"), "");
		String ret = "0";

		Role role = roleService.addRole(orgid, roleName, roleSign, details,rolegroup);
		ret = "1";
		ResponseUtil.writeResponseStr(response, ret);		
	}
	
	
	/**
	 * 是否唯一编号
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午5:16:23
	 * @return String   1：唯一  ; 0：不唯一 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/isUniCode")
	public void isUniCode(HttpServletRequest request,HttpServletResponse response){
		String roleSign = ParamUtil.getParamValue(request.getParameter("roleSign"), "");
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "");
		String ret = "false";//不唯一
		boolean isUni = this.roleService.isUniCode(roleSign,roleid);
		if(isUni){
			ret = "true";// 唯一
		}
		ResponseUtil.writeResponseStr(response, ret);		
	}
	/**
	 * 跳转至编辑界面
	 * @Title: beginEditRole
	 * @author yzp
	 * @date 2014-9-3 下午2:06:05
	 * @param request
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/beginEditRole")
	public String beginEditRole(HttpServletRequest request){
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "");
		Role role = roleService.findById(roleid);
		request.setAttribute("role", role);
		return "./moudle/role/editRole";
	}
	
	
	/**
	 * 更新角色
	 * @Title: updateRole
	 * @author yzp
	 * @date 2014-9-3 下午2:07:16
	 * @return String   1：成功  ; 0：不成功 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/updateRole")
	public void updateRole(HttpServletRequest request,HttpServletResponse response){
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "");
		String roleName = ParamUtil.getParamValue(request.getParameter("roleName"), "");
		String roleSign = ParamUtil.getParamValue(request.getParameter("roleSign"), "");
		String details = ParamUtil.getParamValue(request.getParameter("details"), "");
		String rolegroup = ParamUtil.getParamValue(request.getParameter("rolegroup"), "");
		String ret = "0";
		
		Role role = roleService.updateRole(roleid,roleName, roleSign, details, rolegroup); 
		ret = "1";
		ResponseUtil.writeResponseStr(response, ret);		
	}
	
	/**
	 * 删除角色
	 * @Title: deleteOrg
	 * @author yzp
	 * @date 2014-9-3 下午2:18:30
	 * @return String   1：成功  ; 0：不成功 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/deleteRole")
	public void deleteRole(HttpServletRequest request,HttpServletResponse response){
		String roleid = ParamUtil.getParamValue(request.getParameter("roleid"), "");
		String ret = "0";
		roleService.deleteRole(roleid); 
		ret = "1";
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
	@RequestMapping("/batchDeleteRole")
	public void batchDeleteRole(HttpServletRequest request,HttpServletResponse response){
		String[] roleids = request.getParameterValues("ids[]");
		String ret = "0";
		/**/
		for(String roleid:roleids){		 
			roleService.deleteRole(roleid);
		}
		// 
		ret = "1";
		ResponseUtil.writeResponseStr(response, ret);		
	}
}
