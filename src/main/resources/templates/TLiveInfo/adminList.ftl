<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#assign ctx=request.contextPath />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/skins/blue/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/skins/blue/css/colorskin.css" />
    <script type="text/javascript" src="${ctx}/skins/blue/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/lhgdialog/lhgdialog.min.js?skin=idialog" ></script>
    <script type="text/javascript" src="${ctx}/skins/blue/js/form.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>

    <script type="text/javascript">

      $(function () {
          stgroup(".st-group-toggle",".st-group-lt")//下拉菜单
          stgroup(".page-st-toggle",".page-st-lt")//控制翻页内模拟下拉菜单

          $(".st-group-lt li").click(function(){
              $(this).parent().siblings("input").focus();
              var pagenum=$(this).children().html();
              var div=$(this).parent().parent();
              //取li的name放到input隐藏域中以传值
              div.find("input").val($(this).attr("name"));
              $(this).parent().siblings(".st-group-toggle").html(pagenum+"<b>&nbsp;</b>");
              $(this).siblings(".st-group-lt").removeClass("select-hold");
              $(this).siblings(".st-group-lt").hide();
              $(this).parent().siblings("input").blur();
          });
          getAdminAjax();
      })
      function getAdminAjax() {
          $.ajax({
              type:'post',
              async: true,
              url:"/v1/adminListAjax?date="+new Date().getTime(),
              data:$("#zbFrm").serialize(),
              success:function(msg){
                  $("#zbListDiv").html(msg);
              }
          });
      }

      function setBeginOrEndTime(id,flag){
          var mytips="";
          if("1"==flag){
              mytips="确定要开始直播吗？"
          }else if("3"==flag){
              mytips="确定要结束直播吗？"
          }
          $.dialog.confirm(mytips, function(){
              //写具体的函数
              $.ajax({////1是开始，3结束
                  type:'post',
                  async: true,
                  url:"/v1/setBeginOrEndTime?id="+id+"&flag="+flag+"&date="+new Date().getTime(),
                  data:$("#zbFrm").serialize(),
                  success:function(msg){
                      if("1"==msg){
                          $.dialog.tips('更新成功！',1,'alert.gif',function(){});
                          getAdminAjax();
                      }else if("0"==msg){
                          $.dialog.tips('更新失败，请联系管理员！',1,'alert.gif',function(){});
                      }

                  }
              });

          }, function(){

          });
      }
      function deleteLive(id){
          $.dialog.confirm("确定要删除该直播吗？", function(){
              //写具体的函数
              $.ajax({////1是开始，3结束
                  type:'post',
                  async: true,
                  url:"/v1/deleteLive?id="+id+"&date="+new Date().getTime(),
                  data:$("#zbFrm").serialize(),
                  success:function(msg){
                      if("1"==msg){
                          $.dialog.tips('删除成功！',1,'alert.gif',function(){});
                          getAdminAjax();
                      }else if("0"==msg){
                          $.dialog.tips('删除失败，请联系管理员！',1,'alert.gif',function(){});
                      }

                  }
              });

          }, function(){

          });
      }

      /**
       * 分页
       * @param pageNo
       */
      function goToPage(pageNo){
          $.ajax({
              type:'post',
              async: true,
              url:"/v1/adminListAjax?pageNo="+pageNo+"&date="+new Date().getTime(),
              data:$("#zbFrm").serialize(),
              success:function(msg){
                  $("#zbListDiv").html(msg);
              }
          });
      }

      function downLoadLive() {
          var id=$("#id").val();
          var title=$("#title").val();
          var status=$("#status").val();
          var startTime=$("#startTime").val();
          var endTime=$("#endTime").val();

          location.href="/v1/exportLive?id="+id+"&title="+title+"&status="+status+"&startTime="+startTime+"&endTime="+endTime
      }
    </script>
</head>
<body class="wrapr20">
<div class="breadcrumb">当前位置：
    <span>直播管理</span><i>|</i>
    <span>直播列表</span>
</div>

<form name="zbFrm" id="zbFrm">
<div class="layoutop paddt0">
    		<br />
    <span class="qy-cond">
	    		直播ID：<input type="text" class="text w130" name="id" id="id" />
	    	</span>
    <span class="qy-cond">
	    		直播名称：<input type="text" class="text w130" name="title" id="title" />
	    	</span>


    <span class="qy-cond">
	    		直播状态：
	    		<span class="st-group s100">
					<input type="text" id="status" name="status" value="" class="st-group-input" readonly="readonly">
					<a href="javascript:;" class="st-group-toggle">-----请选择------<b>&nbsp;</b></a>
					<ul class="st-group-lt" style="display: none;">
						<li name="" class="st-group-lt-ft"><a href="javascript:;">请选择</a></li>
						<li name="1" ><a href="javascript:;">直播中</a></li>
						<li name="2" ><a href="javascript:;">未开始</a></li>
						<li name="3" ><a href="javascript:;">已结束</a></li>

					</ul>
				</span>
			</span>
    <br />
    <span class="qy-cond">
				开始时间：
				<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="startTime" name="startTime" class="text input_date"/>
	    	</span>
    <span class="qy-cond">
				结束时间：
				<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="endTime" name="endTime" class="text input_date"/>
	    	</span>

    <span class="qy-cond" style="margin-left: 150px;">
	    		<a href="javascript:getAdminAjax();" class="btngp btn-search"><i>&nbsp;</i><b>搜索</b></a>
	    		<a href="javascript:downLoadLive();" class="btngp btn-download"><i>&nbsp;</i><b>下载</b></a>

	    	</span>
</div>
</form>

<div id="zbListDiv">

</div>
</body>
</html>