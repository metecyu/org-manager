<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>${title}</title>
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
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: function (event, treeId, treeNode) {
				location.href="${ctx}/role/beginViewRole.do?orgid="+treeNode.id;
			}
		}
	};
	var zNodes =[
           <c:forEach items="${orgList}" var="obj">
           	{ id:"${obj.id}", pId:"${obj.pid}", name:"${obj.shortname}", open:true},
           </c:forEach>
	];
	
	$(document).ready(function(){
		// 生成树形目录
		$.fn.zTree.init($("#treeOrg"), setting, zNodes);
		var ztree=$.fn.zTree.getZTreeObj("treeOrg"); 
		ztree.selectNode(ztree.getNodeByParam("id","${orgid}", null)); 
		
		// 新建界面
		wBoxAddForm = $("#show_box").wBox({
			title:"添加角色",
			requestType:"iframe",
			iframeWH:{width:500,height:270},
			successFunc:afterOrgChange,
			target:"${ctx}/role/beginAddRole.do?orgid=${org.id}" 
		});
		// 修改界面
	    wBoxEditForm = $("#show_box").wBox({
			title:"修改角色",
			requestType:"iframe",
			iframeWH:{width:500,height:270},
			successFunc:afterOrgChange,
			target:""
		});
		// 应用权限界面
	    wBoxAssignRoleFuncForm = $("#show_box").wBox({
			title:"角色应用权限设置",
			requestType:"iframe",
			iframeWH:{width:620,height:470},
			// successFunc:afterOrgChange,
			target:""
		});
		 // 数据权限界面
	    wBoxAssignRoleDataForm = $("#show_box").wBox({
			title:"角色数据权限设置",
			requestType:"iframe",
			iframeWH:{width:620,height:470},
			// successFunc:afterOrgChange,
			target:""
		});			
	    // 删除确认框
	    wBoxConfirm = $("#show_box").wBox({
			title:"确认框",
			requestType:"div",
			isDisBtn:1,
			successFunc:confirmDelSubmit,
			msg:"是否删除"
		});
	 	// 批量删除确认框
	    wBoxBatchDelConfirm = $("#show_box").wBox({
			title:"确认框",
			requestType:"div",
			isDisBtn:1,
			successFunc:batchDelete,
			msg:"是否批量删除"
		});
	   
	});
	
	// 刷新页面
	function refreshForm(){
		location.href="${ctx}/role/beginViewRole.do?orgid=${org.id}&currentPage=${rolePager.currentPage}";	
	}
	
	//---------------- 弹出框控制---------------- 
	//------ 新建 ------
	function navAddForm(){
		wBoxAddForm.showBox();
	}
	// 成功添加 、删除以后刷洗页面
	function afterOrgChange(){
		refreshForm();
	}
	
	//------ 修改 ------
	function navEditForm(roleid){
		wBoxEditForm.showBox("${ctx}/role/beginEditRole.do?roleid="+roleid);
	}
	
	//------ 分配应用权限 ------
	function navAssignRoleFuncForm(roleid){
		wBoxAssignRoleFuncForm.showBox("${ctx}/right/beginAssignRoleFunc.do?roleid="+roleid);
	}
	
	//------ 分配数据权限 ------
	function navAssignRoleDataForm(roleid){
		wBoxAssignRoleDataForm.showBox("${ctx}/right/beginAssignRoleData.do?roleid="+roleid);
	}	
	
	//------ 删除 ------  
	function navDelForm(orgid){
		currDelOrgid = orgid;
		wBoxConfirm.showBox();	
	}	
	//------ 批量删除 ------  
	function navBatchDelForm(){		
		wBoxBatchDelConfirm.showBox();	
	}
	// 弹出框 确认删除时 执行的方法
	function confirmDelSubmit(){
		deleteRole(currDelOrgid)
		refreshForm();
	}	 
	
	// 删除组织
	function deleteRole(roleid){
		// alert("xxx")
		 var ret = "";
		 $.ajax({  
           url : '${ctx}/role/deleteRole.do',  
           cache : false,  
           type : 'post',  
           dataType : 'html',  
           async : false,  
           contentType : "application/x-www-form-urlencoded;charset=utf-8",  
           data : {  
               'roleid' : roleid
           },  
           success : function(htmlRet) { 
        	   //alert("xxx1") 
           }  
        })  
        return ret;
	}
	
	function batchDelete(){
		var ids = new Array();
		$('input[name="roleCheck"]:checked').each(function(){   
			// alert($(this).val()	)
			ids.push(	$(this).val()	);   
	    });
		$.ajax({  
           url : '${ctx}/role/batchDeleteRole.do',  
           cache : false,  
           type : 'post',  
           dataType : 'html',  
           async : false,  
           contentType : "application/x-www-form-urlencoded;charset=utf-8",  
           data : {  
               'ids' : ids
           },  
           success : function(htmlRet) { 
        	   //alert("xxx1") 
        	   
           }  
        })  
        refreshForm();
	}
	
	function SelectAll(checked,checkbox_id) {  
	    var checkboxs=document.getElementsByName(checkbox_id);  
	    for (var i=0;i<checkboxs.length;i++) {  
	      var e=checkboxs[i];  
	      e.checked=checked;  
	    }  
	}  
	
		//new
	function toPage(currentPage){
		location.href="${ctx}/role/beginViewRole.do?orgid=${org.id}&currentPage="+currentPage;
	}
	
