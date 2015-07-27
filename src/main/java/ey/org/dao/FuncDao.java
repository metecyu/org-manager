package ey.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ey.org.model.Func;
import ey.org.model.Org;
import ey.org.utils.PinyinUtils;

/**
 * 功能 
 * @ClassName: FuncDao
 * @author yzp
 * @date 2014-8-28 上午10:24:48
 *
 * @Description: 功能
 */
@Repository
public class FuncDao extends BaseDao<Func,String> {

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
        sb.append("select max(func.sort) from Func func where func.iseffect='y' and func.pid='"+pid+"'");  
        Query query = getSession().createQuery(sb.toString());    
        Object ret = query.uniqueResult();  
        int count =0;  
        if(ret!=null){  
            count = ((Integer)ret).intValue();  
        }  
        return count;    
    }
	

	/**
	 * 获取完整功能结构
	 * @Title: getAllOrgList
	 * @author yzp
	 * @date 2014-9-1 上午11:54:21
	 * @return List<Org>    
	 * @throws 
	 * @Description:
	 */
	public List<Func> getAllFuncList(){
		String hql=" from Func func where func.iseffect='y' order by func.pid, func.sort";
		Query query=this.getSession().createQuery(hql);
		// query.setParameter("orgid", orgid);	
		return query.list();
	}

	
	/**
	 * 获取角色功能列表
	 * @Title: getFuncList
	 * @author yzp
	 * @date 2014-9-9 下午1:17:26
	 * @param roleid
	 * @return List<Func>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<Func> getFuncList(String roleid){
		StringBuffer sb=new StringBuffer();    
		sb.append("select func ");
        sb.append("  from Func func,FuncRoleRel rel,Role role where ");
        sb.append(" role.id=rel.roleid");        
        sb.append(" and func.id=rel.funcid");
        sb.append(" and role.iseffect='y' and func.iseffect='y'");//有效性
        sb.append(" and role.id=:roleid");
        sb.append(" order by func.pid, func.sort");
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("roleid", roleid);
		return query.list();
	}
	
	/**
	 * 获取子功能
	 * @Title: getSonFuncList
	 * @author (wmc)
	 * @date 2014-12-5 下午2:37:23
	 * @param pid
	 * @return List<Func>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public List<Func> getSonFuncList(String pid){
		StringBuffer sb=new StringBuffer();
		sb.append("select func ");
		sb.append(" from Func func where ");
		sb.append(" func.iseffect='y' ");
		sb.append(" and func.pid=:pid");
		sb.append(" order by func.sort");
		Query query= getSession().createQuery(sb.toString());
		query.setParameter("pid", pid);
		return query.list();
	}
	
	/**
	 * 更新功能全称
	 * @Title: updateFuncWholeName
	 * @author (wmc)
	 * @date 2014-12-5 下午3:33:59
	 * @param oldPath
	 * @param newPath
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int updateFuncWholeName(String oldPath,String newPath){
		StringBuffer sb=new StringBuffer();
		sb.append("update ORG_FUNC t set t.FUNCWHOLENAME = REPLACE(t.FUNCWHOLENAME,'"+oldPath+"','"+newPath+"') where t.FUNCWHOLENAME like '"+oldPath+"%'");
		int count=getSession().createSQLQuery(sb.toString()).executeUpdate();
		return count;
	}
	
	/**
	 * 删除父节点时连带删除子节点。
	 * @Title: deleteByPid
	 * @author (wmc)
	 * @date 2014-12-22 上午11:49:59
	 * @param pid
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int deleteByPid(String funcwholename){
		StringBuffer sb=new StringBuffer();
		sb.append("update ORG_FUNC t set t.ISEFFECT='n' where t.FUNCWHOLENAME like'"+funcwholename+"/%'");
		int count=getSession().createSQLQuery(sb.toString()).executeUpdate();
		return count;
	}
	
	/**
	 * 
	 * @Title: getFuncWholeName
	 * @author (wmc)
	 * @date 2014-12-31 下午2:02:22
	 * @param funcid
	 * @return List<Func>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 *//*
	public List<Func> getFuncWholeName(String funcid){
		StringBuffer sb=new StringBuffer();
		sb.append("from Func func where func.funcid=:funcid");
		Query query=getSession().createQuery(sb.toString());
		query.setParameter("funcid", funcid);
		return query.list();
	}
	*/
	
	/**
	 * 编码是否唯一
	 * @Title: isUniCode
	 * @author (wmc)
	 * @date 2014-12-5 下午4:12:48
	 * @param code
	 * @param funcid
	 * @return boolean
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public boolean isUniCode(String code,String funcid){
		if("".equals(code)){
			return true;
		}
		if("".equals(funcid)){
			StringBuffer sb=new StringBuffer();
			sb.append("from Func func where func.iseffect='y' and func.code=:code");
			Query query =getSession().createQuery(sb.toString());
			query.setParameter("code", code);
			List list=query.list();
			if(list.size()>0){
				return false;
			}
			return true;
		}else{
			StringBuffer sb=new StringBuffer();
			sb.append("from Func func where func.iseffect='y' and func.code=:code and func.id<>:funcid");
			Query query=getSession().createQuery(sb.toString());
			query.setParameter("code",code);
			query.setParameter("funcid", funcid);
			List list =query.list();
			if(list.size()>0){
				return false;
			}
			return true;
		}
	}
	
	/**
	 * 生成功能id
	 * @Title: genarateDeptId
	 * @author (wmc)
	 * @date 2014-12-10 下午3:20:00
	 * @param deptname
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String genarateDeptId(String deptname) throws BadHanyuPinyinOutputFormatCombination{
		String py = PinyinUtils.getFirstHanyuPinyin(deptname);
		String newDeptid = this.getNewDeptid(py);
		return newDeptid; 
	}
	
	/**
	 * 获得新的组织编号
	 * @Title: getNewDeptid
	 * @author (wmc)
	 * @date 2014-12-10 下午3:18:08
	 * @param deptid
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String getNewDeptid(String deptid){
		 Func dept = this.findById(deptid);
		 if(dept==null){  //如果没用重名
			 return deptid;
		 }		 
		 // 对重复id 进行再编号（）  
		 List<Func> list = findReIdDeptList(deptid);
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
	 * 获取id重复的功能列表
	 * @Title: findReIdDeptList
	 * @author (wmc)
	 * @date 2014-12-10 下午3:16:48
	 * @param deptid
	 * @return List<Func>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	private List<Func> findReIdDeptList(String deptid){  
        Map<String,Object> params = new HashMap();    
        StringBuffer sb=new StringBuffer();    
        sb.append("from Func dept where dept.id like '"+deptid+"@%' order by dept.id desc");  
        Query query = getSession().createQuery(sb.toString());    
        List<Func> list = query.list();
        return list;
    }
	
}
