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
<script type="text/javascript" src="${ctx}/jsGroup/tablednd/jquery.tablednd_0_5.js"></script>
<style>
	#wBox .wBox_body {
		background-color: #ffffff;
		border: 1px solid #A7B5D3;
	}
</style>
<SCRIPT type="text/javascript">
	// 全局变量
	currDelOrgid = "";
	userGender="";
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
				location.href="${ctx}/user/beginViewUser.do?orgid="+treeNode.id+"&currentPage=${userPage.currentPage}";
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
			title:"添加用户",
			requestType:"iframe",
			iframeWH:{width:500,height:370},
			successFunc:afterOrgChange,
			target:"${ctx}/user/beginAddUser.do?orgid=${org.id}" 
		});
		// 修改界面
	    wBoxEditForm = $("#show_box").wBox({
			title:"修改用户",
			requestType:"iframe",
			iframeWH:{width:500,height:370},
			successFunc:afterOrgChange,
			target:""
		});
	 	// 添加角色界面
	    wBoxAssignUserRoleForm = $("#show_box").wBox({
			title:"添加用户角色",
			requestType:"iframe",
			iframeWH:{width:400,height:360},
			//successFunc:afterOrgChange,
			target:""
		});
	 	// 查看用户角色界面
	    wBoxViewUserRoleForm = $("#show_box").wBox({
			title:"查看用户角色",
			requestType:"iframe",
			iframeWH:{width:400,height:260},
			//successFunc:afterOrgChange,
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
		//用户排序框
		wBoxSortUser=$("#show_box").wBox({
			title:"用户排序(拖动用户可调整位置)",
			requestType:"iframe",
			drag:true,
			iframeWH:{width:300,height:550},
			successFunc:afterOrgChange,
			target:""
		});
	   
	});
	
	// 刷新页面
	function refreshForm(){
		location.href="${ctx}/user/beginViewUser.do?orgid=${org.id}&currentPage=${userPage.currentPage}";	
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
	function navEditForm(userid){
		wBoxEditForm.showBox("${ctx}/user/beginEditUser.do?userid="+userid);
	}
	//------ 添加角色 ------
	function navAssignUserRoleForm(userid){
		wBoxAssignUserRoleForm.showBox("${ctx}/right/beginAssignRole.do?userid="+userid);
	}	
	//------ 查看用户角色 ------
	function navViewUserRoleForm(userid){
		wBoxViewUserRoleForm.showBox("${ctx}/right/beginViewUserRole.do?userid="+userid);
	}
	//用户排序
	function navSortUser(orgid){
		wBoxSortUser.showBox("${ctx}/user/beginSortUser.do?orgid="+orgid);
	}
	// ---- 删除用户 ------  
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
		deleteUser(currDelOrgid)
		refreshForm();
	}	 
	// 删除组织
	function deleteUser(userid){
		// alert("xxx")
		 var ret = "";
		 $.ajax({  
           url : '${ctx}/user/deleteUser.do',  
           cache : false,  
           type : 'post',  
           dataType : 'html',  
           async : false,  
           contentType : "application/x-www-form-urlencoded;charset=utf-8",  
           data : {  
               'userid' : userid
           },  
           success : function(htmlRet) { 
        	   //alert("xxx1") 
           }  
        })  
        return ret;
	}
	
	function batchDelete(){
		var ids = new Array();
		$('input[name="userCheck"]:checked').each(function(){   
			// alert($(this).val()	)
			ids.push(	$(this).val()	);   
	    });
		$.ajax({  
           url : '${ctx}/user/batchDeleteUser.do',  
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
		location.href="${ctx}/user/beginViewUser.do?orgid=${org.id}&currentPage="+currentPage;
	}
	
$(document).ready(function(){
	$("[id=${userPager.pageCount}]").hide();
	$("[id=0]").hide();
	$("[name=-1]").hide();
});	

/* $(document).ready(function() {
    // Initialise the table
    $("#sortserTable").tableDnD({
    	onDragClass:'highlight',
    	onDrop:function(){
    		var orgid=$("#orgid").val();
    		//alert('done  +'+this);
    		sortUser(orgid)
    	}
    }); */
	
	
</SCRIPT>

</head>
<body class="list header menu" >
<!-- top 区域  -->
<%@ include file="/includes/top.jsp"%>
<!-- end top  -->

<!-- left menu -->
<%@ include file="/includes/topMenu.jsp"%> 
<!-- end left menu --> 
<!-- handle -->
<div class='handle' style="margin-left:260px;margin-right:0px;overflow:auto">
<div class="titleBar" >
	<span class="titleMessage">当前组织：${org.shortname}</span>
</div>
<div class="body" style="overflow:auto">
	<form id="listForm" action="work_query!list.do" method="post">
		<input type="hidden" name="orgid" id="orgid" value="${org.id}">
		<%@ include file="/includes/currOrgInfo.jsp"%>
		<div class="tab">
			<div class="tab_menu">
				<ul>
					<li  onClick='{location.href="${ctx}/org/beginViewOrg.do?orgid=${org.id}"}'>下属组织</li>
					<li  class="selected" onClick='{location.href="${ctx}/user/beginViewUser.do?orgid=${org.id}"}'>用户管理</li>
					<li  onClick='{location.href="${ctx}/role/beginViewRole.do?orgid=${org.id}"}'>角色管理</li>
				</ul>
				<span style='float:right;'>
					<a href="#" onClick="navSortUser('${org.id}')" title="用户排序">用户排序</a>
				</span>
			</div>
		</div>
		
		<div class='listTableDiv' >
			<table class="listTable" id="userTable" >
				<tbody>
					<tr class="fieldTitle" >
						<th width="30px">
							<span name="number" >
								<input name="test" id="test" type="checkbox" value="${obj.id}" onClick="SelectAll(this.checked,'userCheck');"/>
							</span>
						</th>
					<th width="120px">
						<span name="number">用户</span>
					</th>
					<th >
						<span  name="planLading">登录名称</span>
					</th>
					<!-- <th>
						<span class="sort" name="name">备注</span>
					</th> -->
	<!-- new -->
					<th >
					 <span name="userGender" >性别</span>
					 </th>
	<!-- new -->
					<th >
					 <span name="mobilePhone" >手机号</span>
					 </th>
	<!-- new -->
					<th >
					 <span name="email" >电子邮箱</span>
					 </th>
					 
					<th width="280px">
						操作
					</th>
					<c:forEach items="${pageLevUserList}" var="obj" >
						<tr id="${obj.id }" >
							<td>
									<input name="userCheck"  type="checkbox" value="${obj.id}" />
							</td>
			           		<td>
									${obj.username}
							</td>
							<td>
									${obj.loginid}
							</td>
<!-- new -->
							<td>
									<c:set var="gender" value="${obj.gender }" />
									<c:choose>
									<c:when test="${gender=='0' }">女
									</c:when>
									<c:when test="${gender=='1' }">男
									</c:when>
									<c:otherwise>保密</c:otherwise>
									</c:choose>
									
							</td>
<!-- new -->	      		<td>
							        ${obj.mobile} 
						    </td>
<!-- new -->                <td>
									${obj.email}
						    </td>
							<td>
								<a href="#" onClick="navAssignUserRoleForm('${obj.id}')" title="添加角色">[添加角色]</a>
								<a href="#" onClick="navViewUserRoleForm('${obj.id}')" title="添加角色">[查看用户角色]</a>
								<a href="#" onClick="navEditForm('${obj.id}')" title="修改">[修改]</a>
								<a href="#" onClick="navDelForm('${obj.id}')" title="删除" >[删除]</a>
							</td>
						</tr>	
		           	</c:forEach>
			</tbody></table>
		</div><!-- end listTableDiv -->
	
	<div name=${userPager.currentPage} class="pageBar" >
	<span >
		<a href="#" onClick="toPage(1)" title="首页">首页</a>
		<a id=${userPager.currentPage-1} href="#" onClick="toPage('${userPager.currentPage-1}')" title="上一页">上一页</a>
		<a id=${userPager.currentPage} href="#" onClick="toPage('${userPager.currentPage+1}')" title="下一页">下一页</a>
		<a href="#" onClick="toPage('${userPager.pageCount}')" title="尾页">尾页</a>&nbsp;&nbsp;第${userPager.currentPage}页/共${userPager.pageCount}页  (共${userPager.totalCount }条记录)
	</span>
	</div>	
	
	<div class="addBar" >
		<span >
			<input type="button" class="queryButton" value="新增用户" id="addOrg" onClick="navAddForm()" /> 
		</span> 
		<span>
			<input type="button" id="xx" class="queryButton " value="批量删除" onClick="navBatchDelForm()" />	<!-- -->
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