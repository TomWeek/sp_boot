//存放左侧菜单数据
var leftMenuMap = new Map();
//左中右框架内容高度
function pageInit(ctx) {
	//生成左侧菜单map
	//顶部菜单默认第一个为选中状态
	sidemenu(null,0,ctx);
	//皮肤默认在blue目录 下
	var cookie =getCookie("color");
	if(cookie==null)
	{
    	setCookie("color","blue");
    }
	cookie = top.getCookie("color");
  	styleChange(cookie,ctx);
  	ctrlframeht();
}

function ctrlframeht(){
   	var wd_ht=top.document.documentElement.clientHeight;//窗口的高度
	var wd_wt=top.document.documentElement.clientWidth;//窗口的宽度
	var ht_top=$("#topinfor",top.document).height();//顶部的高度
	var wt_lf=$(".leftmenu",top.document).width();//内容左侧宽度
	var wt_holder=$(".kj-holder",top.document).width();//控制区域宽度
	var wt_rt=wd_wt-wt_lf-wt_holder;//内容右侧宽度
    var cont_ht=wd_ht-ht_top;//内容区域高度
    var topshow=$("#topinfor",top.document).css("display");//判断顶部是否隐藏或展示
    var ltshow=$(".leftmenu",top.document).css("display");//判断顶部是否隐藏或展示
    if(topshow=="none")
    	cont_ht=wd_ht;//顶部隐藏的时候 内容区域高度
    if(ltshow=="none")
    	wt_rt=wd_wt-wt_holder;//内容右侧宽度
    var rtcont_ht=cont_ht-$(".bread-nav-box",top.document).height();//右侧内容区域高度
    $("#leftmenu_scroll",top.document).css("height",(cont_ht-22) + "px");
    $("#lfmenu_shadow",top.document).css("height",cont_ht + "px");
    $("#mainFrame",top.document).css("height",rtcont_ht + "px");
	$(".kj-rt",top.document).css("width",wt_rt+"px");
	$(".kj-rt,.kj-holder",top.document).css("height",cont_ht+"px");//设置内容区域高度
}

