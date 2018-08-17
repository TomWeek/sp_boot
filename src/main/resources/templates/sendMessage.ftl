<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style type="text/css">
        .talk_con{
            width:600px;
            height:550px;
            border:1px solid #666;
            margin:50px auto 0;
            background:#f9f9f9;
        }
        .talk_show{
            width:580px;
            height:420px;
            border:1px solid #666;
            background:#fff;
            margin:10px auto 0;
            overflow:auto;
        }
        .talk_input{
            width:580px;
            margin:10px auto 0;
        }
        .whotalk{
            width:20%;
            height:30px;
            float:left;
            outline:none;
        }
        .liveselect{
            width:80%;
            height:30px;
            outline:none;
        }
        .talk_word{
            width:420px;
            height:50px;
            padding:0px;
            float:left;
            margin-left:10px;
            outline:none;
            text-indent:10px;
            overflow:hidden;
        }        
        .talk_sub{
            width:56px;
            height:30px;
            float:left;
            margin-left:10px;
        }
        .atalk{
           margin:10px; 
		   text-align:right;
        }
        .atalk span{
            display:inline-block;
            background:#0181cc;
            border-radius:10px;
            color:#fff;
            padding:5px 10px;
        }
        .btalk{
            margin:10px;
            text-align:right;
        }
        .btalk span{
            display:inline-block;
            background:#ef8201;
            border-radius:10px;
            color:#fff;
            padding:5px 10px;
        }
    </style>
    <script type="text/javascript" src="http://www.huimg.cn/??lib/jquery-1.6.4.min.js"></script>
    <script type="text/javascript">
        window.onload = function(){
            var TalkSub = document.getElementById("talksub");
            TalkSub.onclick = function(){
                var TalkWords =$("#talkwords");
                var Words = document.getElementById("words");
                var Who = document.getElementById("who");
                //定义空字符串
                var str = "";
                if(TalkWords.val() == ""){
                     // 消息为空时弹窗
                    alert("消息不能为空");
                    return;
                }
                $.ajax({
                    type : "get",
                    url : "/imMessage/submit.do",
                    data:{"userNo":$("#who").val(),"liveId":$("#live").val(),"userNick":$("#who").find("option:selected").text(),"content":TalkWords.val()},
                    dataType : "json",
                    async : false,
                    success : function(data) {
                        console.log(data);

                        if (Who.value == "shiliuadmin1") {
                            //如果Who.value为0n那么是 A说
                            str = '<div class="atalk"><span>石榴君说 :' + TalkWords.val() + '</span></div>';
                        }
                        else {
                            str = '<div class="btalk"><span>石榴姐说 :' + TalkWords.val() + '</span></div>';
                        }
                        // 将之前的内容与要发的内容拼接好 提交
                        Words.innerHTML = Words.innerHTML + str;
                        TalkWords.val("");
                    }
                })
            }
        }
    </script>
</head>
<body>
    <div class="talk_con">
        <select class="whotalk" id="who">
            <option value="shiliuadmin1">石榴君</option>
            <option value="shiliuadmin2">石榴姐</option>
        </select>
        <select class="liveselect" id="live">
          <option value="">请选择直播</option>
          <#list liveList as live>
            <option value="${live.liveId}">${live.liveTitle}</option>
          </#list>
        </select>
        <div class="talk_show" id="words">
        </div>
        <div class="talk_input">
            <textarea name="txt" id="talkwords" class="talk_word"  rows="3" warp="virtual"></textarea>
            <input type="button" value="发送" class="talk_sub" id="talksub">
        </div>
    </div>
</body>