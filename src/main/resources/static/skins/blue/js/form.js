//模拟下拉菜单
function stgroup(clicka,ctrlul){
	var isFrist = true;  
	$(clicka).siblings("input").click(function(){
		if($(this).siblings("a").attr("disabled")=="disabled") return;
		$(this).blur();
		$(this).siblings(ctrlul).toggleClass("select-hold");
		$(this).siblings(ctrlul).toggle();
		/*第一次点击时 设置下拉菜单ctrlul的宽度 最小保持与clicka的父级一直 超出按照ctrlul下a标签的最大值*/
		if (isFrist) {
			var max=$(clicka).parent().width()-2; 
			var len = $(ctrlul+" li").length; 
			for (var i = 1; i < len; i++){ 
				if ($(ctrlul+" li:eq("+i+")").width()> max){ 
					max = $(ctrlul+" li:eq("+i+")").width(); 
				} 
			}
			if(len>10) $(ctrlul).width(max+36);
			else $(ctrlul).width(max);
			isFrist = false;
		} 
		
		return false;//防止冒泡
	});
	/*$(ctrlul+" li").click(function(){
		$(this).parent().siblings("input").focus();
		var pagenum=$(this).children().html();
		var div=$(this).parent().parent();
		//取li的name放到input隐藏域中以传值
		div.find("input").val($(this).attr("name"));
		$(this).parent().siblings(clicka).html(pagenum+"<b>&nbsp;</b>");
		$(this).siblings(ctrlul).removeClass("select-hold");
		$(this).siblings(ctrlul).hide();
		if($(this).hasClass("st-group-lt-ft")) $(this).parent().siblings("input").val("");
		else $(this).parent().siblings("input").val(pagenum);
		
		$(this).parent().siblings("input").blur();
	});*/
}
$(document).ready(function(){
	// 点击页面任意位置 隐藏.st-group-lt
	$(document).click(function (e) {
		if (e.target.id != "show") {
			$(".st-group-lt").removeClass("select-hold");
			$(".st-group-lt").hide();
			$(".page-st-lt").removeClass("select-hold");
			$(".page-st-lt").hide();
		} 
	});
	/*stgroup(".st-group-toggle",".st-group-lt")//下拉菜单
	stgroup(".page-st-toggle",".page-st-lt")//控制翻页内模拟下拉菜单*/
/*	//文本框与文本域 获取焦点 
	$("input[type=text],textarea").focus(function(){
		$(this).addClass("textfocus");
	});
	//文本框与文本域 失去焦点
	$("input[type=text],textarea").blur(function(){
		$(this).removeClass("textfocus");
	});
	*/
	//form 列表页面
	$(".tab-hover tr:odd").addClass("td-gray");
	$(".tab-hover tr:even").addClass("td-white");
	
});