//增加或删除标签
var lastLink ="";//保存最后一个标签页面的链接
function openNew(id, name,src) {
	var breadNav = document.getElementById("bread-nav");
	var tagMenus = breadNav.getElementsByTagName("a");
	//如果已经打开了就不再打开
	for(var i=0;i<tagMenus.length;i++){
		var pid = "p"+id;
		if(tagMenus[i].id==pid){
			return;
		}
	}
	if(tagMenus.length>=6){//最多允许打开6个标签,否则替换最后一个标签
		var tagMenu = tagMenus[tagMenus.length-1];
		breadNav.removeChild(tagMenu); //移除最后一个标签
	}
	var tagMenu = document.createElement("a");
    tagMenu.id = "p" + id;
    tagMenu.name =src;
    tagMenu.innerHTML = name +" " + "<span class='del-bread'>×</span>";
    lastLink=src;
    //标签点击事件
    tagMenu.onclick = function(evt) {
        clearStyle();
        tagMenu.className = "current";
        var ifrobj=$(document.getElementById('mainFrame').contentWindow.document.body);
		$("iframe",ifrobj).css("height","0");
		$("#i"+id,ifrobj).css("height","100%");
    };
   
    //标签内关闭图片点击事件
    tagMenu.getElementsByTagName("span")[0].onclick = function(evt) {
        evt = (evt) ? evt: ((window.event) ? window.event: null);
        if (evt.stopPropagation) {
            evt.stopPropagation();
        } //取消opera和Safari冒泡行为;
        var ifrobj=$(document.getElementById('mainFrame').contentWindow.document.body);
        var delthis=$(this).parent().attr("id");
        thisifrid=delthis.replace(/p/, "");
        this.parentNode.parentNode.removeChild(tagMenu); //删除当前标签
		$("#i"+thisifrid,ifrobj).remove();
        var color = tagMenu.className;
        //设置如果关闭一个标签时，让最后一个标签得到焦点
        if (color == "current") { //区别浏览器对颜色解释
            if (tagMenus.length - 1 >= 0) {
                clearStyle();
                tagMenus[tagMenus.length - 1].className = "current";
                var ifrid=tagMenus[tagMenus.length - 1].id
                ifrid=ifrid.replace(/p/, "");
				$("iframe",ifrobj).css("height","0");
				$("#i"+ifrid,ifrobj).css("height","100%");
            }
            else{
				$("iframe",ifrobj).css("height","0");
				$("#i0",ifrobj).css("height","100%");
            }
        }
    };
    breadNav.appendChild(tagMenu);
}
//清除标签样式
function clearStyle() {
	var menu = document.getElementById("bread-nav"); //顶部菜单容器
    var tags = menu.getElementsByTagName("a"); //顶部菜单
    for (var i = 0; i < tags.length; i++) {
        menu.getElementsByTagName("a")[i].className = "";
        menu.getElementsByTagName("a")[i].style.zIndex=100-i;
        menu.getElementsByTagName("a")[i].href="javascript:;";
    }
}
//获取cookie
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) return unescape(arr[2]);
    else return null;
}
//设置cookie
function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function returnIframeSkins(ctx,doc){
	var cssPath=ctx+"/skins/";
	//主窗口的所有子级iframe添加link样式文件 
    if (top.getCookie("color") != null) {
        csshref=cssPath + top.getCookie("color")+"/css/colorskin.css";
    } else {
        csshref=cssPath  + "blue/css/colorskin.css";
    }
    if(doc.getElementsByTagName("iframe")!=undefined)
    {
    	var Frames = doc.getElementsByTagName("iframe");
    	for(var i=0;i<Frames.length;i++){
    		
	//	if($(window.frames[i].frames[0]).parent().hasClass("edui-default")) return false;
    		var rt_window =Frames[i].contentWindow.document;
			if(rt_window.getElementById("cssLink")!=undefined){
				$("#cssLink",rt_window).attr("href",csshref);
			}
    		else{
	    		var link=rt_window.createElement("link");
			    link.setAttribute("rel", "stylesheet");
			    link.setAttribute("type", "text/css");
			    link.setAttribute("href", csshref);
			    link.setAttribute("id", "cssLink");
			    var heads = rt_window.getElementsByTagName("head")[0];
			    if(heads!=undefined){
			    	heads.appendChild(link);
			    }
    		}
    		if(rt_window.getElementsByTagName("iframe")!=undefined){
    			var ifrnum=rt_window.getElementsByTagName("iframe").length;
		    	for(var j=0;j<ifrnum;j++){
		    		if(rt_window.getElementsByTagName("iframe")[j].parentElement.className=="edui-default") return false;
    				returnIframeSkins(ctx,rt_window);
		    	}
		    }
    	}			    
    }
}
var styleID = 0;
// 点击左上角皮肤切换按钮  皮肤切换效果
function styleChange(n,ctx) {
	var cssPath=ctx+"/skins/";
	var thisobj=document.getElementById("skin_"+n);
	if(thisobj.className=="current") return;
	document.getElementById("skin_orange").className="";
	document.getElementById("skin_blue").className="";
	document.getElementById("skin_green").className="";
	document.getElementById("skin_gray").className="";
	document.getElementById("skin_red").className="";
	thisobj.className="current";
    var cssLink = document.getElementById("cssLink"); //当前主窗口id为cssLink的对象
    //改变 当前主窗口与 右侧页面框架 的样式文件名称
    cssLink.href = cssPath + n + "/css/colorskin.css";
    setCookie("color",n);
    if (top.getCookie("color") != null) {
        cssLink.href=cssPath + top.getCookie("color")+"/css/colorskin.css";
    } else {
        cssLink.href= cssPath + n + "/css/colorskin.css";
    }
		returnIframeSkins(ctx,document)
}
//顶部菜单控制左侧菜单的显示隐藏
function sidemenu(thisobj,parentCode,ctx){
		if( thisobj!=null &&thisobj.className=="current") return
		//第一次没点击顶部菜单thisobj为null
		var menu = document.getElementById("navigation"); //顶部菜单容器
	    var tags = menu.getElementsByTagName("a"); //顶部菜单
	    for(var i=0;i<tags.length;i++){
	    	tags[i].className="";
	    }
		if(thisobj==null){
		    tags[0].className="current";
		}else{
			thisobj.className="current";
		}
}
//添加iframe
function addiframe(src,code){
	var ifrobj=$(document.getElementById('mainFrame').contentWindow.document.body);
	$("iframe",ifrobj).css("height","0");
	var ifr="";
	ifr+="<iframe frameborder='0' hspace='0' id=i"+code+" marginheight='0' marginwidth='0' src="+src+" vspace='0' width='100%' height='100%' scrolling='auto' onload=returnIframeSkins('../../',document)>";
	$(document.getElementById('mainFrame').contentWindow.document.body).append(ifr);
}
/**
 * 短消息和帮助中心打开标签页面
 * @param src
 * @param name
 */
function addTab(src,name,code){
	// 不能与其他的标签id冲突
    openNew(code, name,src); //设置标签显示文字
    clearStyle();
    document.getElementById("p" + code).className = "current";
	var ifrid=$(document.getElementById('mainFrame').contentWindow.document.body);
	if($("#i"+code,ifrid).length>0){
	    var ifrobj=$(document.getElementById('mainFrame').contentWindow.document.body);
		$("iframe",ifrobj).css("height","0");
		$("#i"+code,ifrobj).css("height","100%");
		$("#i"+code,ifrobj).attr("src",src);
		return false;

	}
	addiframe(src,code);
}
//控制左侧菜单部分的显示隐藏
    function ctrl_left(){
		var ctrlframe =top.document.getElementById("leftCtruBarFrame").contentWindow; //获取ID=leftCtruBarFrame的iframe的window对象 
		var holdertit=$("#ctrl_left3",top.document).attr("title");
		$("#ctrl_left3",top.document).attr("title",(holdertit == '隐藏左侧' ? '展开左侧' : '隐藏左侧'));
		$("#ctrl_left3",top.document).toggleClass("ctrl_lt_current");
		$("#leftmenu",top.document).toggle();
		ctrlframe.$("#ctrl_left2").toggle();
		ctrlframeht();
    }
//控制顶部菜单部分的显示隐藏
function ctrl_top(){
	var holdertit=$("#ctrl_top").attr("title");
	$("#ctrl_top").attr("title",(holdertit == '隐藏头部' ? '展开头部' : '隐藏头部'));
	$("#topinfor").toggle();
	$("#ctrl_top").toggleClass("ctrl_top_current");
	ctrlframeht();
}