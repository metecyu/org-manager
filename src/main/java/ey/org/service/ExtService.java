package ey.org.service;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Service;
import ey.org.model.User;
@Service
public class ExtService{
	@Resource
	private OrgService orgService;
	@Resource
	private UserService userService;
	
	/**
	 * 
	 * @Title: addOrg
	 * @author (wmc)
	 * @date 2015-1-5 下午4:13:03
	 * @param name
	 * @param fullname
	 * @param code
	 * @param pcode
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String addOrg(String name,String fullname,String code,String pcode) throws BadHanyuPinyinOutputFormatCombination{
		String ret="0";
		String pid=orgService.getOrgId(pcode);
		if(orgService.isUniCode(code, "")&pid!=null){
		orgService.addOrg(pid, name, fullname, code);
		ret="1";
		}
		return ret;
	}
	
	/**
	 * 
	 * @Title: editOrg
	 * @author (wmc)
	 * @date 2015-1-5 下午4:13:10
	 * @param name
	 * @param fullname
	 * @param code
	 * @param pcode
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String editOrg(String name,String fullname,String oldcode,String newcode,String pcode) throws BadHanyuPinyinOutputFormatCombination{
		String ret="0";
		String orgid=orgService.getOrgId(oldcode);
		String porgid=orgService.getOrgId(pcode);
		if(orgid!=null&porgid!=null){
		orgService.updateOrg(orgid, name, fullname, newcode);
		ret="1";
		}
		return ret;
	}
	
	/**
	 * 
	 * @Title: delOrg
	 * @author (wmc)
	 * @date 2015-1-5 下午4:13:15
	 * @param name
	 * @param fullname
	 * @param code
	 * @param pcode
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String delOrg(String name,String fullname,String code,String pcode){
		String ret="0";
		String orgid=orgService.getOrgId(code);
		String porgid=orgService.getOrgId(pcode);
		if(orgid!=null&porgid!=null){
		orgService.deleteOrg(orgid);
		ret="1";
		}
		return ret;
	}
	
	/**
	 * 
	 * @Title: addUser
	 * @author (wmc)
	 * @date 2015-1-5 下午4:44:49
	 * @param name
	 * @param fullname
	 * @param pwd
	 * @param deptCode
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String addUser(String name,String loginid,String pwd,String deptCode) {
		String ret="0";
		String email="";
		String gender="1";
		String mobile="";
		String orgid=orgService.getOrgId(deptCode);
		if(orgid!=null&userService.isUniCode(loginid, "")){
		try {
			@SuppressWarnings("unused")
			User user=userService.addUser(orgid, name, loginid, pwd, email, gender, mobile);
			ret="1";
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return ret;
	}
	
	/**
	 * 
	 * @Title: editUser
	 * @author (wmc)
	 * @date 2015-1-5 下午4:44:55
	 * @param name
	 * @param loginid
	 * @param pwd
	 * @param deptCode
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String editUser(String name,String oldloginid,String newloginid,String pwd,String deptCode){
		String ret="0";
		String email="";
		String gender="1";
		String mobile="";
		String userid=userService.getUserId(oldloginid);
		String orgid=orgService.getOrgId(deptCode);
		if(userid!=null&orgid!=null){
		userService.updateUser(userid, name, newloginid, pwd, email, gender, mobile);
		userService.updateRel(orgid,userid);
		ret="1";
		}
		return ret;
	}
	
	/**
	 * 
	 * @Title: delUser
	 * @author (wmc)
	 * @date 2015-1-5 下午4:44:59
	 * @param name
	 * @param fullname
	 * @param pwd
	 * @param deptCode
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String delUser(String name,String loginid,String pwd,String deptCode){
		String ret="0";
		String userid=userService.getUserId(loginid);
		if(userid!=null){
		userService.deleteUser(userid);
		ret="1";
		}
		return ret;
	}
}