package ey.org.utils;


public class ParamUtil {
	
	
	
	/**
	 * 获取参数的值，过滤null 和 "" 
	 * @Title: getParamValue
	 * @author yzp
	 * @date 2014-7-17 下午3:05:09
	 * @param paramValue
	 * @param defaultValue
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
   public static String getParamValue(String paramValue,String defaultValue){
	   if(paramValue==null){
		   return defaultValue;
	   }else if("".equals(paramValue)){
		   return defaultValue;
	   }
	   return paramValue;
    }
   
}
