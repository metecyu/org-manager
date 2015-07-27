<%@ page contentType="text/html; charset=UTF-8"%>

<div class="headbody" >
	<div class="headerLogo"></div>
	<div class="headerTop">
		<div class="headerLink">
			<span class="welcome">
				<strong>${user.username}</strong>&nbsp;您好!&nbsp;
			</span>
			<!-- <a class="mForm" onclick="return false;" href="admin!middle.do" target="middleFrame">后台首页</a>| -->
	        <a href="http://www.wanchenginfo.com" target="_blank">联系我们</a>
		</div>
	</div>
	<div class="headerBottom">
		<div class="headerMenu">
		</div>
		<div class="userInfo">
			<!-- <a class="mForm" onclick="return false;" href="/lhs/sys/user!self.do" target="middleFrame">
				<span class="profileIcon">&nbsp;</span>个人资料
			</a> -->
			<a href="${ctx}/login/loginoutWithShrio.do" target="_top">
				<span class="logoutIcon">&nbsp;</span>退出&nbsp;
			</a>
		</div>
	</div>
</div>