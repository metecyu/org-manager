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
	        	"roleName": "required",
	        	"roleSign": {
	                remote: {
	                	async:false, 
	                    type: "post",
	                    url: "${ctx}/role/isUniCode.do",
	                    data: {
	                    	roleSign: function() {
	                            return $("#roleSign").val();
	                        },
	                        roleid:$("#roleid").val()
	                      
	                    },
	                    dataType: "html",
	                    dataFilter: function(data, type) {
	                        if (data == "1")
	                            return true;
	                        else
	                            return false;
	                    }
	                }
	            }
		  	},
	        messages: {
	        	roleName: "请填写名称",
	        	roleSign: {
	                remote: "角色编号已存在"
	            }
		  	}
		  	
	    });
	})
	
	function saveOrg(){
		if($("#inputForm").valid()){
			$.ajax({  
	        url:'${ctx}/role/updateRole.do',  
	            //url:'${ctx}/index.jsp',  
	            cache:false,  
	            type:'post',  
	            dataType:'html',      
	            data:{   
	            	roleid:$("#roleid").val(),  
	            	roleName:$("#roleName").val(),  
	            	roleSign:$("#roleSign").val(),
	            	details:$("#details").val(),
	            	rolegroup:$("#rolegroup").val(),
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
<div class='handle' style="margin-left:0px">
	<div class="body">
	<form id="inputForm" class="validate" action="carry_ship!save.do" method="post">
		<input type="hidden" name="roleid" id="roleid" value="${role.id}">
		<div class="input">
			
			<table class="inputTable tabContent">
				<tbody><tr>
					<th>
						角色名称:
					</th>
					<td>
						<input type="text" id="roleName" name="roleName" class="inputText" value="${role.rolename}">
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						角色编号:
					</th>
					<td>
						<input type="text" id="roleSign" name="roleSign" class="inputText ac_input" value="${role.rolesign}" autocomplete="off" >
					
					</td>
				</tr>
				<tr>
					<th>
						角色分类:
					</th>
					<td>
						<input type="text" id="rolegroup" name="rolegroup" class="inputText ac_input" value="${role.rolegroup}" autocomplete="off" >
					
					</td>
				</tr>
				<tr>
					<th>
						描述:
					</th>
					<td>
						<textarea rows="3" cols="30" class="inputText ac_input" id="details" name="details">${role.details}</textarea>
					</td>
				</tr>
			</tbody></table>
		</div>
		<div class="buttonArea" style ="width:500px;margin-top:30px">
			<input id="submitButton" name="submitButton "  type="button" class="formButton" value="确  定" hidefocus="true" onClick="saveOrg()" >&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="wBox_close formButton"   value="取消" hidefocus="true">
			<input type="hidden" class="wBox_submit" >
		</div>
	</form>
</div>
</div> <!-- end handle  -->

</body></html>