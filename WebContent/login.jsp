<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>组织管理系统</title>
<link href="${ctx}/common/css/base_css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/common/js/base_js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${ctx}/common/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/common/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/common/validate/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${ctx}/common/validate/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${ctx}/common/js/base_js/jquery.jqModal.js"></script>
<script type="text/javascript" src="${ctx}/common/js/base_js/jquery.jqDnR.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript" src="${ctx}/common/js/base_js/belatedPNG.js"></script>
<![endif]-->
<script type="text/javascript" src="${ctx}/common/js/base_js/base.js"></script>
<script type="text/javascript" src="${ctx}/common/js/base_js/dialog.js"></script>
<style type="text/css">
body{
	 text-align:center;
	 background:url(${ctx}/common/images/admin/admin_login.jpg);
	 background-repeat:repeat-x;
	 background-color:#e6e7eb;
	 height:100%;
}

#content{
	width:522px;
	height:279px;
	margin:0 auto;
	margin-top:17%;
}
#header{
	margin-left:0px;
}
.clearfloat{clear:both;height:0;font-size: 1px;line-height: 0px;} 

.input{
	background:url(${ctx}/common/images/login/input_bg.gif) 0 0 no-repeat;
	border:0px;
	width:162px;
	height:26px;
	line-height:21px;
	font-size:14px;
	padding-left:5px;
}
.margin_bl{
	margin-left:5px;
	display:inline;
}
.captchaImage {
	cursor: pointer;
}
.login_btn{
	background:url(${ctx}/common/images/login/login_button.gif) 0 0 no-repeat; 
}
.help_btn{
	background:url(${ctx}/common/images/login/help_button.gif) 0 0 no-repeat; 
	
}
.f_left{
	float:left;
}
.margin_left{
	margin-left:90px;
	display:inline;
}
.margin_top1{
	margin-top:20px;
	margin-left:140px;
	display:inline;
}
.margin_top2{
	margin-top:20px;
	margin-left:20px;
	display:inline;
}
.c_top{
	margin-top:3px;
	display:inline;
}

.btn{
	border:none; 
	cursor:pointer; 
	width:109px; 
	height:26px;
}
.backg{
	background:url(${ctx}/common/images/login/login_backg.gif);
	width:522px;
	
}
.username{
	float:left;
	margin-left:40px;
	display:inline;
}
.search *{
	vertical-align:middle;
}

#captcha{
	TEXT-TRANSFORM: uppercase;
}
</style>
<script type="text/javascript">
	$().ready( function() {
		if($("#msg").val()=="1"){
			$.dialog({type: "warn", content: "账号信息不正确!", modal: true, autoCloseTime: 3000});
		}
		var $loginForm = $("#loginForm");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $isRememberUsername = $("#isRememberUsername");
		var $isRememberPassword = $("#isRememberPassword");
		// 刷新验证码
		$captchaImage.click( function() {
			var timestamp = (new Date()).valueOf();
			var imageSrc = $captchaImage.attr("src");
			if(imageSrc.indexOf("?") >= 0) {
				imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
			}
			imageSrc = imageSrc + "?timestamp=" + timestamp;
			$captchaImage.attr("src", imageSrc);
			
			$captcha.focus();
		});		
		
		$("#login").click( function() {
			$loginForm.submit();
		});
		
		$("input").keydown(function(event){
			if(event.which==13){
				window.event.keyCode = 9;
			}
		});

		$("#captcha").keydown(function(event){
			if(event.which==13){
				$loginForm.submit();
			}
		});
		
		$(".input").focus(function(){
			$(this).select();
		});
		
		// 登录页面若在框架内，则跳出框架
		if (self != top) {
			top.location = self.location;
		};
		
		// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.attr("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
			// 判断"记住密码"功能是否默认选中,并自动填充密码
			if(getCookie("adminPassword") != null) {
				$isRememberPassword.attr("checked", true);
				$password.val(getCookie("adminPassword"));
				$captcha.focus();
			} else {
				$isRememberPassword.attr("checked", false);
				$password.focus();
			}
		} else {
			$isRememberUsername.attr("checked", false);
			$username.focus();
		}
		

		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.dialog({type: "warn", content: "请输入用户名!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($password.val() == "") {
				$.dialog({type: "warn", content: "请输入密码!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($captcha.val() == "") {
				$.dialog({type: "warn", content: "请输入验证码!", modal: true, autoCloseTime: 3000});
				return false;
			}
			//记住用户名
			if($isRememberUsername.attr("checked") == true) {
				var expires = new Date();
				expires.setTime(expires.getTime() + 1000 * 60 * 60 * 24 * 7);
				setCookie("adminUsername", $username.val(), expires);
			} else {
				deleteCookie("adminUsername");
			}	
			//记住密码
			if($isRememberPassword.attr("checked") == true) {
				var expires = new Date();
				expires.setTime(expires.getTime() + 1000 * 60 * 60 * 24 * 7);
				setCookie("adminPassword", $password.val(), expires);
			} else {
				deleteCookie("adminPassword");
			}
		});
		
	
	});
</script>
</head>
<body>
<form id="loginForm" class="loginForm" action="${ctx}/login/loginWithShrio.do" method="post" >
<%-- <form id="loginForm" class="loginForm" action="${ctx}/login/login.do" method="post" > --%>

<input type="hidden" class="input" id="msg" name="msg" value='${msg}'/></div>
<div id="content" >
	<div id="header">
		<div><img src="${ctx}/common/images/login/login_start.gif" /></div>
	</div>
	<div id="center">
		<div  class="backg search">
			<div class="f_left margin_left"><img src="${ctx}/common/images/login/username.gif"/></div>
			<div class="f_left"><input type="text" class="input" id="username" name="j_username" tabindex="1" maxlength="18"/></div>
			<div class="f_left c_top"><input id="isRememberUsername" type="checkbox" hidefocus="true"  /></div>
			<div class="f_left"><img src="${ctx}/common/images/login/reusername.gif"/></div>
			<div class="clearfloat"></div>
		</div>	
		<div  class="backg search">
			<div class="f_left margin_left"><img src="${ctx}/common/images/login/pwd.gif"/></div>
			<div class="f_left"><input id="password" type="password" class="input" name="j_password" tabindex="2" maxlength="18"/></div>
			<div class="f_left c_top"><input id="isRememberPassword" type="checkbox" hidefocus="true" /></div>
			<div class="f_left"><img src="${ctx}/common/images/login/repwd.gif" /></div>
			<div class="clearfloat"></div>
		</div>
		
		<div  class="backg search">
			<div class="f_left margin_top1"><input type="button" id="login" class="btn login_btn" value=""></div>
			<div class="f_left margin_top2"><input type="button" class="btn help_btn" value=""></div>
			<div class="clearfloat"></div>
		</div>
	</div>
	<div id="bottom" >
		<div><img src="${ctx}/common/images/login/login_end.gif" /></div>
	</div>
	<%-- <div id="end"><img src="${ctx}/common/images/login/end_end.gif" /></div> --%>
</div>
</form>
</body>
</html>