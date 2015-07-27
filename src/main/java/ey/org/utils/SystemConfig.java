

package ey.org.utils;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 *
 * @author yzp
 */
@Repository
public class SystemConfig {
    
    private Map flowCommMap;
    private Map missionflowCommMap;
    public Map getFlowCommMap() {
		return flowCommMap;
	}
	public void setFlowCommMap(Map flowCommMap) {
		this.flowCommMap = flowCommMap;
	}
	
	public Map getMissionflowCommMap() {
		return missionflowCommMap;
	}
	public void setMissionflowCommMap(Map missionflowCommMap) {
		this.missionflowCommMap = missionflowCommMap;
	}
	/**
	 * 获取流程命令 对应的值（实现）
	 * @Title: getFlowCommMapValue
	 * @author yzp
	 * @date 2014-8-19 上午10:55:26
	 * @param key
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String getFlowCommMapValue(String key){
        if (this.getFlowCommMap().containsKey(key)){
            return ""+this.getFlowCommMap().get(key);
        }else{
            return "";
        }
    }
   
	/**
	 * 获取流程命令 对应的值（实现）
	 * @Title: getFlowCommMapValue
	 * @author yzp
	 * @date 2014-8-19 上午10:55:26
	 * @param key
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String getMissionFlowCommMapValue(String key){
        if (this.getMissionflowCommMap().containsKey(key)){
            return ""+this.getMissionflowCommMap().get(key);
        }else{
            return "";
        }
    }
}
