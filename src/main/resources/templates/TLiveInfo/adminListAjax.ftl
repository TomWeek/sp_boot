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
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/lhgdialog/lhgdialog.min.js?skin=idialog" ></script>

</head>
<body class="wrapr20">
    <div style="margin-top:20px;">共有记录${total}</div>
    <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tab-lt tab-hover" id="dg">
        <tr class="tab-thead">

            <th>直播ID</th>
            <th>直播名称</th>
            <th>直播开始时间</th>
            <th>直播状态</th>
            <th>可否回放</th>
            <th>操作人</th>
            <th>操作时间</th>
            <th>操作</th>
        </tr>
<#list dataList as obj>
    <tr class="dg_row">

        <td class="text-ct">${obj.id}</td>
        <td class="text-ct">${obj.title}</td>
        <td class="text-ct"><#if obj.startTime?? >${obj.startTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
        <td class="text-ct">
            <#if obj.status=="1">
                <font color="red">直播中</font>
            <#elseif obj.status=="2">
                <font color="#32cd32"> 未开始</font>
            <#elseif obj.status=="3">
              已结束
            <#else>
              未知
            </#if>
        </td>
        <td class="text-ct">
            <#if obj.isHf=="1">
                可回放
            <#elseif obj.isHf=="0">
                不可回放
            <#else>
                未知
            </#if>
        </td>
        <td class="text-ct">${obj.optName}管理员</td>
        <td class="text-ct"><#if obj.modTime?? >${obj.modTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
        <td class="text-ct">
            <a href="/v1/edit?id=${obj.id}" class="btngp btn-edit" title="修改"><i>&nbsp;</i><b>修改</b></a>
            <a href="javascript:deleteLive('${obj.id}');" class="btngp btn-delete" title="删除"><i>&nbsp;</i><b>删除</b></a>
            <#if obj.status=="2">
            <a href="javascript:setBeginOrEndTime('${obj.id}','1');" title="开始" class="btngp btn-browse"><i>&nbsp;</i><b>开播</b></a>
            </#if>
            <#if obj.status=="1">
                <a href="javascript:setBeginOrEndTime('${obj.id}','3');" class="btngp btn-save" title="结束"><i>&nbsp;</i><b>结束</b></a>
            </#if>

        </td>
    </tr>

</#list>


    </table>
    <#--<div class="page-area clearfix">
        <div class="fl">
            <div class="page-st marginr10">
                <input type="text" class="st-group-input" readonly="readonly">
                <a href="javascript:;" class="page-st-toggle">请选择<b>&nbsp;</b></a>
                <ul class="page-st-lt">
                    <li class="st-group-lt-ft"><a href="javascript:;">请选择</a></li>
                    <li><a href="javascript:;">1</a></li>
                    <li><a href="javascript:;">2</a></li>
                    <li><a href="javascript:;">3</a></li>
                    <li><a href="javascript:;">4</a></li>
                </ul>
            </div>
            找到25条记录  显示1到9
        </div>
        <div class="rt page-lt">
            <a href="" title="上一页"><b><</b></a>
            <a href="" class="current"><b>1</b></a>
            <a href=""><b>2</b></a>
            <a href=""><b>3</b></a>
            <a href=""><b>4</b></a>
            ...
            <a href=""><b>5</b></a>
            <a href=""><b>6</b></a>
            <a href=""><b>7</b></a>
            <a href=""><b>8</b></a>
            <a href="" title="下一页"><b>></b></a>
        </div>
    </div>-->

    ${pageHtml}
</body>
</html>