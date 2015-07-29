<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/includes/commFormJsCss.jsp"%>

</head>
<script> 
	$().ready(function() {
		$("#inputForm").validate({
	        rules: {
	        	"username": "required",
	        	"loginid": {
	        		required:true,
	                remote: {
	                    type: "post",
	                    url: "${ctx}/user/isUniCode.do",
	                    data: {
	                    	loginid: function() {
	                            return $("#loginid").val();
	                        },
	                        userid:$("#userid").val()
	                    },
	                    dataType: "html",
	                    dataFilter: function(data, type) {
	                        if (data == "1")
	                            return true;
	                        else
	                            return false;
	                    }
	                }
	            },
	            pwd:{  
	            	required: true,
                    minlength: 6  //自带判断字符最小长度
                },  
                pwdRepeat: { 
                	 required: true,
                     minlength: 6,
                     equalTo: "#pwd"
                },
                email:{
                	/* required:true, */
                	email:true
                },
                mobile:{
                	/* required:true, */
                	number:true,
                	minlength:11,
                	maxlength:11
                }
		  	},
	        messages: {
	        	username: "请填写姓名",
	        	loginid: {
	        		required: "请填写登录名",
	                remote: "角色编号已存在"
	            },
	            pwd:{  
	                required: '请输入密码',  
	                minlength: "确认密码不能小于6个字符"
	            },  
	            pwdRepeat: {  
                    required: "请输入确认密码",  
                    minlength: "确认密码不能小于6个字符",  
                    equalTo: "两次输入密码不一致"  
                },  
                email:{
                	/* required:"请输入电子邮件地址", */
                	email:"电子邮件地址格式不正确"
                },
                mobile:{
                	/* required:"请输入手机号", */
                	number:"手机号为11位数字",
                	minlength:"手机号为11位数字",
                	maxlength:"手机号为11位数字"
                }
		  	}
	    });
	})
	
	function saveOrg(){
		if($("#inputForm").valid()){
			$.ajax({  
	        url:'${ctx}/user/updateUser.do',  
	            //url:'${ctx}/index.jsp',  
	            cache:false,  
	            type:'post',  
	            dataType:'html',      
	            data:{   
	            	userid:$("#userid").val(),  
	            	username:$("#username").val(),  
	            	loginid:$("#loginid").val(),
	            	pwd:$("#pwd").val(),
	            	mobile:$("#mobile").val(),
	            	email:$("#email").val(),
	            	gender:$("#gender").val(),
	        		contentType: "application/x-www-form-urlencoded;charset=utf-8"  
	            },  
	            success:function(ret){  
	                if(ret=="1"){
	                	$('.wBox_submit').trigger("click"); //触发父窗体提交动作
	                }
	            }  
	        })  
		
		}
	}
	
	
</script>


<!-- handle -->
<body >
<div class='handle' style="margin-left:0px;overflow:auto" >
	<div class="body">
	<form id="inputForm" class="validate" action="carry_ship!save.do" method="post">
		<input type="hidden" name="userid" id="userid" value="${user.id}" />
		<div class="input">
			
			<table class="inputTable tabContent">
				<tbody>
					<tr>
						<th>
							用户姓名:
						</th>
						<td>
							<input type="text" id="username" name="username" class="inputText" value="${user.username }" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<th>
							登录名称:   
						</th>
						<td>
							<input type="text" id="loginid" name="loginid" class="inputText ac_input" value="${user.loginid }" autocomplete="off" />
							<label class="requireField">*</label>
						</td>
					</tr>
					
	<!-- new -->
					<tr>
						<th>
							性别:
						</th>
						<td>
							<select id="gender" name="gender">
								<option value="2" ${user.gender=="2"?'selected':'' }>保密</option>
								<option value="1" ${user.gender=="1"?'selected':'' }>男</option>
								<option value="0" ${user.gender=="0"?'selected':'' }>女</option>
							</select>
							<label class="requireField">*</label>						
						</td>
					</tr>
					
	<!-- new -->
					<tr>
						<th>
							手机号:
						</th>
						<td>
							<input type="text" id="mobile" name="mobile" class="inputText ac_input" value="${user.mobile }" autocomplete="off" />
							<!-- <label class="requireField">*</label>			 -->			
						</td>
					</tr>
					
	   <!-- new -->
					<tr>
						<th>
							电子邮箱:
						</th>
						<td>
							<input type="text" id="email" name="email" class="inputText ac_input" value="${user.email }" autocomplete="off" />
							<!-- <label class="requireField">*</label>		 -->				
						</td>
					</tr>
					
					<tr>
						<th>
							密码:
						</th>
						<td>
							<input type="password" id="pwd" name="pwd" class="inputText ac_input" value="${user.password }" autocomplete="off"  disabled="disabled"/>
						<!-- 	<label class="requireField">*</label>		 -->				
						</td>
					</tr>
					<tr>
						<th>
							重复密码:
						</th>
						<td>
							<input type="password" id="pwdRepeat" name="pwdRepeat" class="inputText ac_input" value="${user.password }" autocomplete="off"  disabled="disabled" />
							<!-- <label class="requireField">*</label>	 -->					
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="buttonArea" style ="width:500px;margin-top:30px">
			<input id="submitButton" name="submitButton "  type="button" class="formButton" value="确  定" hidefocus="true" onClick="saveOrg()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="wBox_close formButton"   value="取消" hidefocus="true" />
			<input type="hidden" class="wBox_submit" />
		</div>
	</form>
</div>
</div> <!-- end handle  -->

</body></html>