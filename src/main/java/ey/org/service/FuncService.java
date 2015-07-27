package ey.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Service;

import ey.org.dao.FuncDao;
import ey.org.model.Func;
import ey.org.webmodel.FuncWB;

/**
 * 功能 
 * @ClassName: OrgService
 * @author yzp
 * @date 2014-8-28 上午11:02:40
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
public class FuncService {
	@Resource
	private FuncDao funcDao;
	/**
	 * 新增功能
	 * @Title: addFunc
	 * @author yzp
	 * @date 2014-9-9 上午11:30:52
	 * @param funcName
	 * @param type
	 * @param code
	 * @param pid
	 * @param url
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination Func    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public Func addFunc(String funcName,String type,String code,String pid,String url) throws BadHanyuPinyinOutputFormatCombination  {
		Func func = new Func();
		func.setId(funcDao.genarateDeptId(funcName));
		func.setFuncname(funcName);
		func.setType(type);
		func.setCode(code);
		func.setSort(funcDao.findMaxSerial(pid)+1);
		func.setIseffect("y");
		func.setUrl(url);
		func.setPid(pid);
		funcDao.save(func);
		
		// 更新全路径
		func.setFuncwholename(getFullPath(func.getId()));
		funcDao.update(func);
		return func;
	}
	
	/**
	 * 获取完整功能结构
	 * @Title: getAllFuncList
	 * @author yzp
	 * @date 2014-9-9 下午12:06:38
	 * @return List<Func>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<Func> getAllFuncList(){
		return funcDao.getAllFuncList();
	}
	
	/**
	 * 获取功能的全路径   如：公司/部门/项目组
	 * @Title: getFullPath
	 * @author yzp
	 * @date 2014-9-4 上午10:58:48
	 * @param orgid
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String getFullPath(String orgid){
		Func func = funcDao.findById(orgid);
		String fullPathStr = ""+func.getFuncname();
        if(!"root".equals(func.getId())){        	
        	 String  str = getFullPath(func.getPid());
        	 fullPathStr = str +"/"+fullPathStr;
        	 return fullPathStr;
        }else{
               return fullPathStr;      //当num=0时，循环结束
        }
	}    
	
	/**
	 *  获取角色功能列表
	 * @Title: getFuncList
	 * @author yzp
	 * @date 2014-9-9 下午1:17:26
	 * @param roleid
	 * @return List<Func>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<Func> getFuncList(String roleid){		
		return funcDao.getFuncList(roleid);
	}
	

	/**
	 *  获取角色功能列表
	 * @Title: getFuncList
	 * @author yzp
	 * @date 2014-9-9 下午1:17:26
	 * @param roleid
	 * @return List<Func>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<FuncWB> turnToWB(List<Func> funcList,List<Func> roleFuncList){
		Map roleFuncMap = new HashMap();
		for(Func func : roleFuncList){
			roleFuncMap.put(func.getId(),"");
		}
		List<FuncWB> outList = new ArrayList();
		for(Func func : funcList){
			FuncWB wb = new FuncWB();
			wb.setId(func.getId());
			wb.setFuncname(func.getFuncname());
			wb.setPid(func.getPid());
			
			if(roleFuncMap.containsKey(func.getId())){
				wb.setChecked(true);	
			}
			outList.add(wb);
		} 
		return outList;
	}
	
	/**
	 * 获得功能信息
	 * @Title: getFuncListMG
	 * @author (wmc)
	 * @date 2014-12-5 下午1:40:40
	 * @param funcid
	 * @return Func
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public Func getFuncListMG(String funcid){
		return funcDao.findById(funcid);
	}
	
	/**
	 * 删除功能 （逻辑删除）
	 * @Title: deleteFunc
	 * @author (wmc)
	 * @date 2014-12-5 下午2:20:26
	 * @param funcid
	 * @return Func
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public Func deleteFunc(String funcid){
		Func func= funcDao.findById( funcid );
		func.setIseffect("n");
		funcDao.save(func);
		int count=funcDao.deleteByPid(this.getFullPath(funcid));
	
		return func;
	}
	
	
	/**
	 * 是否允许删除
	 * @Title: isAllowDel
	 * @author (wmc)
	 * @date 2014-12-5 下午2:43:44
	 * @param funcid
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int isAllowDel(String funcid){
		int type=1;
		List funcList= funcDao.getSonFuncList(funcid);
		if(funcList.size()>0){
			type=-1;
		}
		return type;
	}
	
	/**
	 * 更新所有下属子功能的路径
	 * @Title: updateSonFuncWholeName
	 * @author (wmc)
	 * @date 2014-12-5 下午3:34:54
	 * @param funcwholename
	 * @param oldFuncName
	 * @param newFuncName void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public void updateSonFuncWholeName(String funcwholename,String oldFuncName,String newFuncName){
		if(!newFuncName.equals(oldFuncName)){
			String oldPath=funcwholename;
			String newPath="";
			int pos= funcwholename.lastIndexOf("/");
			if(pos!=-1){
				newPath=funcwholename.substring(0,pos+1)+newFuncName;
			}else{
				newPath= newFuncName;
			}
			funcDao.updateFuncWholeName(oldPath, newPath);
		}
	}
	
	/**
	 * 修改功能信息
	 * @Title: updateFunc
	 * @author (wmc)
	 * @date 2014-12-5 下午3:03:01
	 * @param funcid
	 * @param funcname
	 * @param type
	 * @param code
	 * @param funcwholename
	 * @param url
	 * @return Func
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public Func updateFunc(String funcid,String funcname,
			String type,String code,String url){
		Func func=funcDao.findById(funcid);
		String oldFuncName=func.getFuncname();
		String oldFuncWholeName=func.getFuncwholename();
		func.setFuncname(funcname);
		func.setPid(func.getPid());
		func.setType(type);
		func.setCode(code);
		func.setUrl(url);
		func.setFuncwholename(getFullPath(func.getId()));
		funcDao.save(func);
		
		this.updateSonFuncWholeName(oldFuncWholeName, oldFuncName, funcname);
		
		return func;
	}
	
	/**
	 * 编码是否唯一
	 * @Title: isUniCode
	 * @author (wmc)
	 * @date 2014-12-5 下午4:14:08
	 * @param code
	 * @param funcid
	 * @return boolean
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public boolean isUniCode(String code ,String funcid){
		boolean isUni= funcDao.isUniCode(code, funcid);
		return isUni;
	}
	
	/**
	 * 获取子功能
	 * @Title: getSonFuncList
	 * @author (wmc)
	 * @date 2014-12-17 下午4:32:01
	 * @param pid
	 * @return List<Func>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public List<Func> getSonFuncList(String pid){
		return funcDao.getSonFuncList(pid);
	}

}
