<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>11快知识</title>
<#include "comm/head.ftl"/>
    <link rel="stylesheet" href="http://www.huimg.cn/wap/know/css/mescroll.min.css"/>
</head>
<body>
<div class="weixin-share" style="display: none"></div>
<div id="mohu" class="">
    <div class="know-head" style="display: none">
        <h2>快知识</h2>
    </div>

    <div class="swiper-container jm-swiper mt44" style="display: none">
        <div class="swiper-wrapper">
        <#list columnList as obj>
            <div class="swiper-slide know-item${obj_index+1}"><a href="/list/${obj.id}"><h2>
                <span>${obj.columnName}</span></h2></a>
            </div>
        </#list>
        </div>
    </div>
    <#--<div class="s2-adv" >
        <a href="http://www.baike.com/tuijian/ztdsjyqh1.html" ><img src="http://static.hudong.com/53/00/26100000010683152421001863085.jpg" width="100%"/></a>
    </div>-->
    <div class="know-list">
    <#list knowledgeList as obj>
        <div class="knowbox">
            <h1 class="know-title"><a href="/list/${obj.columnId?c}">
                <em><img src="${obj.columnPic}"/></em>
            ${obj.columnName}
            </a></h1>
        <#--  <h2><a href="${obj.url}?from=kuaizhishi">${obj.title}</a></h2>-->
            <div class="know-article">
                <p><a href="/detail/${obj.columnId?c}/${obj.id?c}"> ${obj.content}</a></p>
                <#if obj.videoUrl != "">
                    <div class="know-video">
                        <video class="video-js" width="100%" controls poster="${obj.videoPic}">
                            <source src="${obj.videoUrl}" type="video/mp4">
                        </video>
                    </div>
                </#if>
                <ul class="clearfix">
                    <#if (obj.pic)??>
                        <#assign json=obj.pic?eval />
                        <#list json as item>
                            <li><a><img src="${item.img}"/></a></li>
                        </#list>
                    </#if>
                </ul>
            </div>
            <div class="know-op clearfix">
               <#-- <a class="know-share"></a>-->
                <a class="know-like" cardid="${obj.id?c}"><em>${obj.praiseCount?c}</em></a>
                <a id="know-discuss" class="know-discuss" counmId="${obj.columnId?c}">
                    <#if (obj.commentCount?c >0)>
                        <em>${obj.commentCount?c}</em>
                    </#if>
                </a>
                <a href="${obj.url}?from=kuaizhishi" class="know-link"></a>
            </div>
        </div>
    </#list>
        <p class="know-hint" style="display: none">已经到底啦~</p>
        <p class="loading" style="display: none"><img src="http://www.huimg.cn/cms/shouye/loading.gif"/></p>
    </div>


</div>
<!--回复框-->
<div id="pop-discuss" class="pop-discuss" style="display: none">
    <input type="hidden" id="id" value=""/>
    <input type="hidden" id="cnum" value=""/>
    <input type="text" name="comment" id="comment" placeholder="说说你的看法（60字以内）"/>
    <span class="" id="docomment">评论</span>
</div>
<#include "comm/commons.ftl"/>

<script type="text/javascript" charset="utf-8">
    var jmswiper = new Swiper('.jm-swiper', {
        slidesOffsetBefore: 20,
        spaceBetween: 5,
        slidesPerView: 'auto',
        slidesOffsetAfter: 20,
        observer: true,
        observeParents: true,
    });

    $(function () {
        var pageNow = 1;
        var flag = true;//防止多刷
        $(window).scroll(function () {
            var scrollTop = $(this).scrollTop();
            var scrollHeight = $(document).height();
            var windowHeight = $(this).height();
            if (scrollTop + windowHeight == scrollHeight && flag) {
                $(".loading").show();
                // 此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
                $.ajax({
                    //请求方式为get
                    type: "GET",
                    async: true,
                    //json文件位置
                    url: "/ajaxlist/0/" + pageNow,
                    //返回数据格式为json
                    dataType: 'json',
                    //请求成功完成后要执行的方法
                    success: function (data) {
                        if (data.success) {
                            var tList = data.obj;
                            var html = "";
                            for (var o in tList) {
                                html += "<div class='knowbox'>";
                                html += "<h1 class='know-title'><a href='/list/" + tList[o].columnId + "'>";
                                html += "<em><img src='" + tList[o].columnPic + "'/></em>";
                                html += tList[o].columnName;
                                html += "</a></h1>";
                                /*html += "<h2> <a href='" + tList[o].url + "?from=kuaizhishi'>" + tList[o].title + "</a></h2>";*/
                                html += "<div class='know-article'>";
                                html += "<p > <a href='/detail/" + tList[o].columnId + "/" + tList[o].id+"'>" + tList[o].content + "</p>";
                                if (tList[o].videoUrl != null && tList[o].videoUrl != "") {
                                    html += "<div class='know-video'>";
                                    html += "<video class='video-js' width='100%'  controls poster='" + tList[o].videoPic + "'>";
                                    html += "<source src='" + tList[o].videoUrl + "' type='video/mp4'></source>";
                                    html += "</video>";
                                    html += "</div>";
                                }
                                html += "<ul class='clearfix'>";
                                if (tList[o].pic != null && tList[o].pic != "") {
                                    var pList = eval(tList[o].pic);
                                    for (var j in pList) {
                                        html += "<li><a><img src='" + pList[j].img + "'/></a></li>";
                                    }
                                }
                                html += "</ul> </div>";
                                html += "<div class='know-op clearfix'>";
                              /*  html += "<a class='know-share'></a>";*/
                                html += "<a class='know-like' cardid='" + tList[o].id + "'><em>" + tList[o].praiseCount + "</em></a>";
                                html += "<a id='know-discuss' class='know-discuss' counmId='" + tList[o].columnId + "'>";
                                        if(tList[o].commentCount > 0){
                                            html +=  "<em>" + tList[o].commentCount + "</em>";
                                        }
                                html +=    "</a>";
                                html += "<a href='" + tList[o].url + "?from=kuaizhishi' class='know-link'></a>";
                                html += "</div></div>";
                            }
                            $(".know-hint").before(html);
                            ++pageNow;
                        } else {
                            flag = false;
                            $(".know-hint").show();
                        }
                        $(".loading").hide();
                    }
                });
            }
        });
    });
</script>
</body>
</html>
