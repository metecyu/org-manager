package ey.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ey.org.model.Org;
import ey.org.utils.PinyinUtils;

/**
 * 组织
 * @ClassName: OrgDao
 * @author yzp
 * @date 2014-8-28 上午10:24:48
 *
 * @Description: 组织机构中的组织类
 */
@Repository
public class OrgDao extends BaseDao<Org,String> {

	
	/**
	 * 获取完整组织机构
	 * @Title: getAllOrgList
	 * @author yzp
	 * @date 2014-9-1 上午11:54:21
	 * @return List<Org>    
	 * @throws 
	 * @Description:
	 */
	public List<Org> getAllOrgList(){
		String hql=" from Org org where org.iseffect='y' order by org.pid, org.sort";
		Query query=this.getSession().createQuery(hql);
		// query.setParameter("orgid", orgid);	
		return query.list();
	}
	/**
	 * 通过父id  获取组织机构列表
	 * @Title: getAllOrgList
	 * @author yzp
	 * @date 2014-9-1 上午11:54:21
	 * @return List<Org>    
	 * @throws 
	 * @Description:
	 */
	public List<Org> getOrgList(String pid){
		String hql=" from Org org where org.iseffect='y'  and org.pid=:pid order by org.pid, org.sort";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("pid", pid);
		return query.list();
	}
	
	/**
	 * 
	 * @Title: getPageOrgList分页
	 * @author (wmc)
	 * @date 2014-12-1 下午2:28:46
	 * @param pid
	 * @param currentPage
	 * @return List<Org>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public List<Org> getPageOrgList(String pid,int currentPage){
		int beginRow=0;
		beginRow+=(currentPage-1)*10;
		String hql=" from Org org where org.iseffect='y'  and org.pid=:pid order by org.pid, org.sort";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("pid", pid);
		query.setFirstResult(beginRow);
		query.setMaxResults(10);
		return query.list();
	}
	
	public int getPageCount(String pid){
		String hql=" from Org org where org.iseffect='y'  and org.pid=:pid order by org.pid, org.sort";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("pid", pid);
		return query.list().size();
	}
	
	/**
	 * 获得最大的序号
	 * @Title: findMaxSerial
	 * @author yzp
	 * @date 2014-9-4 上午10:56:16
	 * @param pid
	 * @return int    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public int findMaxSerial(String pid){  
        Map<String,Object> params = new HashMap();    
        StringBuffer sb=new StringBuffer();    
        sb.append("select max(org.sort) from Org org where org.iseffect='y' and org.pid='"+pid+"'");  
        Query query = getSession().createQuery(sb.toString());    
        Object ret = query.uniqueResult();  
        int count =0;  
        if(ret!=null){  
            count = ((Integer)ret).intValue();  
        }  
        return count;    
    } 
	
	/**
	 * 获得新的组织编号
	 * 编号规则：
	 * 	1  如果id未占用，直接使用id。
	 * 	2  如果已被占用在原有的id后添加2位数字编号。
	 * 	3 如果编号用完使用uuid（当然通常情况下 不太可能用完）
	 * @param deptid
	 * @return
	 */
	public String getNewDeptid(String deptid){
		 Org dept = this.findById(deptid);
		 if(dept==null){  //如果没用重名
			 return deptid;
		 }		 
		 // 对重复id 进行再编号（）  
		 List<Org> list = findReIdDeptList(deptid);
		 if(list ==null || list.size()==0 ){
			 deptid = deptid+"@01";
			 return deptid;
		 }
		 if(list.size()>0){ //已有重名情况
			 String atLastDeptid = list.get(0).getId();
			 String[] arr = atLastDeptid.split("@");
			 String num = arr[1];// 数字位
			 int ten = Integer.parseInt(""+num.charAt(0)); //十位
			 int ge = Integer.parseInt(""+num.charAt(1));// 个位
			 int inum = ten*10 + ge;
			 
			 if(inum>=99){ //如果编号超过99个
				 UUID uuid = UUID.randomUUID();
				 return uuid.toString();
			 }
			 inum++;
			 num = inum+"";
			 if(num.length()==1){ //如果是个位数  十位补0
				 num = "0"+num;
			 }
			 return deptid+"@"+num;
		 }
		 return ""; 
	}
	
