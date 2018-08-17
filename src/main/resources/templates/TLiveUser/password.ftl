<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#assign ctx=request.contextPath />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/skins/blue/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/skins/blue/css/colorskin.css" />
    <script type="text/javascript" src="${ctx}/skins/blue/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/skins/blue/js/form.js"></script>
    <script type="text/javascript">
		function savePass(){
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			var rePwd = $("#rePwd").val();
			if(oldPwd==null||oldPwd==''){
				alert("请输入原密码");
				return;
			}
			if(newPwd==null||newPwd==''){
				alert("请输入新密码");
				return;
			}
			if(rePwd==null||rePwd==''){
				alert("请输入确认新密码");
				return;
			}
			if(newPwd!=rePwd){
				alert("两次新密码输入不一致");
				return;
			}
            $.ajax({
            	type:'post',
                async: true,
                url:"/modifypass",
                data:$("#frm").serialize(),
                success:function(result){
					var json = JSON.parse(result);
					var code = json.code;
					var msg = json.msg;
					$("#msg").html(msg);
				}
			});
		}
    </script>
</head>
<body class="wrapr20">
<div class="breadcrumb">当前位置：
    <span>用户管理</span><i>|</i>
    <span>修改密码</span>
</div>
<form name="frm" id="frm">
	<div class="layoutop paddt0"><br>
		<div align="center">原密码：<input name="oldPwd" id="oldPwd" type="password"></div></br>
		<div align="center">新密码：<input name="newPwd" id="newPwd" type="password"></div></br>
		<div align="center">确认新密码：<input name="rePwd" id="rePwd" type="password"></div></br>
		<div align="center"><span><font id="msg" style="color:red;font-size:16px;width:200px;"></font></span></div></br>
		<div align="center">
			<a href="javascript:savePass();" class="btn-fm fmsubmit marginr10" align="center"><b>提交</b></a>
		</div>
	</div>
</form>
</body>
</html>