$(document).ready(function(){
	$("[id=${rolePager.pageCount}]").hide();
	$("[id=0]").hide();
	$("[name=-1]").hide();
});	
	
</SCRIPT>

</head>
<body class="list header menu">
<!-- top 区域  -->
<%@ include file="/includes/top.jsp"%>
<!-- end top  -->

<!-- left menu -->
<%@ include file="/includes/topMenu.jsp"%> 
<!-- end left menu --> 
<!-- handle -->
<div class='handle' style="margin-left:260px">
<div class="titleBar" >
	<span class="titleMessage">当前组织：${org.shortname}</span>
</div>
<div class="body"  >
	<form id="listForm" action="work_query!list.do" method="post">
		<input type="hidden" name="orgid" id="orgid" value="${org.id}">
		<%@ include file="/includes/currOrgInfo.jsp"%>
		<div class="tab">
			<div class="tab_menu">
				<ul>
					<li  onClick='{location.href="${ctx}/org/beginViewOrg.do?orgid=${org.id}"}'>下属组织</li>
					<li onClick='{location.href="${ctx}/user/beginViewUser.do?orgid=${org.id}"}'>用户管理</li>
					<li class="selected" onClick='{location.href="${ctx}/role/beginViewRole.do?orgid=${org.id}"}'>角色管理</li>
					
					
				</ul>
				<span style='float:right;'>
					<a href="${ctx}/func/beginViewFunc.do" target='_blank'>功能项配置</a>
				</span>
			</div>
		</div>
		<div class='listTableDiv' > 
			<table class="listTable" > 
				<tbody>
					<tr class="fieldTitle">
						<th width="30px">
							<span name="number" >
								<input name="test" id="test" type="checkbox" value="${obj.id}" onClick="SelectAll(this.checked,'roleCheck');"/>
							</span>
						</th>
					<th width="120px">
						<span name="number">角色名称</span>
					</th>
					<th width="100px">
						<span  name="planLading">角色编号</span>
					</th>
					<th width="100px">
						<span  name="planLading">角色分类</span>
					</th>
					<th>
						<span  name="name">角色全称</span>
					</th>
					
					
					<th width="220px">
						操作
					</th>
					<c:forEach items="${pageRoleList}" var="obj">
						<tr>
							<td>
									<input name="roleCheck"  type="checkbox" value="${obj.id}" />
							</td>
			           		<td>
									${obj.rolename}
							</td>
							<td>
									${obj.rolesign}
							</td>
							<td>
									${obj.rolegroup}
							</td>
							<td>
									${obj.roleWholename}
							</td>
							<td>
								<a href="#" onClick="navAssignRoleFuncForm('${obj.id}')" title="应用资源权限">[应用权限]</a>
								<a href="#" onClick="navAssignRoleDataForm('${obj.id}')" title="应用资源权限">[数据权限]</a>
								<a href="#" onClick="navEditForm('${obj.id}')" title="修改">[修改]</a>
								<a href="#" onClick="navDelForm('${obj.id}')" title="删除" >[删除]</a>
							</td>
						</tr>	
		           	</c:forEach>
			</tbody></table>
		</div><!-- end listTableDiv -->
		
		<div name=${rolePager.currentPage} class="pageBar">
	<span >
		<a href="#" onClick="toPage(1)" title="首页">首页</a>
		<a id=${rolePager.currentPage-1} href="#" onClick="toPage('${rolePager.currentPage-1}')" title="上一页">上一页</a>
		<a id=${rolePager.currentPage} href="#" onClick="toPage('${rolePager.currentPage+1}')" title="下一页">下一页</a>
		<a href="#" onClick="toPage('${rolePager.pageCount}')" title="尾页">尾页</a>&nbsp;&nbsp;第${rolePager.currentPage}页/共${rolePager.pageCount}页 (共${rolePager.totalCount }条记录)  
	</span>
	</div>	
	<div class="addBar" >
		<span >
			<input type="button" class=" queryButton" value="新增角色" id="addOrg" onClick="navAddForm()"> 
		</span>
		<span>
			<input type="button" id="xx" class="queryButton " value="批量删除" onClick="navBatchDelForm()">	<!-- -->
		</span>
	
	</div>
	<input type="hidden" name="pager.pageNumber" id="pageNumber" value="1">
	<input type="hidden" name="pager.orderBy" id="orderBy" value="createDate">
	<input type="hidden" name="pager.orderType" id="order" value="desc">
	</form>
		
</div>	<!-- end div body -->
</div> <!-- end handle  -->

<div id="show_box"></div>


</body></html>