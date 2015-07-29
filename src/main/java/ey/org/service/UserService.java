package ey.org.service;

import java.util.List;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Service;

import ey.org.dao.OrgDao;
import ey.org.dao.UserDao;

import ey.org.model.User;
import ey.org.model.UserOrgRel;

/**
 * 用户管理
 * @ClassName: OrgService
 * @author yzp
 * @date 2014-8-28 上午11:02:40
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
public class UserService {
	@Resource
	private OrgDao orgDao;
	
	@Resource
	private UserDao userDao;
	/**
	 * 获取用户列表
	 * @Title: getUserList
	 * @author yzp
	 * @date 2014-9-4 上午11:46:44
	 * @param orgid
	 * @return List<User>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<User> getUserList(String orgid){
		return userDao.getUserList(orgid);
	}
	
	/**
	 * 
	 * @Title: getPageUserList分页
	 * @author (wmc)
	 * @date 2014-12-1 上午10:02:35
	 * @param orgid
	 * @param currentPage
	 * @return List<User>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	
	public List<User> getPageUserList(String orgid,int currentPage){
		return userDao.getPageUserList(orgid, currentPage);
	}
	
	/**
	 * 
	 * @Title: getPageCount获取分页总页数
	 * @author (wmc)
	 * @date 2014-12-1 下午1:18:47
	 * @param orgid
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int getPageCount(String orgid){
		return userDao.getPageCount(orgid);
	}
	
	/**
	 * 获取用户对象
	 * @Title: findById
	 * @author yzp
	 * @date 2014-9-4 上午11:46:32
	 * @param roleid
	 * @return User    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public User findById(String roleid){
		return userDao.findById(roleid);
	}
	
	/**
	 * 添加用户
	 * @Title: addUser
	 * @author yzp
	 * @date 2014-9-4 上午11:46:20
	 * @param orgid
	 * @param username
	 * @param loginid
	 * @param pwd
	 * @return User    
	 * @throws BadHanyuPinyinOutputFormatCombination 
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public User addUser(String orgid,String username ,String loginid ,String pwd,
			String email,String gender,String mobile) throws BadHanyuPinyinOutputFormatCombination{
		User user = new User();
		user.setId(userDao.genarateDeptId(username));
		user.setUsername(username);
		user.setLoginid(loginid);
		user.setPassword(pwd);
		user.setIseffect("y");
     	user.setEmail(email);
		user.setGender(gender);
        user.setMobile(mobile);
		userDao.save(user);
		
		// 部门关联
		UserOrgRel rel = new UserOrgRel();
		rel.setIsmain("y");
		rel.setOrgid(orgid);
		rel.setUserid(user.getId());
		rel.setSort(userDao.findMaxSerial(orgid));
		userDao.saveObject(rel);
		return user;
		
	}
	/**
	 * 更新用户
	 * @Title: updateUser
	 * @author yzp
	 * @date 2014-9-4 上午11:46:07
	 * @param userid
	 * @param username
	 * @param loginid
	 * @param pwd
	 * @return User    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	 
	public User updateUser(String userid,String username ,String loginid ,String pwd,
			String email,String gender,String mobile){
		User user = userDao.findById(userid);
		// role.setOrgid(orgid);
		user.setUsername(username);
		user.setLoginid(loginid);
		// user.setPassword(pwd);
		user.setMobile(mobile);
		user.setGender(gender);
		user.setEmail(email);
		userDao.save(user);
		return user;
	}
	/**
	 * 删除用户（逻辑删除）
	 * @param User
	 * @return
	 * @Description:
	 * @throws 
	 */
	public User deleteUser(String userid)  {
		User user = userDao.findById(userid);
		user.setIseffect("n");
		userDao.save(user);
		return user;
	}
	
	/**
	 * 登陆id 是否重名
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午2:26:33
	 * @param loginid
	 * @return boolean    
	 * @throws 
	 * @Description: 
	 */
	public boolean isUniCode(String loginid,String userid) {
		boolean isUni = userDao.isUniCode(loginid,userid);
		return isUni;
	}
	
	/**
	 * 用户排序
	 * @Title: moveUser
	 * @author (wmc)
	 * @date 2014-12-25 上午9:31:51
	 * @param userid1
	 * @param userid2
	 * @return boolean
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public boolean moveUser(String userid1,String userid2){
		return userDao.moveUser(userDao.getSort(userid1), userDao.getSort(userid2));
	}
	
	/**
	 * 通过用户名和登录名获取用户id
	 * @Title: getUserId
	 * @author (wmc)
	 * @date 2015-1-5 下午4:40:22
	 * @param loginid
	 * @param userid
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String getUserId(String loginid){
		return userDao.getUserId(loginid);
	}
	
	/**
	 * 修改用户组织关系
	 * @Title: updateRel
	 * @author (wmc)
	 * @date 2015-1-5 下午5:23:41
	 * @param orgid
	 * @param userid void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public void updateRel(String orgid,String userid){
		userDao.updateRel(orgid, userid);
	}
	
	/**
	 * 更新用户排序
	 * @Title: updateSortUser
	 * @author (wmc)
	 * @date 2015-1-9 上午10:19:56
	 * @param userid
	 * @param orgid
	 * @param sort void
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public void updateSortUser(String userid,String orgid,String sort){
		userDao.updateSortUser(orgid, userid, sort);
	}
	
}
