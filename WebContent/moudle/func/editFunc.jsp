<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/includes/commListJsCss.jsp"%>
</head>
<!-- 

暂时无用

 -->
<script>
	
	
	$().ready(function(){
		$("#inputForm").validate({
			rules:{
				"funcname":"required",
				"funcid":"required",
			},
			messages:{
				funcname:"请填写名称",
				funcid:"请填写编号",
			}
		});
	})
	
	function saveFunc(){
		
		if($("#inputForm").valid()){
			$.ajax({
				url:'${ctx}/func/updateFunc.do',
				cache:false,
				type:'post',
				dataType:'html',
				data:{
					funcid:$("#funcid").val(),
					funcname:$("#funcname").val(),
					funcwholename:$("#funcwholename").val(),
					code:$("#code").val(),
					type:$("#type").val(),
					url:$("#url").val(),
					contentType:"application/x-www-form-urlencoded;charset=utf-8"
				},
				success:function(ret){
					if(ret=="1"){
					//	$('.wBox_submit').trigger("click");
					}
				}
			})
		}
	}
	


</script>

<div class='handle' style="margin-left:260px">
	<div class="titleBar" >
		<span class="titleMessage">当前功能：${func.funcname}</span>
	</div>
<div class="body" >
<form id="inputForm" class="validate" action="carry_ship!save.do" method="post">
		<input type="hidden" name="funcid" id="funcid" value="${func.id}" />
		<div class="input">
			
			<table class="inputTable tabContent">
				<tbody><tr>
					<th>
						功能名称:
					</th>
					<td>
						<input type="text" id="funcname" name="funcname" class="inputText" value="${func.funcname}"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						功能编号:
					</th>
					<td>
						<input type="text" id="code" name="code" class="inputText ac_input" value="${func.code}" autocomplete="off" />
					
					</td>
				</tr>
				<tr>
					<th>
						功能全称:
					</th>
					<td>
						<input type="text" id="funcwholename" name="funcwholename" class="inputText ac_input" value="${func.funcwholename}" autocomplete="off" />
					
					</td>
				</tr>
				<tr>
					<th>
						访问链接:
					</th>
					<td>
						<input type="text" id="url" name="url" class="inputText ac_input" value="${func.url}" autocomplete="off" />
					</td>
				</tr>
				<tr>
					<th>
						类型:
					</th>
					<td>
						<select id="type" name="type">
								<option value="1" ${func.type=="1"?'selected':'' }>访问控制</option>
								<option value="2" ${func.type=="2"?'selected':'' }>可用性</option>
								<option value="3" ${func.type=="3"?'selected':'' }>访问控制+可用性</option>
							</select>
					</td>
				</tr>
			</tbody></table>
		</div>
		<div class="buttonArea" style ="width:500px;margin-top:30px">
			<input id="submitButton" name="submitButton "  type="button" class="formButton" value="确  定" hidefocus="true" onclick="saveFunc()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="wBox_close formButton"   value="取消" hidefocus="true">
		</div>
	</form> 
	</div> 
</div>
	
	

</body></html>