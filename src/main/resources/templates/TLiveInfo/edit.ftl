<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#assign ctx=request.contextPath />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/skins/blue/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/skins/blue/css/colorskin.css" />
    <script type="text/javascript" src="${ctx}/skins/blue/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
    <script type="text/javascript" src="${ctx}/lhgdialog/lhgdialog.min.js?skin=idialog" ></script>
    <script type="text/javascript" src="${ctx}/skins/blue/js/form.js"></script>
    <#--<script src="${ctx}/widgets/ueditor/editor_config.js" type="text/javascript"></script>
    <script src="${ctx}/widgets/ueditor/editor_all.js" type="text/javascript"></script>-->
        <!--jquery-validate -->
    <script src="${ctx}/widgets/jquery-validate/jquery.validate.js" type="text/javascript"></script>
    <script src="${ctx}/widgets/jquery-validate/additional-methods.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>



</head>
<body class="wrapr20">
<div class="breadcrumb">当前位置：
    <span>直播管理</span><i>|</i>
    <span>直播修改</span>
</div>
<div class="layoutop">
    <a href="/v1/adminList" class="btngp btn-return"><i>&nbsp;</i><b>返回</b></a>
</div>
<div class="layoutop tdbd-bot-2">
    <div class="tabtit relative"><h3>基本信息</h3></div>
    <form class="cmxform" id="myform" name="myform" method="post" action="/v1/save">
        <input type="hidden" name="id" value="${(liveInfo.id)!}"/>
        <table width="100%" cellspacing="0" cellpadding="0" border="0" class="tab-bd">
            <tr>
                <td class="text-rt td-gray-2 tdbd-bot" width="20%">直播名称</td>
                <td class="tdbd-bot-2">
                    <input name="title" id="title" type="text" class="text" value="${(liveInfo.title)!}">
                </td>
            </tr>
            <tr>
                <td class="text-rt td-gray-2 tdbd-bot-2">直播简介</td>
                <td class="tdbd-bot-2"><textarea cols="100" rows="4" class="w400" name="detail">${(liveInfo.detail)!}</textarea></td>
            </tr>
            <tr>
                <td class="text-rt td-gray-2 tdbd-bot">开始时间</td>
                <td class="tdbd-bot-2">
                    <span class="qy-cond">
                        <#if liveInfo.status==2>
                             <input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${liveInfo.startTime?string("yyyy-MM-dd HH:mm:ss")}'})" id="startTime" name="startTime" value="<#if liveInfo.startTime?? >${liveInfo.startTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="text input_date">
                        </#if>

                        <#if liveInfo.status!=2>
                            <#if liveInfo.startTime?? >${liveInfo.startTime?string("yyyy-MM-dd HH:mm:ss")}</#if>
                        </#if>


                    </span>
                </td>
            </tr>
            <tr>
                <td class="text-rt td-gray-2 tdbd-bot" width="20%">注水系数</td>
                <td class="tdbd-bot-2">
                    <input name="zsxs" type="text" class="text" value="${(liveInfo.onlineNumZsxs)!}">
                </td>
           </tr>
            <tr>
                <td class="text-rt td-gray-2 tdbd-bot" width="20%">直播流地址</td>
                <td class="tdbd-bot-2">
                    <input name="obsUrl" type="text" class="text"  value="${(liveInfo.obsId)!}">
                </td>
            </tr>


            <tr>
                <td class="text-rt td-gray-2 tdbd-bot">直播预览图(竖屏)</td>
                <td class="tdbd-bot-2">
							<span class="browsearea">
								<div style="height:200px;width:100px;margin-bottom:10px;" id="1111" >
                                    <img id="vimg" src="${(liveInfo.imgUrlV)!}"  style="height:200px;width:136px;" />

                                </div>
                                <input type="hidden" value="${(liveInfo.imgUrlV)!}"  name="imgV" id="img_1111"/>
								<a href="javascript:uploadFile('1111');" class="btngp btn-browse"><i>&nbsp;</i><b>浏览</b></a>
								<#--<input name="afile" onchange="$(this).siblings(':first').val(this.value)" type="file" size="52" class="file-btn w384" hidefocus="true">-->
							     <label style="color:red;">(建议图片大小1M以内)</label>
                            </span>
                </td>
            </tr>
            <tr>
                <td class="text-rt td-gray-2 tdbd-bot">直播预览图(横屏)</td>
                <td class="tdbd-bot-2">
							<span class="browsearea">
								<div id="2222"  style="height:110px;width:196px;margin-bottom:10px;">
                                    <img id="himg" src="${(liveInfo.imgUrlH)!}"  style="height:110px;width:196px;" />

                                </div>
                                <input type="hidden" value="${(liveInfo.imgUrlH)!}"  name="imgH" id="img_2222"/>
								<a href="javascript:uploadFile('2222');" class="btngp btn-browse"><i>&nbsp;</i><b>浏览</b></a>
                               <label style="color:red;">(建议图片大小1M以内)</label>
							</span>
                </td>
            </tr>
            <tr>
                <td class="text-rt td-gray-2 tdbd-bot">是否回放</td>
                <td class="tdbd-bot-2">
                    <span class="marginr10"><input type="radio" name="isHF" <#if liveInfo.isHf==0> checked="checked"</#if>    class="marginr5" value="0">否</span>
                    <span class="marginr10"><input type="radio" name="isHF" class="marginr5" value="1"  <#if liveInfo.isHf==1> checked="checked"</#if>   >是</span>
                </td>

            </tr>

        </table>
        <div class="text-rt paddtb20"><a href="javascript:;" id="tj" class="btn-fm fmsubmit marginr10"><b>提交</b></a><a href="/v1/adminList" class="btn-fm resetbtn"><b>返回</b></a></div>
    </form>
