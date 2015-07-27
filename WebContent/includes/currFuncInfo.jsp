<%@ page contentType="text/html; charset=UTF-8"%>

<div class="queryBar" >
			<input type="hidden" name="funcid" id="funcid" value="${func.id}" />
			<span>
				<label class="queryTitle">名称:</label>
				<input type="text" name="funcname" class="inputText" value="${func.funcname}" />
			</span>
			<span>
				<label class="queryTitle">编号:</label>
				<input type="text" name="code" class="inputText" value="${func.code}" />
			</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span>
				<!-- <input type="button" id="searchButton2" class="searchButton queryButton" value="修改" onClick="refreshTree()"> -->
			</span>
			
		</div>
		<div class="queryBar">
			<span>
				<label class="queryTitle">功能全称:</label>
				<input type="text" name="funcwholename" class="inputText" style="width:480px" value="${func.funcwholename}" > 
			</span>
			 
		</div>
		<div class="queryBar">
			<span>
				<label class="queryTitle">访问链接:</label>
				<input type="text" name="url" class="inputText" style="width:480px" value="${func.url}" > 
			</span>
			 
		</div>
		<div class="queryBar">
			<span>
				<label class="queryTitle">类型:</label>
				<select id="type" name="type">
					<option value="1" ${func.type=="1"?'selected':'' }>访问控制</option>
					<option value="2" ${func.type=="2"?'selected':'' }>可用性</option>
					<option value="3" ${func.type=="3"?'selected':'' }>访问控制+可用性</option>
				</select>
			</span> 
		</div>
		
		