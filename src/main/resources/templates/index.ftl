<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>石榴财经</title>
    <style>
        body,html{height:100%;overflow:hidden;}
    </style>
    <link rel="stylesheet" type="text/css" href="./skins/blue/css/master.css">
    <link rel="stylesheet" type="text/css" href="./skins/blue/css/colorskin.css" id="cssLink">
    <script type="text/javascript" src="./skins/blue/js/Map.js"></script>
    <script type="text/javascript" src="./skins/blue/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="./skins/blue/js/master.js"></script>
    <script type="text/javascript" src="./skins/blue/js/jquery.tree.js"></script>
    <link rel="stylesheet" type="text/css" href="./skins/blue/css/colorbox.css" />
    <script type="text/javascript" src="./skins/blue/js/jquery.colorbox.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#leftmenutree').tree({
                expanded: 'li:first'
            });
            pageInit('../../');
            $(window).resize(ctrlframeht);
        });

        function createLive(){
            $.ajax({
                type: 'post',
                url: '/v1/create?id=1&date=' + new Date().getTime(),
                success: function (msg) {
                    $("#main").html(msg);
                    alert("认证成功!");

                }
            })
        }
    </script>
    <script type="text/javascript">
    	function logout(){
        	window.location.href = "${request.contextPath}"+"/logout";
        }
    </script>
    <!--[if IE 6]><script>function correctPNG() {for(var i=0; i<document.images.length; i++){var img = document.images[i];var imgName = img.src.toUpperCase();if (imgName.substring(imgName.length-3, imgName.length) == "PNG"){var imgID = (img.id) ? "id='" + img.id + "' " : "";var imgClass = (img.className) ? "class='" + img.className + "' " : "";var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' ";var imgStyle = "display:inline-block;" + img.style.cssText;if (img.align == "left") imgStyle = "float:left;" + imgStyle;if (img.align == "right") imgStyle = "float:right;" + imgStyle;if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle;var strNewHTML = "<span "+ imgID + imgClass + imgTitle + "style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";" + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" + "(src='" + img.src + "', sizingMethod='scale');\"></span>";img.outerHTML = strNewHTML;i = i-1;}}}window.attachEvent("onload", correctPNG);</script><![endif]-->
</head>
<body>
<table width="100%" id="topinfor" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="middle" class="logo"><#--<img src="../../skins/blue/images/logo.png"/>--></td>
        <td>
            <h2 class="logo-tit">石榴财经直播后台</h2>
            <div class="navigation clearfix" id="navigation">
                <a href="javascript:;" class="current"></a>
                <a href="javascript:;" class=""></a>
            </div>
            <!--皮肤切换 btn-->
            <div class="loginInfo">您好，超级管理员<span class="loginInfo_line">&nbsp;</span>
                <!--<a href="javascript:addTab('right.html','短消息','message')">短消息(1)</a>
                <a href="javascript:addTab('show.html','帮助中心','help')">帮助中心</a>-->
                <a href="javascript:addTab('/password','修改密码','modifypass')">修改密码</a>
                <a href="javascript:logout();" title="安全退出" class="topexit">&nbsp;</a></div>
            <div id="skin-select">
                <a href="javascript:;" onclick="styleChange('blue','../../')" id="skin_blue" class="current">点击切换皮肤</a>
                <a href="javascript:;" onclick="styleChange('green','../../')" id="skin_green">点击切换皮肤</a>
                <a href="javascript:;" onclick="styleChange('gray','../../')" id="skin_gray">点击切换皮肤</a>
                <a href="javascript:;" onclick="styleChange('orange','../../')" id="skin_orange">点击切换皮肤</a>
                <a href="javascript:;" onclick="styleChange('red','../../')" id="skin_red">点击切换皮肤</a>
            </div>
        </td>
    </tr>
</table>
<div id="leftmenu" class="leftmenu relative">
    <div id="leftmenu_scroll">
        <div id="lfmenu_0" style="display:block;">
            <ul id="leftmenutree" role="tree" class="tree">
                <li role="treeitem" aria-expanded="true">
                    <a href="javascript:;" tabindex="-1" class="tree-one tree-one-active tree-parent" name="tree-1">账务管理</a>
                    <ul role="group">
                        <li role="treeitem" aria-expanded="true">
                            <a href="###" target="mainFrame" tabindex="-1" class="tree-parent tree-two-2" name="tree-2" onclick="addTab('/v1/create?id=1&date=','直播配置管理','5')">直播配置管理</a>
                        </li>
                        <li role="treeitem" aria-expanded="true">
                            <a href="###" target="mainFrame" tabindex="-1" class="tree-parent tree-two-2" name="tree-2" onclick="addTab('/v1/adminList','直播列表管理','5')">直播列表管理</a>
                        </li>
                        <li role="treeitem" aria-expanded="true">
                            <a href="###" target="mainFrame" tabindex="-1" class="tree-parent tree-two-2" name="tree-2" onclick="addTab('/imMessage/sendPage','主讲消息管理','5')">主讲消息管理</a>
                        </li>


                    </ul>
                </li>

            </ul>
        </div>
    </div>
    <div id="lfmenu_shadow">&nbsp;</div>
    <input type="button" value="" id="ctrl_left" onclick="ctrl_left()" class="left_ctrl_bar_hide" >
</div>
<div class="kj-holder">

    <iframe frameborder="0" height="100%" hspace="0" id="leftCtruBarFrame" name="leftCtruBarFrame" marginheight="0" marginwidth="0" scrolling="no" src="./holder-kj.html" vspace="0" width="100%">
    </iframe>
</div>
<div class="kj-rt">
    <div class="bread-nav-box">
        <div id="bread-nav" class="bread-nav clearfix"></div>
        <div class="ctrl_lt_top clearfix">
            <a href="javascript:;" onclick="ctrl_left();" id="ctrl_left3" class="ctrl_lt" title="隐藏左侧"></a><a href="javascript:;" onclick="ctrl_top();" id="ctrl_top" class="ctrl_top" title="隐藏头部"></a>
        </div>
    </div>
    <iframe frameborder="0" hspace="0" id="mainFrame" name="mainFrame" marginheight="0" marginwidth="0" src="./workarea.html" vspace="0" width="100%" scrolling="auto"  onload="returnIframeSkins('../../',document)">
    </iframe>
</div>
<!--弹出层内容 开始-->
<div style="display:none;">
    <!--删除多个 开始-->
    <div id="popup-delmore" class="paddtb70">
        <div class="text-ct size14 popwd">sfa</div>
        <div class="text-ct margint15">
            <a href="javascript:;" class="marginr10 popbtn">确 认</a>
            <a href="javascript:;"  class="popbtn-qx">取 消</a>
        </div>
    </div>
    <!--删除多个 结束-->
    <!--删除多个 开始-->
    <div id="popup-delone" class="paddtb70">
        <div class="text-ct size14 popwd">sfa</div>
        <div class="text-ct margint15">
            <a href="javascript:;" class="marginr10 popbtn">确 认</a>
            <a href="javascript:;"  class="popbtn-qx">取 消</a>
        </div>
    </div>
    <!--删除多个 结束-->
</div>
<!--弹出层内容 结束-->
</body>
</html>