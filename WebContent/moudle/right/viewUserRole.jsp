<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/includes/commListJsCss.jsp"%>
<link rel="stylesheet" href="${ctx}/jsGroup/zTree_v3//css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/jsGroup/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<style>
	#wBox .wBox_body {
		background-color: #ffffff;
		border: 1px solid #A7B5D3;
	}
</style>
<SCRIPT type="text/javascript">
	// 全局变量
	currDelOrgid = "";
	
	function refreshTree(){
		// alert($("#orgTree").attr("scrollTop"));
		 $("#orgTree").scrollTop(100);
		 // alert($("#orgTree").scrollTop())
		// alert(document.all.orgTree.scrollTop1+" a " + document.all.orgTree.scrollLeft1)
	}
	//---------------- 初始化  树结构---------------- 
	
	
	$(document).ready(function(){
		
	   
	});
	
	// 刷新页面
	function refreshForm(){
		location.reload();
		// self.location.href="${ctx}/right/beginViewUserRole.do?userid=${userid}";
	}
	
	
	// 删除组织
	function deleteUserRole(relid){
		
		// alert("xxx")
		/* */ var ret = "";
		 $.ajax({  
           url : '${ctx}/right/deleteUserRole.do',  
           cache : false,  
           type : 'post',  
           dataType : 'html',  
           async : false,  
           contentType : "application/x-www-form-urlencoded;charset=utf-8",  
           data : {  
               'relid' : relid
           },  
           success : function(htmlRet) { 
        	   refreshForm();
           }  
        })  
        return ret; 
	}
</SCRIPT>

</head>

<!-- end left menu --> 
<!-- handle -->

<div class="body" >
	<form id="listForm" action="work_query!list.do" method="post">
		<input type="hidden" name="userid" id="userid" value="${userid}">
		<div class='listTableDiv' style="height:200px; overflow:auto;"" >
			<c:if test="${0 == roleListSize}">  
			    <div style="margin-left:100px;margin-top:50px;font-size:24px">用户未分配角色</div>  
			</c:if> 
			
			<table class="listTable" >
				<tbody>
					<c:forEach items="${roleList}" var="obj">
						<tr>
			           		<td>
			           			${obj.orgwholename}
							</td>
							<td>
								<span ><img style="vertical-align:bottom" src="${ctx}/cssGroup/org/role.png"/> </span> ${obj.rolename}
							</td>
							
							<td>
								<a href="javascript:void(0)" onClick="deleteUserRole('${obj.userRoleRelid}')" title="删除" >[删除]</a>
							</td>
						</tr>	
		           	</c:forEach>
			</tbody></table>
		</div><!-- end listTableDiv -->
	<div class="buttonArea" style ="margin-top:10px;margin-right:20px;text-align:right;">
			<input type="button" class="wBox_close formButton"   value="取消" hidefocus="true">
			<input type="hidden" class="wBox_submit" >
			
		</div>
	</form>
		
</div>	<!-- end div body -->
</div> <!-- end handle  -->

<div id="show_box"></div>


</body></html>