</div>
<#--图片上传-->
<div>
    <form id="uploadBannerPicForm" name="uploadFileForm"
          action=""
          method="post" enctype="multipart/form-data"
          target="upload_pro_pic1">
        <input id="pro_pic_url" style="display:none;width:350px;"
               name="imgFile" type="file" onchange="changePic();"   accept=".jpg,.png,.psd,.bmp" />
        <input type="hidden" id="token" name="token">

        <input name="uploadId" id="uploadId" type="hidden"  />
        <input name="url" id="url" type="hidden"  />
    </form>
    <iframe src="" name="upload_pro_pic1" width="0" height="0" style="top:-100px;"></iframe>
</div>

<#--<input id="pro_pic_url" style="display:none;width:350px;"
       name="photofile" type="file" onchange="changePic();"/>-->


<script>

    function uploadPicToService(divId){

        var formData = new FormData($("#uploadBannerPicForm")[0]);
        $.ajax({
            url : "/v1/uploadV2",
            type : 'POST',
            data : formData,
            cache: false,
            async: false,
            processData : false,  //必须false才会避开jQuery对 formdata 的默认处理
            contentType : false,  //必须false才会自动加上正确的Content-Type

            success: function (data) {
                var uploadId=$("#uploadId").val();
                if(data.status==1){
                    $("#img_"+uploadId).val(data.imgUrl);
                    $.dialog.tips('上传成功！',1,'alert.gif',function(){});
                    if("1111"==uploadId){//竖屏
                        $("#"+uploadId).html("<img id=\"vimg\" src="+data.imgUrl+"  style=\"height:200px;width:136px;\" />");
                    }else{//横屏
                        $("#"+uploadId).html("<img id=\"himg\" src="+data.imgUrl+"  style=\"height:110px;width:196px;\" />");
                    }

                }else{
                    $.dialog.tips('上传失败，请联系管理员！',1,'alert.gif',function(){});
                }

                /*
                layer.alert("增加成功", {icon: 6}, function () {
                            window.parent.location.reload(); //刷新父页面
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                */
            },
            error: function (data) {
                console.log("失败");
                /*
                layer.msg(data.message, {time: 2000});
                */
            }
        });


    }

    /**
     图片上传相关js代码
     **/
    function changePic(i) {
        //alert("图片改变") ;
        uploadPicToService("11");
    }

    function uploadFile(uploadId) {
        $("#uploadId").val(uploadId);
        $("#pro_pic_url").click();
    }

    function processUploadImgCallback(obj) {
        alert("回调了") ;

    }
    //图片上传js代码结束
    $(function(){
        $(".fmsubmit").click(function(){
            $("#myform").submit();

        })
        var validator=$("#myform").validate({
            errorClass:"fm-ms-error",//用此设定的样式来定义错误消息的样式
            rules: {           //定义验证规则,其中属性名为表单的name属性
                title: {
                    required: true,
                    minlength: 2
                },
                isHF:"required",
                zsxs: {
                    required: true,
                    range: [0,120]
                },
                obsUrl: {
                    required: true,
                    minlength: 2
                }
            },
            messages: {       //自定义验证消息
                title: {
                    required: "直播名是必需的！",
                    minlength: "直播名至少要{0}个字符！"
                },
                zsxs: {
                    required: "注水系数是必需的！",
                    rangelength: "密码要在{0}-{1}个字符之间！"
                },
                isHF: "是否回放是必须的！",
                obsUrl: {
                    required: "直播流地址是必需的！",
                    minlength: "直播流地址至少要{0}个字符！"
                }
            },
            errorPlacement: function(error, element) {
                if ( element.is(":radio")|| element.is(":checkbox") || element.is(".st-group-input") || element.is(":file"))
                    error.appendTo( element.parent().parent());
                else
                    error.appendTo( element.parent("td") );
            },
            highlight: function(element, errorClass) {
                $(element).addClass(errorClass);
            },
            unhighlight: function(element, errorClass) {
                $(element).removeClass(errorClass);
            },
            success: function(label) {
                label.addClass("fm-ms-success").html("&nbsp;")
            },
            validClass:"fm-ms-success",//设定当验证通过时，消息显示的样式
            submitHandler:function(form){

                $("#tj").replaceWith("<a href=\"javascript:;\" id=\"tj\" class=\"btn-fm  marginr10\"><b>提交</b></a>");
                $.ajax({
                    type:'post',
                    async: true,
                    url:"/v1/update?date="+new Date().getTime(),
                    data:$("#myform").serialize(),
                    success:function(msg){

                        $.dialog.tips('保存成功！',1,'alert.gif',function(){});
                        location.href="/v1/adminList";
                    }
                });
            }
        });
        $(".resetbtn").click(function(){
            validator.resetForm()
        })
    });
</script>
</body>
</html>