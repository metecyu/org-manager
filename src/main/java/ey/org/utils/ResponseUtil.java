package ey.org.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 输出字符串
 * @Title: ResponseUtil.java
 * @Package ey.org.utils
 * @Description: 输出字符串2
 * 
 * @author yzp
 * @date 2014-9-1 下午1:51:45
 * @version V1.0
 */
public class ResponseUtil {
	public static void writeResponseStr(HttpServletResponse response, String ret) {
		try {  
            response.setCharacterEncoding("UTF-8");  
            PrintWriter pw = response.getWriter();  
            pw.write(ret);  
            pw.flush();  
            pw.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
}
