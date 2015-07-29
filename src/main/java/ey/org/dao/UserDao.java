package ey.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ey.org.model.User;
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
public class UserDao extends BaseDao<User,String> {
	/**
	 * 通过父id  获取组织机构列表
	 * @Title: getAllOrgList
	 * @author yzp
	 * @date 2014-9-1 上午11:54:21
	 * @return List<Org>    
	 * @throws 
	 * @Description:
	 */
	public List<User> getUserList(String orgid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select usr from User usr,Org org,UserOrgRel rel");
		sb.append(" where usr.iseffect='y' and org.iseffect='y'");
		sb.append(" and usr.id = rel.userid");
		sb.append(" and org.id = rel.orgid");
		sb.append(" and org.id =:orgid");
		sb.append(" order by rel.sort ");
		Query query=this.getSession().createQuery(sb.toString());
		query.setParameter("orgid", orgid);
		return query.list();
	}
	
	/**
	 * 
	 * @Title: 获取用户列表分页
	 * @author (wmc)
	 * @date 2014-12-1 上午9:33:55
	 * @param orgid
	 * @param currentPage
	 * @return List<User>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public List<User> getPageUserList(String orgid,int currentPage){
		int beginRow=0;
		beginRow+=(currentPage-1)*10;
		StringBuffer sb=new StringBuffer();
		sb.append(" select usr from User usr,Org org,UserOrgRel rel");
		sb.append(" where usr.iseffect='y' and org.iseffect='y'");
		sb.append(" and usr.id = rel.userid");
		sb.append(" and org.id = rel.orgid");
		sb.append(" and org.id =:orgid");
		sb.append(" order by rel.sort ");
		Query query=this.getSession().createQuery(sb.toString());
		query.setParameter("orgid", orgid);
		query.setFirstResult(beginRow);
		query.setMaxResults(10);
		return query.list();
	}
	
	public int getPageCount(String orgid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select usr from User usr,Org org,UserOrgRel rel");
		sb.append(" where usr.iseffect='y' and org.iseffect='y'");
		sb.append(" and usr.id = rel.userid");
		sb.append(" and org.id = rel.orgid");
		sb.append(" and org.id =:orgid");
		sb.append(" order by rel.sort ");
		Query query=this.getSession().createQuery(sb.toString());
		query.setParameter("orgid", orgid);
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
        sb.append("select max(rel.sort) from Org org,UserOrgRel rel where org.iseffect='y' and org.pid='"+pid+"'");  
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
	 * @param userid
	 * @return
	 */
	public String getNewDeptid(String userid){
		 User dept = this.findById(userid);
		 if(dept==null){  //如果没用重名
			 return userid;
		 }		 
		 // 对重复id 进行再编号（）  
		 List<User> list = findReIdDeptList(userid);
		 if(list ==null || list.size()==0 ){
			 userid = userid+"@01";
			 return userid;
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
			 return userid+"@"+num;
		 }
		 return ""; 
	}
	
