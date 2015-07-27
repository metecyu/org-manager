package ey.orgclient.serviceSqlImp.dao;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import ey.orgclient.model.User;
import ey.orgclient.serviceSqlImp.dao.UserMapper;

/**
 * @Title: MyBatisTest.java
 * @Package ey.org.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午9:54:46
 * @version V1.0
 */
public class MyBatisTest {
	/** 
     * 获得MyBatis SqlSessionFactory   
     * SqlSessionFactory负责创建SqlSession，一旦创建成功，就可以用SqlSession实例来执行映射语句，commit，rollback，close等方法。 
     * @return 
     */  
    private static SqlSessionFactory getSessionFactory() {  
        SqlSessionFactory sessionFactory = null;  
        String resource = "./orgClientRes/configuration.xml";  
        try {  
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources  
                    .getResourceAsReader(resource));  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return sessionFactory;  
    }  
  
    public static void main(String[] args) {  
        SqlSession sqlSession = getSessionFactory().openSession();  
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);  
        User user = userMapper.findById("yzp");  
        System.out.println(user.getUsername());  
  
    }  
}
