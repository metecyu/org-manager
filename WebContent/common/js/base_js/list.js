/***
 *	SHOP++ Register JavaScript
 *
 *	http://www.shopxx.net
 *
 *	Copyright (c) 2008 SHOP++. All rights reserved.
 **/

$().ready( function() {

	var $allCheck = $(".list input.allCheck");// 全选复选框
	var $idsCheck = $(".list input[name='ids']");// ID复选框
	var $deleteButton = $(".list input.deleteButton");// 删除按钮
	var $recoverButton = $(".list input.recoverButton");// 恢复按钮
	
	var $listForm = $(".list #listForm");// 列表表单
	var $searchButton =  $("#searchButton");// 查询按钮
	var $pageNumber = $("#pageNumber");// 当前页码
	var $pageSize = $("#pageSize");// 每页显示数
	var $sort = $(".list .sort");// 排序
	var $orderBy = $("#orderBy");// 排序方式
	var $order = $("#order");// 排序字段
	
	// 全选
	$allCheck.click( function() {
		if ($(this).attr("checked") == true) {
			$idsCheck.attr("checked", true);
			$deleteButton.attr("disabled", false);
			$recoverButton.attr("disabled", false);
		} else {
			$idsCheck.attr("checked", false);
			$deleteButton.attr("disabled", true);
			$recoverButton.attr("disabled", true);
		}
	});
	
	// 无复选框被选中时,删除按钮不可用
	$idsCheck.click( function() {
		var $idsChecked = $(".list input[name='ids']:checked");
		if ($idsChecked.size() > 0) {
			$deleteButton.attr("disabled", false);
			$recoverButton.attr("disabled", false);
		} else {
			$deleteButton.attr("disabled", true);
			$recoverButton.attr("disabled", true);
		}
	});
	
	// 批量删除
	$deleteButton.click( function() {
		var url = $(this).attr("url");
		var $idsCheckedCheck = $(".list input[name='ids']:checked");
		if (confirm("您确定要删除吗？") == true) {
			$.ajax({
				url: url,
				data: $idsCheckedCheck.serialize(),
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$deleteButton.attr("disabled", true)
				},
				success: function(data) {
					$deleteButton.attr("disabled", false)
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().remove();
						$deleteButton.attr("disabled", true)
					}
					$.dialog({type: data.status, title:"请注意", content: data.message, ok: "确定",okCallback: freshURL, modal: true});
				},
				error:function(data) {
					$deleteButton.attr("disabled", false)
					$.dialog({type: "error", title:"请注意", content: "删除失败", ok: "确定",okCallback: freshURL, modal: true});
				}
			});
		}
	});
	
	// 批量恢复
	$recoverButton.click( function() {
		var url = $(this).attr("url");
		var $idsCheckedCheck = $(".list input[name='ids']:checked");
		if (confirm("您确定要恢复吗？") == true) {
			$.ajax({
				url: url,
				data: $idsCheckedCheck.serialize(),
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$deleteButton.attr("disabled", true)
				},
				success: function(data) {
					$deleteButton.attr("disabled", false)
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().remove();
						$deleteButton.attr("disabled", true)
					}
					$.dialog({type: data.status, title:"请注意", content: data.message, ok: "确定",okCallback: freshURL, modal: true});
				},
				error:function(data) {
					$deleteButton.attr("disabled", false)
					$.dialog({type: "error", title:"请注意", content: "操作失败", ok: "确定",okCallback: freshURL, modal: true});
				}
			});
		}
	});
	
	function freshURL(){
		//location.replace(location.href);
		//location.reload();
	}	

	// 查找
	$searchButton.click( function() {
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 每页显示数
	$pageSize.change( function() {
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 排序
	$sort.click( function() {
		var $currentOrderBy = $(this).attr("name");
		if ($orderBy.val() == $currentOrderBy) {
			if ($order.val() == "") {
				$order.val("asc")
			} else if ($order.val() == "desc") {
				$order.val("asc");
			} else if ($order.val() == "asc") {
				$order.val("desc");
			}
		} else {
			$orderBy.val($currentOrderBy);
			$order.val("asc");
		}
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 排序图标效果
	sortStyle();
	function sortStyle() {
		var orderByValue = $orderBy.val();
		var orderValue = $order.val();
		if (orderByValue != "" && orderValue != "") {
			$(".sort[name='" + orderByValue + "']").after('<span class="' + orderValue + 'Sort">&nbsp;</span>');
		}
	}
	
	// 页码跳转
	$.gotoPage = function(id) {

		$pageNumber.val(id);
		$listForm.submit();
	}
	
});