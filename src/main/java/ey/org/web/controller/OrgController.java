package ey.org.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ey.org.bean.Pager;
import ey.org.model.Org;
import ey.org.service.OrgService;
import ey.org.utils.FinalValue;
import ey.org.utils.ParamUtil;
import ey.org.utils.ResponseUtil;

/**
 * 组织管理 Controller
 * @ClassName: OrgController
 * @author yzp
 * @date 2014-8-28 上午11:06:54
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Controller
@RequestMapping("/org")
public class OrgController {
	@Resource 
	private OrgService orgService;
	

	/**
	 * 查看组织列表
	 * @Title: beginViewOrg
	 * @author yzp
	 * @date 2014-8-28 上午11:08:06
	 * @throws 
	 * @Description:
	 */
  
	@RequestMapping("/beginViewOrg")
	public String beginViewOrg(HttpServletRequest request){
		System.out.println("xxxxxxxxxxxxxddddddddddd");
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), FinalValue.ROOT_ORG_ID);
		
		String orgCurrentPage = ParamUtil.getParamValue(request.getParameter("currentPage"),"1");
		Org org = orgService.findById(orgid);		
		List<Org> orgList = orgService.getAllOrgList();
		//List<Org> levOrgList = orgService.getOrgList(orgid);
		List<Org> pageLevOrgList=orgService.getPageOrgList(orgid, Integer.parseInt(orgCurrentPage));
		
		if(pageLevOrgList.size()==0){
			orgCurrentPage="-1";
		}
		Pager orgPager=new Pager();
		orgPager.setMaxRows(10);
		orgPager.setTotalCount(orgService.getPageCount(orgid));
		orgPager.setCurrentPage(Integer.parseInt(orgCurrentPage));
		//分页结束
		request.setAttribute("orgPager", orgPager);
		request.setAttribute("pageLevOrgList", pageLevOrgList);
		
		request.setAttribute("orgList", orgList);
		//request.setAttribute("levOrgList", levOrgList);
		request.setAttribute("org", org);
		request.setAttribute("orgid", orgid);		
		return "./moudle/org/viewOrg";
	}
	
	/**
	 * 跳转至新建组织界面
	 * @Title: beginAddOrg
	 * @author yzp
	 * @date 2014-9-4 上午11:01:28
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/beginAddOrg")
	public String beginAddOrg(HttpServletRequest request){
		String pid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		request.setAttribute("pid", pid);;
		return "./moudle/org/addOrg";
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
	@RequestMapping("/addOrg")
	public void addOrg(HttpServletRequest request,HttpServletResponse response){
		String pid = ParamUtil.getParamValue(request.getParameter("pid"), "");
		String orgname = ParamUtil.getParamValue(request.getParameter("orgName"), "");
		String orgQname = ParamUtil.getParamValue(request.getParameter("orgQname"), "");
		String code = ParamUtil.getParamValue(request.getParameter("code"), "");
		String ret = "0";
		try {
			Org org = orgService.addOrg(pid, orgname, orgQname, code);
			ret = "1";
		} catch (BadHanyuPinyinOutputFormatCombination e) {			
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
	@RequestMapping("/beginEditOrg")
	public String beginEditOrg(HttpServletRequest request){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		Org org = orgService.findById(orgid);
		request.setAttribute("org", org);
		return "./moudle/org/editOrg";
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
	 
	@RequestMapping("/updateOrg")
	public void updateOrg(HttpServletRequest request,HttpServletResponse response){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		String orgname = ParamUtil.getParamValue(request.getParameter("orgName"), "");
		String orgQname = ParamUtil.getParamValue(request.getParameter("orgQname"), "");
		String code = ParamUtil.getParamValue(request.getParameter("code"), "");
		String ret = "0";
		try {
			Org org = orgService.updateOrg(orgid,orgname, orgQname, code); 
			ret = "1";
		} catch (BadHanyuPinyinOutputFormatCombination e) {			
			e.printStackTrace();
		}
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
	@RequestMapping("/deleteOrg")
	public void deleteOrg(HttpServletRequest request,HttpServletResponse response){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		String ret = "0";
		Org org = orgService.deleteOrg(orgid); 
		ret = "1";
		ResponseUtil.writeResponseStr(response, ret);		
	}
	/**
	 * 是否唯一编号
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午5:16:23
	 * return String 1:唯一，0：不唯一 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping("/isUniCode")
	public void isUniCode(HttpServletRequest request,HttpServletResponse response){
		String code = ParamUtil.getParamValue(request.getParameter("code"), "");
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		String ret = "0";//不唯一
		boolean isUni = this.orgService.isUniCode(code,orgid);
		if(isUni){
			ret = "1";// 唯一
		}
		ResponseUtil.writeResponseStr(response, ret);		
	}
	
	/**
	 * 是否允许删除
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午5:16:23
	 * @return String 1:合法，-1:有子部门 
	 * @throws 
	 * @Description: 
	 */
	@RequestMapping("/isAllowDel")
	public void isAllowDel(HttpServletRequest request,HttpServletResponse response){
		String orgid = ParamUtil.getParamValue(request.getParameter("orgid"), "");
		int type = this.orgService.isAllowDel(orgid);
		ResponseUtil.writeResponseStr(response, ""+type);		
	}
	
}
