package ey.org.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ey.org.model.DataRoleRel;



/**
 * 功能角色关联
 * @ClassName: RoleDao
 * @author yzp
 * @date 2014-8-28 上午10:24:48
 *
 * @Description: 组织机构中的角色
 */
@Repository
public class DataRoleRelDao extends BaseDao<DataRoleRel,String> {
	
	/**
	 * 获取角色功能关联列表
	 * @Title: getFuncRoleRelList
	 * @author yzp
	 * @date 2014-9-10 上午9:53:18
	 * @param roleid
	 * @return List<FuncRoleRel>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<DataRoleRel> getDataRoleRelList(String roleid){
		StringBuffer sb=new StringBuffer();    
		sb.append("select rel ");
        sb.append(" from Org org,DataRoleRel rel,Role role where ");
        sb.append(" role.id=rel.roleid");        
        sb.append(" and org.id=rel.orgId");
        sb.append(" and role.iseffect='y' and org.iseffect='y'");//有效性
        sb.append(" and role.id=:roleid");
        // sb.append(" order by func.pid, func.sort");
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("roleid", roleid);
		return query.list();
	}
	
}
