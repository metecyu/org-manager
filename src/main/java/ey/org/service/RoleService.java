package ey.org.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ey.org.dao.RoleDao;
import ey.org.mgmodel.RoleOrgMG;
import ey.org.model.Role;

/**
 * 
 * @ClassName: OrgService
 * @author (作者)
 * @date 2014-8-28 上午11:02:40
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
public class RoleService {
	@Resource
	private RoleDao roleDao;
	/**
	 * 获取角色列表
	 * @Title: getRoleList
	 * @author yzp
	 * @date 2014-9-3 下午1:16:01
	 * @param orgid
	 * @return List<Role>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<RoleOrgMG> getRoleList(String orgid){
		return roleDao.getRoleList(orgid);
	}
	
	/**
	 * 角色列表分页显示
	 * @Title: getPageRoleList
	 * @author (wmc)
	 * @date 2014-12-1 下午2:51:20
	 * @param orgid
	 * @param currentPage
	 * @return List<RoleOrgMG>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public List<RoleOrgMG> getPageRoleList(String orgid,int currentPage){
		return roleDao.getPageRoleList(orgid, currentPage);
	}
	
	/**
	 * 获取记录总数。用于计算分页页数
	 * @Title: getPageCount
	 * @author (wmc)
	 * @date 2014-12-1 下午2:52:22
	 * @param orgid
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int getPageCount(String orgid){
		return roleDao.getPageCount(orgid);
	}
	
	/**
	 * 获取所有角色列表
	 * @Title: getRoleList
	 * @author yzp
	 * @date 2014-9-3 下午1:16:01
	 * @param orgid
	 * @return List<Role>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<RoleOrgMG> getAllRoleList(){
		return roleDao.getAllRoleList();
	}
	
	
	/**
	 * 获取角色对象
	 * @Title: findByIdassignUserRole
	 * @author yzp
	 * @date 2014-9-3 下午2:28:04
	 * @param roleid
	 * @return Role    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public Role findById(String roleid){
		return roleDao.findById(roleid);
	}
	
	/**
	 * 添加角色
	 * @Title: addRole
	 * @author yzp
	 * @date 2014-9-3 下午1:19:42
	 * @param orgid
	 * @param roleName
	 * @param roleSign
	 * @param details void    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public Role addRole(String orgid,String roleName ,String roleSign ,String details,String group){
		Role role = new Role();
		role.setOrgid(orgid);
		role.setDetails(details);
		role.setRolename(roleName);
		role.setRolesign(roleSign);
		role.setRolegroup(group);
		role.setIseffect("y");
		role.setSort(	roleDao.findMaxSerial(orgid) + 1 );
		roleDao.save(role);
		return role;
	}
	/**
	 * 更新角色
	 * @Title: updateRole
	 * @author yzp
	 * @date 2014-9-3 下午2:08:29
	 * @param roleid
	 * @param roleName
	 * @param roleSign
	 * @param details
	 * @return Role    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public Role updateRole(String roleid,String roleName ,String roleSign ,String details,String group){
		Role role = roleDao.findById(roleid);
		// role.setOrgid(orgid);
		role.setDetails(details);
		role.setRolename(roleName);
		role.setRolesign(roleSign);
		role.setRolegroup(group);
		roleDao.save(role);
		return role;
	}
	
	/**
	 * 编码是否重名
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午2:26:33
	 * @param code
	 * @return boolean true：唯一  ;false:不唯一  
	 * @throws 
	 * @Description: 
	 */
	public boolean isUniCode(String code,String orgid) {
		boolean isUni = roleDao.isUniCode(code,orgid);
		return isUni;
	}
	
	/**
	 * 删除角色（逻辑删除）
	 * @param orgid
	 * @return
	 * @Description:
	 * @throws 
	 */
	public Role deleteRole(String roleid)  {
		Role role = roleDao.findById( roleid);
		role.setIseffect("n");
		roleDao.save(role);
		return role;
	}
	
}
