<%@ page contentType="text/html; charset=UTF-8"%>
<div class="footer" style="width:100%">Copyright © 2014 - 2015  , All rights reserved  eway 版权所有</div>
<script type="text/javascript">
//鼠标移上改变背景
$(document).ready(function(){
  $(".table_ul li").mouseover(function(){
	$(".table_ul li").removeClass("move_color");
	$(this).addClass("move_color");
  });
});



</script>