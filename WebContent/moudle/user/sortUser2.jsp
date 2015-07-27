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

	// 刷新页面
	function refreshForm(){
		location.href="${ctx}/user/beginSortUser.do?orgid=${org.id}";	
	}
	
	function submitSort(){
		var orgid=$("#orgid").val();
		sortUser(orgid);
		
		$('.wBox_submit').trigger("click"); //触发父窗体提交动作
	}

	function scrollUp(){
		if (document.getElementById("sortUserScroll"))
     		 document.getElementById("sortUserScroll").scrollTop=document.getElementById("sortUserScroll").scrollTop-40;   
		else if (document.documentElement) 
      		 document.documentElement.scrollTop=document.documentElement.scrollTop-40;
		
		//document.getElementById("sortUserScroll").scrollTop = document.getElementById("sortUserScroll").scrollTop-25;
		//alert(document.getElementById("sortUserScroll").scrollTop);
	}
	
	function scrollDown(){
		alert(document.documentElement.scrollTop);
		/* if (document.getElementById("sortUserScroll"))
     		 document.getElementById("sortUserScroll").scrollTop=document.getElementById("sortUserScroll").scrollTop+40;   
		else if ($(document)) 
      		 $(document).scrollTop=$(document).scrollTop+40; */
		//document.getElementById("sortUserScroll").scrollTop = document.getElementById("sortUserScroll").scrollTop+25;
		//alert(document.getElementById("sortUserScroll").scrollTop);
	}
	
	function sortUser(orgid){
		var ids=new Array();
		$('input[name="sortid"]').each(function(){
			ids.push(	$(this).val()	);
		});	
		$.ajax({
			url:'${ctx}/user/sortUser.do',
			cache:false,
			type:'post',
			dataType:'html',
			async:false,
			contentType:"application/x-www-form-urlencoded;charset=utf-8",  
			data:{
				'ids':ids,
				'orgid':orgid
			},
			success:function(Ret){
				//$('.wBox_submit').trigger("click"); //触发父窗体提交动作
			}
		})
	}

	$(document).ready(function() {
    // Initialise the table
    $("#sortUserTable").tableDnD({
    	onDragClass:'highlight',
    	onDrop:function(){
    		//var orgid=$("#orgid").val();
    		//alert('done  +'+this);
    		//sortUser(orgid)
    		//scrollDown();
    	}
    });
	
});
	
</SCRIPT>

</head>
<body >
<div id="sortUserScroll" class="body" style="margin-left:0px;margin-right:0px;width:300px;overflow:auto;display:inline-block;height:500px"> 
	<form id="listForm" action="work_query!list.do" method="post">
		<input type="hidden" name="orgid" id="orgid" value="${org.id}" />
		<div class='listTableDiv' >
			<table class="listTable" id="sortUserTable">
				<tbody>
					<tr class="fieldTitle nodrop nodrag" >
					<th width="120px">
						<span name="number">用户</span>
					</th>
					<th width="120px">
						<span  name="planLading">登录名称</span>
					</th>
					</tr>
					<c:forEach items="${levUserList}" var="obj" >
						<tr id="${obj.id }" >
			           		<td>
			           		<input type="hidden" name="sortid" id="sortid" value="${obj.id }" />
									${obj.username}
							</td>
							<td>
									${obj.loginid}
							</td>
						</tr>	
		           	</c:forEach>
			</tbody></table>
		</div><!-- end listTableDiv -->
		
	</form>
		
</div>	<!-- end div body -->
<div class="buttonArea" style ="width:300px;margin-top:5px">
			<input type="button" class="wBox_close formButton" hidefocus="true" onclick="submitSort()" value="确定" />
			<input type="hidden" class="wBox_submit" />
			<!-- <input type="button"  onclick="scrollDown()" value="向下滚动" />
			<input type="button"   onclick="scrollUp()" value="向上滚动" /> -->
		</div>
<div id="show_box"></div>


</body></html>