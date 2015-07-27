package ey.org.web.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ey.org.service.ExtService;
import ey.org.utils.ParamUtil;
import ey.org.utils.ResponseUtil;

@Controller
@RequestMapping("/ext")
public class ExtController{
	@Resource
	private ExtService extService;
	
	@RequestMapping("/beijinHandle")
	public void beijinHandle(HttpServletRequest request,HttpServletResponse response ) throws BadHanyuPinyinOutputFormatCombination, UnsupportedEncodingException{
		String hdType=ParamUtil.getParamValue(request.getParameter("hdType"), "0");
		//获取各种变量：
		String name="";
		String fullname="";
		String oldcode=""; 
		String code="";
		String newcode="";
		String pcode="";
		String pwd="";
		String deptCode="";
		String loginid="";
		String oldloginid="";
		String newloginid="";
		if("1".equals(hdType)|"2".equals(hdType)|"3".equals(hdType))
		{
			String isoname = request.getParameter("name");
			String isofullname = request.getParameter("fullname");
			String isooldcode = request.getParameter("oldcode");
			String isonewcode=request.getParameter("newcode");
			String isopcode = request.getParameter("pcode");
			String isocode=request.getParameter("code");
			/*String utfname = new String(isoname.getBytes("iso-8859-1"),"UTF-8");
			String utffullname = new String(isofullname.getBytes("iso-8859-1"),"UTF-8");
			String utfcode = new String(isocode.getBytes("iso-8859-1"),"UTF-8");
			String utfpcode = new String(isopcode.getBytes("iso-8859-1"),"UTF-8");*/
			name=ParamUtil.getParamValue(isoname, "");
			fullname=ParamUtil.getParamValue(isofullname, "");
			oldcode=ParamUtil.getParamValue(isooldcode, "");
			newcode=ParamUtil.getParamValue(isonewcode, "");
			code=ParamUtil.getParamValue(isocode, "");
			pcode=ParamUtil.getParamValue(isopcode, "");
		}
		else{
			String isoname = request.getParameter("name");
			String isologinid = request.getParameter("loginid");
			String isooldloginid = request.getParameter("oldloginid");
			String isonewloginid = request.getParameter("newloginid");
			String isopwd = request.getParameter("pwd");
			String isodeptCode = request.getParameter("deptCode");
			/*String utfname = new String(isoname.getBytes("iso-8859-1"),"UTF-8");
			String utfloginid = new String(isologinid.getBytes("iso-8859-1"),"UTF-8");
			String utfpwd = new String(isopwd.getBytes("iso-8859-1"),"UTF-8");
			String utfdeptCode = new String(isodeptCode.getBytes("iso-8859-1"),"UTF-8");*/
			name=ParamUtil.getParamValue(isoname, "");
			loginid=ParamUtil.getParamValue(isologinid, "");
			oldloginid=ParamUtil.getParamValue(isooldloginid, "");
			newloginid=ParamUtil.getParamValue(isonewloginid, "");
			pwd=ParamUtil.getParamValue(isopwd, "");
			deptCode=ParamUtil.getParamValue(isodeptCode, "");
		}
		int hdType2=Integer.parseInt(hdType);
		String ret="0";
		switch(hdType2){
		case 1:
			ret=extService.addOrg(name, fullname, code, pcode);
			break;
		case 2:
			ret=extService.editOrg(name, fullname, oldcode,newcode, pcode);
			break;
		case 3:
			ret=extService.delOrg(name, fullname, code, pcode);
			break;
		case 4:
			ret=extService.addUser(name, loginid, pwd, deptCode);
			break;
		case 5:
			ret=extService.editUser(name, oldloginid, newloginid,pwd, deptCode);
			break;
		case 6:
			ret=extService.delUser(name, loginid, pwd, deptCode);
			
			break;
		}
		ResponseUtil.writeResponseStr(response, ret);
	}
}