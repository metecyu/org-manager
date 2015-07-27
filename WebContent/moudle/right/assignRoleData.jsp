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
			data: {
				simpleData: {
					enable: true
				}
			},
		callback: {
			onClick: function (event, treeId, treeNode) {	
				if(!treeNode.isRole){
					return;
				}
				var treeObj = $.fn.zTree.getZTreeObj("treeOrg");
				var nodes = treeObj.getNodesByParam("open",true, null);
				var str = "";
				for(var i=0;i<nodes.length;i++){
					str = str+","+nodes[i].id;
				}
				if(str.length>0){
					str = str.substring(1,str.length)
				}
				//alert(str);
				// 设置当前代开的目录，传回服务器，用于回显
				$("#openOrgStr").val(str);
				var form = document.forms["inputForm"];
				form.action = "${ctx}/right/beginAssignRoleData.do?roleid="+treeNode.id;
				form.method = "post";
				form.submit();
			}
		}
	};
	var zNodes =[
           <c:forEach items="${orgList}" var="obj" varStatus="status">
           		{ id:"${obj.id}", pId:"${obj.pid}", name:"${obj.shortname}", open:false, nocheck:true},
           </c:forEach>
            <c:forEach items="${roleList}" var="obj">
           		{ id:"${obj.id}", pId:"${obj.orgid}", name:"${obj.rolename}",  checked:false,nocheck:false, icon:"${ctx}/cssGroup/org/role.png",isRole:true},
           </c:forEach>
	];
	// ===========================
	var settingFunc = {
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
	var zNodesFunc =[
           <c:forEach items="${roleOrgList}" var="obj" varStatus="status">
           		{ id:"${obj.id}", pId:"${obj.pid}", name:"${obj.shortname}", checked:"${obj.checked}",open:true, nocheck:false},
           </c:forEach>

	]; 
	
	$().ready(function() {
		// ==========orgtree==========
		// 加载配置 及 数据
		$.fn.zTree.init($("#treeOrg"), setting, zNodes);
		var ztree=$.fn.zTree.getZTreeObj("treeOrg"); 
		ztree.selectNode(ztree.getNodeByParam("id","${orgid}", null)); 
		
		// 设置选中当前角色
		ztree.selectNode(ztree.getNodeByParam("id","${roleid}", null)); 
		// 设置上一次请求前  展开项目
		var datastr = $("#openOrgStr").val();
		var str= new Array();   
		str=datastr.split(",");      
		for (i=0;i<str.length ;i++ ) {   
			//alert(str[i]);   
			var node = ztree.getNodeByParam("id",str[i], null)
			ztree.expandNode(node, true, false, true);
		}  
		
		// ==========functree==========
		settingFunc.check.chkboxType = { "Y" : "", "N" : ""}; //设置选择子功能选择项时，不选择父功能项（ztree官网有介绍）
		$.fn.zTree.init($("#funcTree"), settingFunc, zNodesFunc);
		var ztreeFunc=$.fn.zTree.getZTreeObj("funcTree"); 

	})
	
	
	function assignUserRole(){	
		// 获取选择的角色列表
		var treeObj = $.fn.zTree.getZTreeObj("funcTree");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = new Array();  
		for (var i =0;i<nodes.length>0;i++) {
			// alert(nodes[i].id);
			ids.push(   nodes[i].id   );   
		}
		$.ajax({    
            url : '${ctx}/right/updateRoleData.do',    
            cache : false,    
            type : 'post',    
            dataType : 'html',    
            async : false,    
            contentType : "application/x-www-form-urlencoded;charset=utf-8",    
            data : {    
                'ids' : ids,
                'roleid':'${roleid}'
            },    
            success : function(htmlRet) {  
            	if(htmlRet=="-1"){
            		alert("添加功能失败");
                }
            	
            	/* else if(htmlRet=="0"){
                	alert("未添加角色");
                } */
                
                else{
                	alert("更新角色部门权限成功");
                	// $('.wBox_close').trigger("click"); //触发父窗体提交动作
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
		<input type="hidden" name="openOrgStr" id="openOrgStr" value="${openOrgStr}">
		
		
		<div style="text-algin:center;float:left;width:300px;">
			<dl style="width:300px;height:400px;overflow:auto;border-style:solid;border-color: #f5f5f5;">
				<ul id="treeOrg" class="ztree"></ul>
			</dl>
		</div>
		
		<div style="text-algin:center;float:left;width:300px;margin-left:10px">
			<dl  style="width:300px;height:400px;overflow:auto;border-style:solid;border-color: #f5f5f5;">
				<ul id="funcTree" class="ztree"></ul>
			</dl>			
		</div>
		<div style="clear:both;"/>
		<div class="buttonArea" style ="width:610px;margin-top:30px">
			<input id="submitButton" name="submitButton "  type="button" class="formButton" value="确  定" hidefocus="true" onClick="assignUserRole()" >&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="wBox_close formButton"   value="取消" hidefocus="true">
			<input type="hidden" class="wBox_submit" >
		</div>
	</form>
</div>
</div> <!-- end handle  -->

</body></html>