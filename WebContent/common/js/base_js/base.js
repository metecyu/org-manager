

if(!window.XMLHttpRequest) {

	// 解决IE6透明PNG图片BUG
	DD_belatedPNG.fix(".png");
	
	// 解决IE6不缓存背景图片问题
	document.execCommand("BackgroundImageCache", false, true);

}


// 添加收藏夹
function addFavorite(url, title) {
	if (document.all) {
		window.external.addFavorite(url, title);
	} else if (window.sidebar) {
		window.sidebar.addPanel(title, url, "");
	}
}

// html字符串转义
function htmlEscape(htmlString) {
	htmlString = htmlString.replace(/&/g, '&amp;');
	htmlString = htmlString.replace(/</g, '&lt;');
	htmlString = htmlString.replace(/>/g, '&gt;');
	htmlString = htmlString.replace(/'/g, '&acute;');
	htmlString = htmlString.replace(/"/g, '&quot;');
	htmlString = htmlString.replace(/\|/g, '&brvbar;');
	return htmlString;
}

// 设置Cookie
function setCookie(name, value) {
	var expires = (arguments.length > 2) ? arguments[2] : null;
	document.cookie = name + "=" + encodeURIComponent(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ";path=/";
}


// 获取Cookie
function getCookie(name) {
	var value = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (value != null) {
		return decodeURIComponent(value[2]);
    } else {
		return null;
	}
}

// 删除cookie
function deleteCookie(name) {
	var expires = new Date();
	expires.setTime(expires.getTime() - 1000 * 60);
	setCookie(name, "", expires);
}

// 浮点数加法运算
function floatAdd(arg1, arg2) {
	var r1, r2, m;
	try{
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

// 浮点数减法运算
function floatSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 浮点数乘法运算
function floatMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch(e) {}
	try {
		m += s2.split(".")[1].length;
	} catch(e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// 浮点数除法运算
function floatDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;    
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch(e) {}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch(e) {}
	with(Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

// 设置数值精度
function setScale(value, scale, roundingMode) {
	if (roundingMode.toLowerCase() == "roundhalfup") {
		return (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else if (roundingMode.toLowerCase() == "roundup") {
		return (Math.ceil(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else {
		return (Math.floor(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	}
}

//由毫秒数得到x天数x小时x分钟
function getTimeDHM(ms){
	if(isNaN(ms)){
		return {"day":0,"hour":0,"min":0};
	}	
	var day=Math.floor(ms/(24*60*60*1000));
	var hour=Math.floor((ms/(60*60*1000)-day*24));
	var min=Math.floor(((ms/(60*1000))-day*24*60-hour*60));
	return {"day":day,"hour":hour,"min":min};
}
//由毫秒数得到x小时
function getTimeH(ms){
	if(isNaN(ms)){
		return {"hour":0};
	}	
	var hour=Math.round((ms/(60*60*10)))/100;
	return {"hour":hour};
}

//由x天数x小时x分钟得到毫秒数
function getTimeMs(day,hour,min){
	return Math.round(day*24*60*60*1000+hour*60*60*1000+min*60*1000);
}	

//判断非负整数
function isIntNotNeg(num){
	var re=/^\d+$/;
	if (re.test(num)){
		return true;
	}else{
		return false;
	}	
}

//修改元素的html为指定时间值,t为毫秒
function setDisplayTimeHTML(obj,t){
	if(t==''){
		obj.html('');
		return;
	}	
	if(t>0){
		var objTime=getTimeDHM(t);
		var s="";
		if(objTime.day!=0){
			s=s+objTime.day+"天  ";
		}
		if(objTime.hour!=0){
			s=s+objTime.hour+"小时 ";
		}
		if(objTime.min!=0){
			s=s+objTime.min+"分钟";
		}
		obj.html(s);
	}else{
		obj.html("");
	}
}

//返回处理过的全称	autocomplete中使用
function getFullName(name){
	return name=="" ? "" : " ("+name+")"
}

var keItems=[
        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', '|', 'fullscreen', '/',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
        'flash', 'media', 'table', 'hr', 'emoticons', 'pagebreak',
        'link', 'unlink', 'selectall', '|', 'about'
]

function getrandom() {
	return Math.random()*100;
}
