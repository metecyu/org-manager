<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${title}</title>
<%@ include file="/includes/commListJsCss.jsp"%>
<link rel="stylesheet" href="${ctx}/jsGroup/zTree_v3//css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${ctx}/jsGroup/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/jsGroup/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/jsGroup/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
<style>

</style>
 <style type="text/css"> .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle} </style>
<script>
	function refreshTree(){
		 $("#funcTree").scrollTop(100);	 
	}
	
	var setting = {
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
			},
			data:{
				simpleData:{
					enable:true
				}
			},
			callback:{
				onClick: function (event, treeId, treeNode){
					//alert("click"+treeNode.name);
					location.href="${ctx}/func/beginViewFunc.do?funcid="+treeNode.id;
				},
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename
			},
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom
			}
		};
		
	var zNodes =[
			<c:forEach items="${funcList}" var="obj">
			 {id:"${obj.id}", pId:"${obj.pid}", name:"${obj.funcname}", open:true},
			</c:forEach>
	];
	
	var log, className = "dark";
	var newCount = 1;
	$(document).ready(function(){
		$.fn.zTree.init($("#treeFunc"),setting, zNodes );
		var ztree=$.fn.zTree.getZTreeObj("treeFunc");
		ztree.selectNode(ztree.getNodeByParam("id","${func.id}", null));
		
	});
	
	function refreshForm(){
		location.href="${ctx}/func/beginViewFunc.do?funcid=${func.id}";	
	}
	
	function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='增加功能' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeFunc");
				zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
				//添加功能子功能
				addFunc(treeNode.id);
				return false;
			});
		};
	function beforeDrag(treeId, treeNodes) {
			return false;
		}
	function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeFunc");
			zTree.selectNode(treeNode);
			return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			//showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeFunc");
			zTree.selectNode(treeNode);
			return confirm("确认删除 功能-- " + treeNode.name + " 吗？");
		}
		function onRemove(e, treeId, treeNode) {
			deleteFunc(treeNode.id);
			//alert("1");
			//showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("treeFunc");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}	
	function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		
	function showRemoveBtn(treeId, treeNode) {
			return true;
		};
		
	function showRenameBtn(treeId, treeNode) {
			/* return !treeNode.isLastNode; */
		};
		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}
		
		function batchDelete(){
			if(confirm("确定删除这些功能吗？")){
			var seldel= new Array();
		$('input[name="selectDelete"]:checked').each(function(){   
			// alert($(this).val()	)
			seldel.push(	$(this).val()	);   
	    });
		$.ajax({  
           url : '${ctx}/func/batchDeleteFunc.do',  
           cache : false,  
           type : 'post',  
           dataType : 'html',  
           async : false,  
           contentType : "application/x-www-form-urlencoded;charset=utf-8",  
           data : {  
               'seldel' : seldel
           },  
           success : function(ret) { 
        	  if(ret=="1"){
					alert("修改成功");
				}
				else
				{
					alert("修改失败");
				}
           }  
        })  
      refreshForm();
        }
		}
		
		function deleteFunc(id){
			$.ajax({
				url:'${ctx}/func/deleteFunc.do',
				cache:false,
				type:'post',
		 		dataType:'html',
		  		async:false,
		  		contentType:"application/x-www-form-urlencoded;charset=utf-8",
				data:{
					'funcid':id
				},
				success:function(htmlRet){
					refreshForm();
					alert("删除成功！");
				}
			})
		};
			
	function addFunc(pId){
		var ifuncname="newnode";
		var icode="";
		var iurl="";
		var itype=3;
		$.ajax({
		  url:'${ctx}/func/addFunc.do',
		  cache:false,
		  type:'post',
		  dataType:'html',
		  async:false,
		  contentType:"application/x-www-form-urlencoded;charset=utf-8",
		  data:{
		  	pid:pId,
		  	'funcname':ifuncname,
		  	'code':icode,
		  	'url':iurl,
		  	'type':itype
		  },
		  success:function(htmlRet){
		  	refreshForm();
		  }
		})
	};
