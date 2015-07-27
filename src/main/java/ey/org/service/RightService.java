package ey.org.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ey.org.dao.DataRoleRelDao;
import ey.org.dao.FuncRoleRelDao;
import ey.org.dao.RoleDao;
import ey.org.dao.UserRoleRelDao;
import ey.org.mgmodel.UserRoleMG;
import ey.org.model.DataRoleRel;
import ey.org.model.FuncRoleRel;

/**
 * 权限管理
 * @ClassName: RightService
 * @author yzp
 * @date 2014-8-28 上午11:02:40
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
public class RightService {
	@Resource
	private RoleDao roleDao;
	@Resource
	UserRoleRelDao userRoleRelDao;
	@Resource
	FuncRoleRelDao funcRoleRelDao;
	@Resource
	DataRoleRelDao dataRoleRelDao;
	/**
	 * 委派用户角色 
	 * @Title: assignUserRole
	 * @author yzp
	 * @date 2014-9-5 上午10:58:28
	 * @param userid
	 * @param roleidArr void    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public int assignUserRole(String userid,String[] roleidArr){
		int ret = 0;
		System.out.println("'"+roleidArr+"'");
		if(roleidArr==null || roleidArr.equals("null")){
			return ret;
		}
		// 添加用户角色关联		
		for(String roleid:roleidArr){
			// 如果已存在的话 就不保存了
			boolean isExist = roleDao.isExistThisRole(userid, roleid);
			if(!isExist){
				ret++;
				roleDao.addUserRoleRel(userid,roleid);
			}
		}
		return ret;
	}
	
	
	/**
	 * 更新角色功能权限 
	 * @Title: assignUserRole
	 * @author yzp
	 * @date 2014-9-5 上午10:58:28
	 * @param userid
	 * @param roleidArr void    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public int updateRoleFunc(String roleid,String[] orgidArr){
		int ret = 0;
		// System.out.println("'"+roleidArr+"'");
		/**/
		// 首先删除所有的历史关联记录
		List<FuncRoleRel> list = funcRoleRelDao.getFuncRoleRelList(roleid);
		if(list.size()>0){
			funcRoleRelDao.deleteBatch(list);
		}
		if(orgidArr==null || orgidArr.equals("null")){
			return ret;
		}
		// 添加用户角色关联		
		for(String orgid:orgidArr){			
			FuncRoleRel rel = new FuncRoleRel();
			rel.setFuncid(orgid);
			rel.setRoleid(roleid);
			funcRoleRelDao.save(rel);
		}
		return ret;
	}
	
	/**
	 * 更新角色数据权限 
	 * @Title: assignUserRole
	 * @author yzp
	 * @date 2014-9-5 上午10:58:28
	 * @param userid
	 * @param roleidArr void    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public int updateRoleData(String roleid,String[] funcidArr){
		int ret = 0;
		// System.out.println("'"+roleidArr+"'");
		/**/
		// 首先删除所有的历史关联记录
		List<DataRoleRel> list = dataRoleRelDao.getDataRoleRelList(roleid);
		if(list.size()>0){
			dataRoleRelDao.deleteBatch(list);
		}
		if(funcidArr==null || funcidArr.equals("null")){
			return ret;
		}
		// 添加用户角色关联		
		for(String funcid:funcidArr){			
			DataRoleRel rel = new DataRoleRel();
			rel.setOrgId(funcid);
			rel.setRoleid(roleid);
			dataRoleRelDao.save(rel);
		}
		return ret;
	}
	
	
	/**
	 * 获取用户角色列表
	 * @Title: assignUserRole
	 * @author yzp
	 * @date 2014-9-5 上午10:58:28
	 * @param userid
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<UserRoleMG> getUserRoleList(String userid){
		List<UserRoleMG> list = roleDao.getUserRoleList(userid);
		return list;
	}
	
	/**
	 *  删除用户角色关联
	 * @Title: deleteUserRoleRel
	 * @author yzp
	 * @date 2014-9-5 下午4:34:46
	 * @param reldid void    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public void deleteUserRole(String reldid){
		userRoleRelDao.delete(reldid);
	}
	
}
