<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link rel="stylesheet" type="text/css" href="./skins/blue/css/lgi.css" />
    <link rel="stylesheet" type="text/css" href="./skins/blue/css/colorbox.css" />
    <script src="./skins/blue/js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="./skins/blue/js/jquery.placeholder.min.js" type="text/javascript"></script>
    <script src="./skins/blue/js/form-field-tooltip.js" type="text/javascript"></script>
    <script src="./skins/blue/js/rounded-corners.js" type="text/javascript"></script>
    <script src="./skins/blue/js/jquery.colorbox.js" type="text/javascript"></script>
</head>
<body onkeydown="keyDown(event);">
<div class="header"></div>
<div class="lgi-info">
    <h2 class="lgi-tit">石榴财经直播系统管理平台</h2>
    <form class="lgi-input" id="frm" name="frm">
        <div class="lgi-item">
            <label class="fm-label">用户名</label>
            <div class="relative">
                <input type="text" id="username" name="username" class="text" placeholder="请输入用户名......" tooltipText="*此处为必填项">
            </div>
        </div>
        <div class="lgi-item">
            <label class="fm-label">密　码</label>
            <div class="relative">
                <input type="password" id="password" name="password" class="text" placeholder="请输入密码......" tooltipText="*此处为必填项">
            </div>
        </div>
        <div class="lgi-btn clearfix">
            <!--<span class="fr"><input type="checkbox" class="vmiddle marginr7">记住我</span>-->
            <a class="btn see-info" id="submitLogin" onclick="javascript:submitLogin();">用户登录</a>
            <a href="" class="color-green" id="msg" style="color:red;font-size:16px;width:200px;"></a>
            <!--<a href="" class="color-green">忘记密码？</a>-->
        </div>
    </form>
</div>
<#--<div class="foot">版权所有：LEAP UI 后台</div>-->
<script>
	function keyDown(e){
		var keynum;
		if(window.event){//IE
			keynum = e.keyCode;
		}else if(e.which){//Netscape/Firefox/Opera
	  		keynum = e.which;
		}
		if (keynum==13){//回车
			e.returnValue=false;
        	e.cancel = true;
			$("#submitLogin").click();
		}
	}
	
	function submitLogin(){
		var username = $("#username").val();
		var password = $("#password").val();
		if(username==null || username == ''){
			alert("请输入用户名");
			return;
		}
		if(password==null || password == ''){
			alert("请输入密码");
			return;
		}
		$.ajax({
			type:'post',
			async: true,
			url:"/login",
            data:$("#frm").serialize(),
			success:function(result){
				var json = JSON.parse(result);
				var code = json.code;
				if(code=='1'){
					window.location.href = '/index';
				}else{
					var msg = json.msg;
					$("#msg").html(msg);
					//window.location.href = '/';
				}
			}
		});
	}
</script>
</body>
</html>