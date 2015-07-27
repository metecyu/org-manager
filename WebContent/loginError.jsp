<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="login.title"/></title>
    <content tag="heading"><fmt:message key="login.heading"/></content>
    <meta name="menu" content="Login"/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["theme"]}/layout-1col.css'/>" />
</head>

<body id="login"/>

<script type="text/javascript">
<!--
	function mask( inputbox ){
		var text = inputbox.value;
		var tempText = "";
		for( var i = 0; i < text.length; i ++ ){
			var curChar = text.charAt(i);
			if( curChar != ' ' && curChar != '\'' )
				tempText = tempText + curChar;
		}
		inputbox.value = tempText;

	}
//-->
</script>
    
<form method="post" id="loginForm" action="<c:url value="/j_security_check"/>"
    onsubmit="saveUsername(this);return validateForm(this)">
<fieldset>
<ul>
<c:if test="${param.error != null}">
    <li class="error">
        <img src="<c:url value="/images/iconWarning.gif"/>"
            alt="<fmt:message key="icon.warning"/>" class="icon" />
        <fmt:message key="errors.password.mismatch"/>
    </li>
</c:if>

	<li>
       <label for="j_username" class="required desc">
            <B><font color="red"><fmt:message key="login.check"/></font></B>
        </label>
    </li>
    
    <li>
       <label for="j_username" class="required desc">
            <fmt:message key="label.username"/> <span class="req">*</span>
        </label>
        <input type="text" name="j_username" id="j_username" tabindex="1" size=18 onkeyup="mask(this);"/>
    </li>

    <li>
        <label for="j_password" class="required desc">
            <fmt:message key="label.password"/> <span class="req">*</span>
        </label>&nbsp;&nbsp;
        <input type="password" name="j_password" id="j_password" tabindex="2" size=18 onkeyup="mask(this);"/>
    </li>

<c:if test="${appConfig['rememberMeEnabled']}">
    <li>
        <input type="checkbox" class="checkbox" name="rememberMe" id="rememberMe" tabindex="3"/>
        <label for="rememberMe" class="choice"><fmt:message key="login.rememberMe"/></label>
    </li>
</c:if>
    <li>
        <input type="submit" class="button" name="login" value="<fmt:message key="button.login"/>" tabindex="4" />
    </li>
</ul>
</fieldset>
</form>


