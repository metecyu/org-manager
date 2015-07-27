package ey.org.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ey.org.mgmodel.UserRoleMG;
import ey.org.mgmodel.RoleOrgMG;
import ey.org.model.Role;
import ey.org.model.UserRoleRel;



/**
 *角色
 * @ClassName: RoleDao
 * @author yzp
 * @date 2014-8-28 上午10:24:48
 *
 * @Description: 组织机构中的角色
 */
@Repository
public class RoleDao extends BaseDao<Role,String> {
	/**
	 * 通过组织id 获取角色列表
	 * @Title: getRoleList
	 * @author yzp
	 * @date 2014-9-3 下午1:07:22
	 * @param orgid
	 * @return List<Role>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<RoleOrgMG> getRoleList(String orgid){
		String hql="select new ey.org.mgmodel.RoleOrgMG( role.id,role.rolename,role.rolesign,role.iseffect,role.type,role.sort,role.orgid,role.details,org.orgwholename,role.rolegroup)" +	
			" from Role role,Org org where role.orgid=org.id and role.iseffect='y' and org.iseffect='y'  and role.orgid=:orgid order by role.sort";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("orgid", orgid);
		return query.list();
	}
	
	/**
	 * rolelist分页显示
	 * @Title: getPageRoleList
	 * @author (wmc)
	 * @date 2014-12-1 下午2:47:29
	 * @param orgid
	 * @param currentPage
	 * @return List<RoleOrgMG>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public List<RoleOrgMG> getPageRoleList(String orgid,int currentPage ){
		int beginRow=0;
		beginRow+=(currentPage-1)*10;
		String hql="select new ey.org.mgmodel.RoleOrgMG( role.id,role.rolename,role.rolesign,role.iseffect,role.type,role.sort,role.orgid,role.details,org.orgwholename,role.rolegroup)" +	
				" from Role role,Org org where role.orgid=org.id and role.iseffect='y' and org.iseffect='y'  and role.orgid=:orgid order by role.sort";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("orgid", orgid);
		query.setFirstResult(beginRow);
		query.setMaxResults(10);
		return query.list();
	}
	
	/**
	 * rolelist记录总数
	 * @Title: getPageCount
	 * @author (wmc)
	 * @date 2014-12-1 下午2:49:37
	 * @param orgid
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int getPageCount(String orgid){
		String hql="select new ey.org.mgmodel.RoleOrgMG( role.id,role.rolename,role.rolesign,role.iseffect,role.type,role.sort,role.orgid,role.details,org.orgwholename,role.rolegroup)" +	
				" from Role role,Org org where role.orgid=org.id and role.iseffect='y' and org.iseffect='y'  and role.orgid=:orgid order by role.sort";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("orgid", orgid);
		return query.list().size();
	}
	
	/**
	 * 获取所有角色列表
	 * @Title: getRoleList
	 * @author yzp
	 * @date 2014-9-3 下午1:07:22
	 * @param orgid
	 * @return List<Role>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	
	public List<RoleOrgMG> getAllRoleList(){
		String hql="select new ey.org.mgmodel.RoleOrgMG( role.id,role.rolename,role.rolesign,role.iseffect,role.type,role.sort,role.orgid,role.details,org.orgwholename,role.rolegroup)" +	
			" from Role role,Org org where role.orgid=org.id and role.iseffect='y' and org.iseffect='y'  order by role.sort";
		Query query=this.getSession().createQuery(hql);
		return query.list();
	}
	
	/**
	 * 获得最大的编号
	 * @Title: findMaxSerial
	 * @author yzp
	 * @date 2014-9-3 下午1:07:51
	 * @param orgid
	 * @return int    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	
	public int findMaxSerial(String orgid){  
        Map<String,Object> params = new HashMap();    
        StringBuffer sb=new StringBuffer();    
        sb.append("select max(role.sort) from Role role where role.iseffect='y' and role.orgid='"+orgid+"'");  
        Query query = getSession().createQuery(sb.toString());    
        Object ret = query.uniqueResult();  
        int count =0;  
        if(ret!=null){  
            count = ((Integer)ret).intValue();  
        }  
        return count;    
    } 
	
	
	/**
	 * 编码是否唯一
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午2:24:30
	 * @param roleSign
	 * @return boolean   true：唯一  ;false:不唯一  
	 * @throws 
	 * @Description: 
	 */
	public boolean isUniCode(String roleSign,String roleid) {
		if("".equals(roleSign)){
			 return true;
		}
		if("".equals(roleid)){
			StringBuffer sb=new StringBuffer();    
	        sb.append("from Role role where role.iseffect='y' and role.rolesign=:rolesign");  
	        Query query = getSession().createQuery(sb.toString());
	        query.setParameter("rolesign", roleSign);
	        List list = query.list();
	        if(list.size()>0){
	        	return false;
	        }
	        return true;       
		}else{
			StringBuffer sb=new StringBuffer();    
	        sb.append("from Role role where role.iseffect='y' and role.rolesign=:rolesign and role.id<>:roleid");  
	        Query query = getSession().createQuery(sb.toString());
	        query.setParameter("rolesign", roleSign);
	        query.setParameter("roleid", roleid);
	        List list = query.list();
	        if(list.size()>0){
	        	return false;
	        }
	        return true;       
		}
	}
	/**
	 * 添加 用户角色关联
	 * @Title: addUserRoleRel
	 * @author yzp
	 * @date 2014-9-5 下午2:20:03
	 * @param userid
	 * @param roleid
	 * @return UserRoleRel    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public UserRoleRel addUserRoleRel(String userid,String roleid){
		UserRoleRel rel = new UserRoleRel();
		rel.setUserid(userid);
		rel.setRoleid(roleid);
		this.saveObject(rel);
		return rel;
		
	}
	
	/**
	 * 用户是否存在此角色
	 * @Title: isHaveThisRole
	 * @author yzp
	 * @date 2014-9-5 下午2:21:46
	 * @param userid
	 * @param roleid
	 * @return boolean   1：存在  ； 0：不存在
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public boolean isExistThisRole(String userid,String roleid){
		StringBuffer sb=new StringBuffer();    
        sb.append("from UserRoleRel rel where rel.userid=:userid and rel.roleid=:roleid");  
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("userid", userid);
        query.setParameter("roleid", roleid);
        List list = query.list();
        if(list.size()>0){
        	return true;
        }
        return false;       
		
	}
	
	/**
	 * 获取用户角色列表
	 * @Title: getUserRoleList
	 * @author yzp
	 * @date 2014-9-5 下午4:29:58
	 * @param userid
	 * @return List<MgUserRole>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	 
	public List<UserRoleMG> getUserRoleList(String userid){
		List outList = new ArrayList();
		StringBuffer sb=new StringBuffer();    
		sb.append("select rel.id,rel.userid,rel.roleid,org.id,role.rolename,role.rolesign,role.type,role.sort,role.details,org.orgwholename");
        sb.append(" from UserRoleRel rel,Role role,User user,Org org where ");
        sb.append(" role.orgid=org.id");        
        sb.append(" and role.id=rel.roleid");
        sb.append(" and user.id=rel.userid");
        sb.append(" and role.iseffect='y' and org.iseffect='y'  and user.iseffect='y'");//有效性
        sb.append(" and userid=:userid");//有效性
        sb.append(" order by org.orgwholename");//有效性
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("userid", userid);
        List<Object[]> list = query.list();  
        for(Object[] object : list){
        	UserRoleMG mg = new UserRoleMG();
        	mg.setUserRoleRelid((String)object[0]);
        	mg.setUserid((String)object[1]);
        	mg.setRoleid((String)object[2]);
        	mg.setOrgid((String)object[3]);
        	mg.setRolename((String)object[4]);
        	mg.setRolesign((String)object[5]);
        	mg.setType((String)object[6]);
        	mg.setRoleSort((Integer)object[7]);
        	mg.setDetails((String)object[8]);
        	mg.setOrgwholename((String)object[9]);
        	outList.add(mg);
        }
        //MgUserRole
        return outList;
		
	}
	
}
