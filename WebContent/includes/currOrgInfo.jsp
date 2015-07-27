<%@ page contentType="text/html; charset=UTF-8"%>
<div class="queryBar" >
			<span>
				<label class="queryTitle">名称:</label>
				<input type="text" name="search.strKeyword[0]" class="inputText" value="${org.shortname}" readonly="readonly" style="height: 25px; ">
			</span>
			<span>
				<label class="queryTitle">全称:</label>
				<input type="text" name="search.strKeyword[1]" class="inputText" value="${org.orgname}" readonly="readonly" style="height: 25px; width: 214px">
			</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span>
				<!-- <input type="button" id="searchButton2" class="searchButton queryButton" value="修改" onClick="refreshTree()"> -->
			</span>
			
		</div>
		<div class="queryBar">
			<span>
				<label class="queryTitle">编码:</label>
				<input type="text" name="search.strKeyword[1]" class="inputText"  value="${org.code}" readonly="readonly" style="height: 25px; ">
			</span> 
			<span>
				<label class="queryTitle">组织全称:</label>
				<input type="text" name="search.strKeyword[1]" class="inputText" style="width: 239px; height: 25px" value="${org.orgwholename}" readonly="readonly"> 
			</span>
			 
		</div>
		