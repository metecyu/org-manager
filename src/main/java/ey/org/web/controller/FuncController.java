package ey.org.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ey.org.model.Func;
import ey.org.service.FuncService;
import ey.org.utils.FinalValue;
import ey.org.utils.ParamUtil;
import ey.org.utils.ResponseUtil;

/**
 * 功能项控制器层
 * @ClassName: FuncController.java
 * @author (wmc)
 * @date 2014-12-26 上午10:34:46
 *
 * @Description: TODO(xxxxxxxxxxxxxxx)
 */
@Controller
@RequestMapping("/func")
public class FuncController{
	@Resource
	private FuncService funcService;
	
	/** 
	 * 查看功能树
	 * @Title: beginViewFunc
	 * @author (wmc)
	 * @date 2014-12-5 下午2:11:57
	 * @param request
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("beginViewFunc")
	public String beginViewFunc(HttpServletRequest request){
		String funcid= ParamUtil.getParamValue(request.getParameter("funcid"), FinalValue.ROOT_FUNC_ID);
		Func func=funcService.getFuncListMG(funcid);
		List<Func> funcList =funcService.getAllFuncList();
		List<Func> sonFuncList=funcService.getSonFuncList(funcid);
		request.setAttribute("sonFuncList", sonFuncList);
		request.setAttribute("funcList", funcList);
		request.setAttribute("func", func);
		return "./moudle/func/viewFunc";
	}
	
	/**
	 * 删除功能 （逻辑删除）
	 * @Title: deleteFunc
	 * @author (wmc)
	 * @date 2014-12-5 下午2:21:55
	 * @param request
	 * @param response void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/deleteFunc")
	public void deleteFunc(HttpServletRequest request,HttpServletResponse response){
		String funcid= ParamUtil.getParamValue(request.getParameter("funcid"), "");
		String ret="0";
		Func func =funcService.deleteFunc(funcid);
		ret="1";
		ResponseUtil.writeResponseStr(response, ret);
	}
	
	/**
	 * 是否允许删除
	 * @Title: isAllowDel
	 * @author (wmc)
	 * @date 2014-12-5 下午2:44:16
	 * @param request
	 * @param response void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/isAllowDel")
	public void isAllowDel(HttpServletRequest request ,HttpServletResponse response){
		String funcid=ParamUtil.getParamValue(request.getParameter("funcid"), "");
		int type=this.funcService.isAllowDel(funcid);
		ResponseUtil.writeResponseStr(response, ""+type);
	}
	
	/**
	 * 更新功能项
	 * @Title: updateFunc
	 * @author (wmc)
	 * @date 2014-12-8 上午10:05:25
	 * @param request
	 * @param response void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/updateFunc")
	public void updateFunc(HttpServletRequest request,HttpServletResponse response){
		String funcid=ParamUtil.getParamValue(request.getParameter("funcid"), "");
		String funcname =ParamUtil.getParamValue(request.getParameter("funcname"), "123");
		/*String funcwholename=ParamUtil.getParamValue(request.getParameter("funcwholename"), "");*/
		String code=ParamUtil.getParamValue(request.getParameter("code"), "123");
		String url=ParamUtil.getParamValue(request.getParameter("url"), "");
		String type=ParamUtil.getParamValue(request.getParameter("type"), "");
		String ret="0";
		Func func= funcService.updateFunc(funcid, funcname, type, code, url);
		String[] funcidlist=request.getParameterValues("funcidlist[]");
		String[] funcnamelist=request.getParameterValues("funcnamelist[]");
		String[] codelist=request.getParameterValues("codelist[]");
		String[] urllist=request.getParameterValues("urllist[]");
		String[] typelist=request.getParameterValues("typelist[]");
		if(funcidlist!=null){
			int count=funcidlist.length;
			for(int i=0;i<count;i++){
				String fid=funcidlist[i].trim();
				String fname=funcnamelist[i].trim();
				String ftype= typelist[i].trim();
				String fcode=codelist[i].trim();
				String furl=urllist[i].trim();
				Func func2=funcService.updateFunc(fid, fname, ftype, fcode, furl);
			}
		}
		ret="1";
		ResponseUtil.writeResponseStr(response, ret);
	}
	
	/**
	 * 编码是否唯一
	 * @Title: isUniCode
	 * @author (wmc)
	 * @date 2014-12-5 下午4:17:34
	 * @param request
	 * @param response void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/isUniCode")
	public void isUniCode(HttpServletRequest request ,HttpServletResponse response){
		String code=ParamUtil.getParamValue(request.getParameter("code"), "");
		String funcid=ParamUtil.getParamValue(request.getParameter("funcid"), "");
		String ret="0";
		boolean isUni=this.funcService.isUniCode(code, funcid);
		if(isUni){
			ret="1";
		}
		ResponseUtil.writeResponseStr(response, ret);
	}
	
	/**
	 * 编辑功能项详细信息
	 * @Title: beginEditFunc
	 * @author (wmc)
	 * @date 2014-12-8 上午10:05:51
	 * @param request
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/beginEditFunc")
	public String beginEditFunc(HttpServletRequest request){
		String funcid= ParamUtil.getParamValue(request.getParameter("funcid"), "");
		Func func= funcService.getFuncListMG(funcid);
		request.setAttribute("func", func);
		return "./moudle/func/editFunc";
	}
	
	/**
	 * 增加功能项
	 * @Title: beginAddFunc
	 * @author (wmc)
	 * @date 2014-12-10 下午3:03:57
	 * @param request
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/beginAddFunc")
	public String beginAddFunc(HttpServletRequest request){
		String pid= ParamUtil.getParamValue(request.getParameter("funcid"), "");
		request.setAttribute("pid", pid);
		return "./moudle/func/addFunc";
	}
	
	/**
	 * 添加功能
	 * @Title: addFunc
	 * @author (wmc)
	 * @date 2014-12-10 下午3:24:32
	 * @param request
	 * @param response void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/addFunc")
	public void addFunc(HttpServletRequest request,HttpServletResponse response){
		String pid=ParamUtil.getParamValue(request.getParameter("pid"), "");
		String funcname=ParamUtil.getParamValue(request.getParameter("funcname"), "");
		//String funcwholename=ParamUtil.getParamValue(request.getParameter("funcwholename"), "");
		String code=ParamUtil.getParamValue(request.getParameter("code"), "");
		String url=ParamUtil.getParamValue(request.getParameter("url"), "");
		String type=ParamUtil.getParamValue(request.getParameter("type"), "");
		String ret="0";
		try{
			Func func= funcService.addFunc(funcname, type, code, pid, url);
		}catch(BadHanyuPinyinOutputFormatCombination e){
			e.printStackTrace();
		}
		ResponseUtil.writeResponseStr(response, ret);
	}
	
	/**
	 * 批量删除
	 * @Title: batchDelete
	 * @author (wmc)
	 * @date 2014-12-23 上午9:48:11
	 * @param request
	 * @param response void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	@RequestMapping("/batchDeleteFunc")
	public void batchDelete(HttpServletRequest request,HttpServletResponse response){
		String[] funcidList=request.getParameterValues("seldel[]");
		String ret="0";
		if(funcidList.length>0){
		for(String funcid:funcidList){
			funcService.deleteFunc(funcid);
		}
		ret="1";
		}
		ResponseUtil.writeResponseStr(response, ret);
	}
	
/*	public void moveFunc(HttpServletRequest request,HttpServletResponse response){
		String funcid1=ParamUtil.getParamValue(request.getParameter("funcid1"), "");
		String funcid2=ParamUtil.getParamValue(request.getParameter("funcid2"), "");
		
	}
	*/
}