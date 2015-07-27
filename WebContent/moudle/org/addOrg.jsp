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
	        	"orgName": "required",
	        	"code": {
	                remote: {
	                    type: "post",
	                    url: "${ctx}/org/isUniCode.do",
	                    data: {
	                    	code: function() {
	                            return $("#code").val();
	                        }
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
	        	orgName: "请填写名称",
	        	code: {
	                remote: "编码已存在"
	            }
		  	}
	    });
	})
	
	function saveOrg(){
		if($("#inputForm").valid()){
			$.ajax({  
	        url:'${ctx}/org/addOrg.do',  
	            //url:'${ctx}/index.jsp',  
	            cache:false,  
	            type:'post',  
	            dataType:'html',      
	            data:{   
	            	pid:$("#pid").val(),  
	            	orgName:$("#orgName").val(),  
	            	orgQname:$("#orgQname").val(),
	            	code:$("#code").val(),
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
		<input type="hidden" name="pid" id="pid" value="${pid}">
		<div class="input">
			
			<table class="inputTable tabContent">
				<tbody><tr>
					<th>
						名称:
					</th>
					<td>
						<input type="text" id="orgName" name="orgName" class="inputText" value="">
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						全称:
					</th>
					<td>
						<input type="text" id="orgQname" name="orgQname" class="inputText ac_input" value="" autocomplete="off" >
					
					</td>
				</tr>
				<tr>
					<th>
						编码:
					</th>
					<td>
						<input type="text" id="code" name="code" class="inputText" value="">
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