</script>
	
<script>
$().ready(function(){
	$("#inputForm").validate({
		rules:{
			"funcname":"required",
			"code":{
				required:true
			}
		},
		messages:{
			funcname:"请填写功能名",
			code:{
				required:"请填写编号",
				remote:"功能编号已存在"
			}
		}
	});
})

function saveFunc(){
	var funcidlists= new Array();
	var funcnamelists= new Array();
	var codelists= new Array();
	var urllists= new Array();  
	var typelists= new Array();
	$('input[name="funcidlist"]').each(function(){
		funcidlists.push( $(this).val() );
	})
	$('input[name="funcnamelist"]').each(function(){
		funcnamelists.push( $(this).val() );
	})
	$('input[name="codelist"]').each(function(){
		codelists.push( $(this).val() );
	})
	$('input[name="urllist"]').each(function(){
		urllists.push( $(this).val() );
	})
	$('select[name="typelist"]').each(function(){
		typelists.push( $(this).val() );
	})
	if($("#inputForm").valid()){
		$.ajax({
			url:'${ctx}/func/updateFunc.do',
			cache:false,
			type:'post',
			dataType:'html',
			contentType:"application/x-www-form-urlencoded;charset=utf-8",
			data:{
				funcid:$("#funcid").val(),
				funcname:$("#funcname").val(),
				funcwholename:$("#funcwholename").val(),
				code:$("#code").val(),
				type:$("#type").val(),
				url:$("#url").val(),
				'funcidlist':funcidlists,
				'funcnamelist':funcnamelists,
				'codelist':codelists,
				'urllist':urllists,
				'typelist':typelists
			},
			success:function(ret){
				if(ret=="1"){
					refreshForm();
					alert("修改成功");
				}
				else
				{
					alert("修改失败");
				}
			}
		})
	}
}
	
</script>
</head>
<body class="list header menu"  >

<%-- <%@ include file="/includes/top.jsp"%> --%>
<div class="headbody">
	<div class="headerLogo"></div>
	<div class="headerTop">
		<div class="headerLink">
			<span class="welcome">
				<strong>${user.username}</strong>&nbsp;您好!&nbsp;
			</span>
			<!-- <a class="mForm" onclick="return false;" href="admin!middle.do" target="middleFrame">后台首页</a>| -->
	        <a href="http://www.wanchenginfo.com" target="_blank">联系我们</a>
		</div>
	</div>
	<div class="headerBottom">
		<div class="headerMenu">
		</div>
		<div class="userInfo">
			<!-- <a class="mForm" onclick="return false;" href="/lhs/sys/user!self.do" target="middleFrame">
				<span class="profileIcon">&nbsp;</span>个人资料
			</a> -->
			<a href="${ctx}/login/loginout.do" target="_top">
				<span class="logoutIcon">&nbsp;</span>退出&nbsp;
			</a>
		</div>
	</div>
</div>


<div id='menuContent' class="menuContent " style="width:25%">
	
		<dl>
			<dt>
				<span>功能项管理</span>
			</dt>
			
		</dl>
		<dl id="funcTree" style="width:100%;height:90%;overflow:auto">
			<ul id="treeFunc" class="ztree"></ul>
		</dl>
</div>

<div class="middle" style ="float:left;width:6px;height:100%;">
	<div class="main leftArrow"  ></div>
</div> 
<%-- <%@ include file="/includes/funcTopMenu.jsp" %> --%>
<%-- <%@ include file="/moudle/func/editFunc.jsp"%> --%>
<%-- <%@ include file="/includes/commFormJsCss.jsp"%> --%>
<%-- <%@ include file="/moudle/func/editFunc.jsp"%> --%>
<!-- -->
<script type="text/javascript" src="${ctx}/common/validate/jquery.validate.js"></script>

