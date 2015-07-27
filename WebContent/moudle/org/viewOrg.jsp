<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
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
				location.href="${ctx}/org/beginViewOrg.do?orgid="+treeNode.id;
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
			title:"添加组织",
			requestType:"iframe",
			iframeWH:{width:500,height:200},
			successFunc:afterOrgChange,
			target:"${ctx}/org/beginAddOrg.do?orgid=${org.id}" 
		});
		// 修改界面
	    wBoxEditForm = $("#show_box").wBox({
			title:"修改组织",
			requestType:"iframe",
			iframeWH:{width:500,height:200},
			successFunc:afterOrgChange,
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
	});
	
	// 刷新页面
	function refreshForm(){
		location.href="${ctx}/org/beginViewOrg.do?orgid=${org.id}&currentPage=${orgPage.currentPage}";	
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
	function navEditForm(orgid){
		wBoxEditForm.showBox("${ctx}/org/beginEditOrg.do?orgid="+orgid);
	}	
	
	//------ 删除 ------  
	function navDelForm(orgid){
		currDelOrgid = orgid;
		wBoxConfirm.showBox();	
	}	
	// 弹出框 确认删除时 执行的方法
	function confirmDelSubmit(){
		var ret = isAllowDel(currDelOrgid)
		// alert(ret)
		if(ret=="1"){
			deleteOrg(currDelOrgid)
			refreshForm();
		}
		// wBoxConfirm.close();
	}	 
	// 是否组织是否可以删除 
	function isAllowDel(orgid){
		 var ret = "";
		 $.ajax({  
           url : '${ctx}/org/isAllowDel.do',  
           cache : false,  
           type : 'post',  
           dataType : 'html',  
           async : false,  
           contentType : "application/x-www-form-urlencoded;charset=utf-8",  
           data : {  
               'orgid' : orgid
           },  
           success : function(htmlRet) { 
        	   ret = htmlRet;
        	   // alert("ret:" +ret);
               // 异常处理  
               if(ret=="-1"){//
                   alert("存在子部门");   
                   return ret;  
               }else if(ret=="1"){  
            	  
                   return ret;  
               } 
           }  
        })  
        return ret;
	}
	// 删除组织
	function deleteOrg(orgid){
		// alert("xxx")
		 var ret = "";
		 $.ajax({  
           url : '${ctx}/org/deleteOrg.do',  
           cache : false,  
           type : 'post',  
           dataType : 'html',  
           async : false,  
           contentType : "application/x-www-form-urlencoded;charset=utf-8",  
           data : {  
               'orgid' : orgid
           },  
           success : function(htmlRet) { 
        	   //alert("xxx1") 
           }  
        })  
        return ret;
	}
	
		function toPage(currentPage){
		location.href="${ctx}/org/beginViewOrg.do?orgid=${org.id}&currentPage="+currentPage;
	}
	
$(document).ready(function(){
	$("[id=${orgPager.pageCount}]").hide();
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
<div class="body" >
	<form id="listForm" action="work_query!list.do" method="post">
		<input type="hidden" name="orgid" id="orgid" value="${org.id}">
		<%@ include file="/includes/currOrgInfo.jsp"%>
		<div class="tab">
			<div class="tab_menu">
				<ul>
					<li class="selected" onClick='{location.href="${ctx}/org/beginViewOrg.do?orgid=${org.id}"}'>下属组织</li>
					<li onClick='{location.href="${ctx}/user/beginViewUser.do?orgid=${org.id}"}'>用户管理</li>
					<li onClick='{location.href="${ctx}/role/beginViewRole.do?orgid=${org.id}"}'>角色管理</li>
				</ul>
			</div>
		</div>
		<div class='listTableDiv' >
			<table class="listTable" >
				<tbody><tr class="fieldTitle">
					<th width="120px">
						<span name="number">编码</span>
					</th>
					<th width="100px">
						<span  name="planLading">名称</span>
					</th>
					<th>
						<span name="name">组织全称</span>
					</th>
					
					
					<th width="120px">
						操作
					</th>
					<c:forEach items="${pageLevOrgList}" var="obj"> 
						<tr>
			           		<td>
										${obj.code}
							</td>
							<td>
									${obj.shortname}
							</td>
							<td>
									${obj.orgwholename}
							</td>
							<td>
								<a href="#" onClick="navEditForm('${obj.id}')" title="修改">[修改]</a>
								<a href="#" onClick="navDelForm('${obj.id}')" title="删除" >[删除]</a>
							</td>
						</tr>	
		           	</c:forEach>
			</tbody></table>
		</div><!-- end listTableDiv -->
		<div name=${orgPager.currentPage} class="pageBar" >
	<span  >
		<a href="#" onClick="toPage(1)" title="首页">首页</a>
		<a id=${orgPager.currentPage-1} href="#" onClick="toPage('${orgPager.currentPage-1}')" title="上一页">上一页</a>
		<a id=${orgPager.currentPage} href="#" onClick="toPage('${orgPager.currentPage+1}')" title="下一页">下一页</a>
		<a href="#" onClick="toPage('${orgPager.pageCount}')" title="尾页">尾页</a>&nbsp;&nbsp;第${orgPager.currentPage}页/共${orgPager.pageCount}页 (共${orgPager.totalCount }条记录)  
	</span>
	</div>
	<div class="addBar" >
		<span >
			<shiro:hasPermission name="org:navNew">  
			<input type="button" class=" queryButton" value="新增组织" id="addOrg" onClick="navAddForm()">
			</shiro:hasPermission>  
		</span>
		<span>
			<!-- <input type="button" id="searchButton" class="queryButton " value="批量删除"> -->
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