	/**
	 * 获取id重复的组织列表
	 * @Title: findReIdDeptList
	 * @author yzp
	 * @date 2014-9-4 上午10:57:44
	 * @param deptid
	 * @return List<Org>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	private List<Org> findReIdDeptList(String deptid){  
        Map<String,Object> params = new HashMap();    
        StringBuffer sb=new StringBuffer();    
        sb.append("from Org dept where dept.id like '"+deptid+"@%' order by dept.id desc");  
        Query query = getSession().createQuery(sb.toString());    
        List<Org> list = query.list();
        return list;
    }
	
	/**
	 * 生成部门id
	 * @Title: genarateDeptId
	 * @author yzp
	 * @date 2014-9-4 上午10:57:55
	 * @param deptname
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	
	public String genarateDeptId(String deptname) throws BadHanyuPinyinOutputFormatCombination{
		String py = PinyinUtils.getFirstHanyuPinyin(deptname);
		String newDeptid = this.getNewDeptid(py);
		return newDeptid; 
	}
	
	/**
	 * 编码是否唯一
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午2:24:30
	 * @param code
	 * @return boolean    
	 * @throws 
	 * @Description: 
	 */
	public boolean isUniCode(String code,String orgid) {
		if("".equals(code)){
			 return true;
		}
		if("".equals(orgid)){
			StringBuffer sb=new StringBuffer();    
	        sb.append("from Org org where org.iseffect='y' and org.code=:code");  
	        Query query = getSession().createQuery(sb.toString());
	        query.setParameter("code", code);
	        List list = query.list();
	        if(list.size()>0){
	        	return false;
	        }
	        return true;       
		}else{
			StringBuffer sb=new StringBuffer();    
	        sb.append("from Org org where org.iseffect='y' and org.code=:code and org.id<>:orgid");  
	        Query query = getSession().createQuery(sb.toString());
	        query.setParameter("code", code);
	        query.setParameter("orgid", orgid);
	        List list = query.list();
	        if(list.size()>0){
	        	return false;
	        }
	        return true;       
		}
		 
	}
	/**
	 * 更新组织全称
	 * @Title: updateOrgWholeName
	 * @author yzp
	 * @date 2014-9-2 上午10:11:29
	 * @param oldPath
	 * @param newPath
	 * @return int    
	 * @throws 
	 * @Description: 
	 */
    public int updateOrgWholeName(String oldPath,String newPath){   
        Map<String,Object> params = new HashMap(); 
        StringBuffer sb=new StringBuffer();  
        sb.append("update  ORG_ORG t set  t.ORGWHOLENAME =  REPLACE（t.ORGWHOLENAME,'"+oldPath+"','"+newPath+"'）  where t.ORGWHOLENAME like '"+oldPath+"%'");    
        int count = getSession().createSQLQuery(sb.toString()).executeUpdate();      
        // this.getSession().clear(); 
        return count;      
    }  
	
    /**
	 * 获取角色数据权限列表（组织）
	 * @Title: getRoleOrgList
	 * @author yzp
	 * @date 2014-9-9 下午1:17:26
	 * @param roleid
	 * @return List<Func>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<Org> getRoleOrgList(String roleid){
		StringBuffer sb=new StringBuffer();    
		sb.append("select org ");
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
    
	/**
	 * 通过编码获取组织id
	 * @Title: getOrgId
	 * @author (wmc)
	 * @date 2015-1-5 下午4:08:47
	 * @param code
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String getOrgId(String code){
		StringBuffer sb=new StringBuffer();
		sb.append("select org.id");
		sb.append(" from ORG_ORG org");
		sb.append(" where org.code=:code");
		sb.append(" and org.iseffect='y'");
		Query query=getSession().createSQLQuery(sb.toString());
		query.setParameter("code", code);
		Object result=query.uniqueResult();
		return (String)result;
	}
	
}