<div class='body' style="margin-left:25%;height:90%" > 
	<div class="titleBar" >
		<span class="titleMessage">当前功能：${func.funcname}</span>
	</div>
	<div class="body" style="overflow:auto" >
	 <form id="inputForm" class="validate" action="carry_ship!save.do" method="post">
		<input type="hidden" name="funcid" id="funcid" value="${func.id}" />
			<div class="queryBar" >
				<span>
					<label class="queryTitle">功能名称:</label>
						<input type="text" id="funcname" name="funcname" class="inputText" style="height: 25px; " value="${func.funcname}"/>
						
				</span>
				<span>
					<label class="queryTitle">功能编号:</label>
						<input type="text" id="code" name="code" class="inputText" style="height: 25px; " value="${func.code}"  style="width: 96px; "/>
				</span>
				</div>
				<div class="queryBar" >
				<span>
					<label class="queryTitle">访问链接:</label>
						<input type="text" id="url" name="url" class="inputText" style="height: 25px; " value="${func.url}"  style="width: 200px; "/>
				</span>	
				<span>
					<label class="queryTitle" >类&nbsp;&nbsp;&nbsp;&nbsp;型:</label>	
						<select id="type" name="type" style="height: 25px; ">
							<option value="1" ${func.type=="1"?'selected':'' }>访问控制</option>
							<option value="2" ${func.type=="2"?'selected':'' }>可用性</option>
							<option value="3" ${func.type=="3"?'selected':'' }>访问控制+可用性</option>
						</select>
				</span> 
				</div>
				
		<div class='tab' >
		<div class="tab_menu" >
			<ul>
			<li style="width:165px">当前功能下的子功能</li>
			</ul>
		</div>
		</div>	
		
 	
	
	 <div class='listTableDiv' style="height:400px">
 	
	<table class="listTable" style="overflow:auto">
		<tbody>
			<tr class="fieldTitle" >
					<th width="10px"><span></span></th>
					<th width="80px">
						<span>编码</span>
					</th>
					<th width="280px">
						<span>名称</span>
					</th>
					<th width="300px">
						<span>访问链接</span>
					</th>	
					<th width="80px">
						<span>访问类型</span>
					</th>
					<c:forEach items="${sonFuncList}" var="obj">
						<tr>
							<td>
								<input type="checkbox" name="selectDelete" value="${obj.id }"/>
							</td>
			           		<td>
			           		<input type="hidden" name="funcidlist" id="funcidlist" value="${obj.id} "/>
							<input type="text" name="codelist" id="codelist" value="${obj.code}" style="width:100%;"/>					
							</td>
							<td>
							<input type="text" name="funcnamelist" id="funcnamelist" value="${obj.funcname}" style="width:100%;"/>		
							</td>
							<td>
							<input type="text" name="urllist" id="urllist" value="${obj.url}" style="width:100%;"/>	
							</td>
							<td>
							<select id="typelist" name="typelist">
							<option value="1" ${obj.type=="1"?'selected':'' }>访问控制</option>
							<option value="2" ${obj.type=="2"?'selected':'' }>可用性</option>
							<option value="3" ${obj.type=="3"?'selected':'' }>访问控制+可用性</option>
						</select>	
							</td>
						</tr>
		           	</c:forEach>
		           	</tr>
			</tbody>
	</table>
	</div><!-- end listTableDiv -->
	<div class="buttonArea" style ="width:500px;margin-top:10px">
			<input type="button" class="formButton" value="新增功能" hidefocus="true" onClick="addFunc('${func.id}')"/>
			
			<input type="button" class="formButton"   value="批量删除" hidefocus="true" onClick="batchDelete()"/>
			<input id="submitButton" name="submitButton "  type="button" class="formButton" value="修  改" hidefocus="true" onClick="saveFunc()" />&nbsp;&nbsp;&nbsp;&nbsp;	
				
	</div>
	 </form>
	</div><!-- end bodydiv -->
</div><!-- end handlediv -->

</body>
</html>