	/**
	 * 获取id重复的用户列表
	 * @Title: findReIdDeptList
	 * @author yzp
	 * @date 2014-9-4 上午10:57:44
	 * @param deptid
	 * @return List<Org>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	private List<User> findReIdDeptList(String deptid){  
        Map<String,Object> params = new HashMap();    
        StringBuffer sb=new StringBuffer();    
        sb.append("from User user where user.id like '"+deptid+"@%' order by user.id desc");  
        Query query = getSession().createQuery(sb.toString());    
        List<User> list = query.list();
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
	 * 登录名是否唯一
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-4 上午11:49:20
	 * @param loginid
	 * @param userid
	 * @return boolean    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	 
	public boolean isUniCode(String loginid,String userid) {
		if("".equals(loginid)){
			 return true;
		}
		if("".equals(userid)){
			StringBuffer sb=new StringBuffer();    
	        sb.append("from User user where user.iseffect='y' and user.loginid=:loginid");  
	        Query query = getSession().createQuery(sb.toString());
	        query.setParameter("loginid", loginid);
	        List list = query.list();
	        if(list.size()>0){
	        	return false;
	        }
	        return true;       
		}else{
			StringBuffer sb=new StringBuffer();    
	        sb.append("from User user where user.iseffect='y' and user.loginid=:loginid and user.id<>:userid");  
	        Query query = getSession().createQuery(sb.toString());
	        query.setParameter("loginid", loginid);
	        query.setParameter("userid", userid);
	        List list = query.list();
	        if(list.size()>0){
	        	return false;
	        }
	        return true;       
		}
		 
	}
	
	/**
	 * 调整
	 * @Title: moveUser
	 * @author (wmc)
	 * @date 2014-12-24 下午5:03:49
	 * @param sort1
	 * @param sort2
	 * @param sign void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public boolean moveUser(int sort1,int sort2){
		StringBuffer sbfin=new StringBuffer();
		StringBuffer sb=new StringBuffer();
		sb.append("update ORG_USERROLEREL urr set urr.sort=urr.sort+1 ");
		sb.append("where urr.sort<:sort1 "); 
		sb.append("and urr.sort>=:sort2 ");
		StringBuffer sb1=new StringBuffer();
		sb1.append("update ORG_USERROLEREL urr set urr.sort=urr.sort-1 ");
		sb1.append("where urr.sort>:sort1 ");
		sb1.append("and urr.sort<=:sort2");
		if(sort1>sort2){
			sbfin=sb;
		}
		else
			sbfin=sb1;
		Query query=getSession().createSQLQuery(sbfin.toString());
		query.setParameter("sort1", sort1);
		query.setParameter("sort2", sort2);
		List list=query.list();
		StringBuffer sbuser=new StringBuffer();
		sb.append("update ORG_USERROLEREL urr set urr.sort=:sort2 where urr.sort=:sort1");
		Query queryuser=getSession().createSQLQuery(sbuser.toString());
		queryuser.setParameter("sort1", sort1);
		queryuser.setParameter("sort2", sort2);
		List list2=queryuser.list();
		return true;
	}
	
	/**
	 * 获取用户角色的排序
	 * @Title: getSort
	 * @author (wmc)
	 * @date 2014-12-24 下午4:55:05
	 * @param userid
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int getSort(String userid){
		StringBuffer sb=new StringBuffer();
		sb.append("select urr.sort from ORG_USERROLEREL urr where urr.userid=:userid");
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("userid", userid);
		Object sort=query.uniqueResult();
		return ((Integer)sort).intValue();
	}
	
	/**
	 * 通过用户名和登录名获取用户id
	 * @Title: getUserId
	 * @author (wmc)
	 * @date 2015-1-5 下午4:39:50
	 * @param loginid
	 * @param userid
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String getUserId(String loginid){
		StringBuffer sb=new StringBuffer();
		sb.append("select usr.id");
		sb.append(" from ORG_USER usr");
		sb.append(" where usr.loginid=:loginid");
		/*sb.append(" and usr.username=:userid");*/
		sb.append(" and usr.iseffect='y'");
		Query query=getSession().createSQLQuery(sb.toString());
		query.setParameter("loginid", loginid);
		/*query.setParameter("userid", userid);*/
		Object result=query.uniqueResult();
		return (String)result;
	}
	
	/**
	 * 修改用户组织关系
	 * @Title: updateRel
	 * @author (wmc)
	 * @date 2015-1-5 下午5:23:00
	 * @param orgid
	 * @param userid void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public void updateRel(String orgid,String userid){
		StringBuffer sb=new StringBuffer();
		sb.append("update ORG_USERORGREL set orgid=:orgid");
		sb.append(" where userid=:userid");
		Query query=getSession().createSQLQuery(sb.toString());
		query.setParameter("orgid", orgid);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	
	/**
	 * 修改用户排序
	 * @Title: updateSortUser
	 * @author (wmc)
	 * @date 2015-1-9 上午10:19:17
	 * @param orgid
	 * @param userid
	 * @param sort void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public void updateSortUser(String orgid,String userid,String sort){
		StringBuffer sb=new StringBuffer();
		sb.append("update ORG_USERORGREL set");
		sb.append(" sort=:sort ");
		sb.append(" where userid=:userid");
		sb.append(" and orgid=:orgid");
		Query query=getSession().createSQLQuery(sb.toString());
		query.setParameter("userid", userid);
		query.setParameter("orgid", orgid);
		query.setParameter("sort", sort);
		query.executeUpdate();
	}
	
}
