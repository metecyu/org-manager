package ey.org.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import ey.orgclient.pub.FuncH;
import ey.orgclient.pub.OrgH;
import ey.orgclient.pub.RoleH;
import ey.orgclient.pub.UserH;
import ey.orgclient.pub.model.OrgRole;
import ey.orgclient.pub.model.OrgUser;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {

	@Resource
	private UserH userH;
	@Resource
	private OrgH orgH; 
	@Resource
	private RoleH roleH; 
	@Resource
	private FuncH funcH; 
	/**
     * 获取登陆用户的角色、授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	System.out.println(" 加载用户 的角色 许可信息  ...  ");
        String loginid = (String)principals.getPrimaryPrincipal();
        
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 加载角色
        String userid = userH.findByLonginId(loginid).getId();
        List<OrgRole> roles = roleH.findByUserid(userid);      
        Set<String> roleNoList = new HashSet();
        for (OrgRole orgRole : roles) {
			roleNoList.add(orgRole.getRolesign());
		}
        authorizationInfo.setRoles(roleNoList);
        // 加载许可（permission）
        Set<String> perms = new HashSet();    
        perms.add("org:list");       
        perms.add("org:navNew");
        
        authorizationInfo.setStringPermissions(perms);
        
        return authorizationInfo;
    }
    /**
     * 获取登陆用户的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	System.out.println(" doGetAuthenticationInfo 2 ...  ");
        String username = (String)token.getPrincipal();
        UsernamePasswordToken tokenPass= (UsernamePasswordToken)token;
        String pwd = tokenPass.getPassword()+"";
        String md5 = new Md5Hash(pwd, "").toString();
        OrgUser user = userH.findByLonginId(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

       /* if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }*/

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getLoginid(), //用户名
                user.getPassword(), //密码
                user.getUsername()
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
