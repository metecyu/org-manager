<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/includes/commFormJsCss.jsp"%>
<link rel="stylesheet" href="${ctx}/jsGroup/zTree_v3//css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/jsGroup/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/jsGroup/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<style>

</style>
</head>
<script> 

	var setting = {
			check: {
				enable: true,
				nocheckInherit: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
		callback: {
			onClick: function (event, treeId, treeNode) {
				// location.href="${ctx}/user/beginViewUser.do?orgid="+treeNode.id;
			}
		}
	};
	var zNodes =[
           <c:forEach items="${orgList}" var="obj" varStatus="status">
           		{ id:"${obj.id}", pId:"${obj.pid}", name:"${obj.shortname}", open:false, nocheck:true},
           </c:forEach>
            <c:forEach items="${roleList}" var="obj">
           		{ id:"${obj.id}", pId:"${obj.orgid}", name:"${obj.rolename}",  checked:false,nocheck:false, icon:"${ctx}/cssGroup/org/role.png"},
           </c:forEach>
	]; 
	
	$().ready(function() {
		// 加载配置 及 数据
		$.fn.zTree.init($("#treeOrg"), setting, zNodes);
		var ztree=$.fn.zTree.getZTreeObj("treeOrg"); 
		ztree.selectNode(ztree.getNodeByParam("id","${orgid}", null)); 
		
		// 设置root菜单打开
		var treeObj = $.fn.zTree.getZTreeObj("treeOrg");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length>0) {
			treeObj.expandNode(nodes[0], true, false, true);
		}

	})
	
	function assignUserRole(){	
		// 获取选择的角色列表
		var treeObj = $.fn.zTree.getZTreeObj("treeOrg");
		var nodes = treeObj.getCheckedNodes(true);
		
		var ids = new Array();  
		for (var i =0;i<nodes.length>0;i++) {
			// alert(nodes[i].id);
			ids.push(   nodes[i].id   );   
			
		}
		$.ajax({    
            url : '${ctx}/right/assignUserRole.do',    
            cache : false,    
            type : 'post',    
            dataType : 'html',    
            async : false,    
            contentType : "application/x-www-form-urlencoded;charset=utf-8",    
            data : {    
                'ids' : ids,
                'userid':'${userid}'
            },    
            success : function(htmlRet) {  
            	if(htmlRet=="-1"){
            		alert("添加角色失败");
                }
            	
            	/* else if(htmlRet=="0"){
                	alert("未添加角色");
                } */
                
                else{
                	// alert("成功添加角色");
                	$('.wBox_close').trigger("click"); //触发父窗体提交动作
                }
            }    
         })    
         
	}
</script>

<!-- handle -->
<div class='handle' style="margin-left:0px;boder-size:1px">
	<!-- <div class="body"> --> 
	<div>
	<form id="inputForm" class="validate" action="carry_ship!save.do" method="post">
		<input type="hidden" name="userid" id="userid" value="${userid}">
		
		<div style="text-algin:center;">
			<dl id="orgTree" style="width:395px;height:300px;overflow:auto;border-style:solid;border-color: #f5f5f5;">
			<ul id="treeOrg" class="ztree"></ul>
		</dl>
			
		</div>
		<div class="buttonArea" style ="width:380px;margin-top:0px">
			<input id="submitButton" name="submitButton "  type="button" class="formButton" value="确  定" hidefocus="true" onClick="assignUserRole()" >&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="wBox_close formButton"   value="取消" hidefocus="true">
			<input type="hidden" class="wBox_submit" >
		</div>
	</form>
</div>
</div> <!-- end handle  -->

</body></html>
