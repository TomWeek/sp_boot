		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$(".ztreeclose").click(function(){
			  $("#inneriframeclose").hide();
			  $("#inneriframeopen").show();
			});
			$(".ztreeopen").click(function(){
			  $("#inneriframeclose").show();
			  $("#inneriframeopen").hide();
			});
		});
		
		//二级iframe页面高度
		function ctrlinnerframeht(){
		    var h = top.document.body.clientHeight - 92; //当前页面高度- (顶部iFrame + 底部iFrame)高度
		    var topht=top.document.getElementById("topinfor").style.display;
		    if(topht=="none")
		    {
				h=top.document.body.clientHeight;
		    }
		    var d=h-58;
		    document.getElementById('inneriframelf').style.height = d+ "px";
		    document.getElementById('inneriframeinfor').style.height = d+ "px";
		    document.getElementById('inneriframert').style.height = d+ "px";
			var treewt=document.getElementById("inneriframeclose").width
			document.getElementById('treeDemo').style.width =treewt+"px";
			var ht = h - 129; 
			document.getElementById('treeDemo').style.height=ht+"px";
			/*
	    	document.getElementById('treeDemo').style.maxHeight=ht+"px";
			var isIE=!!window.ActiveXObject;
			var isIE6=isIE&&!window.XMLHttpRequest;
			if(isIE6)
			{
				document.getElementById('treeDemo').style.height=ht+"px";
			}
			*/
		}
		//鼠标拖动 改变左右宽度
        function customWidth(evt) {
        var aa=document.getElementById("inneriframeclose").style.display;
        if(aa!="none"){ //判断 当inneriframeclose不是隐藏的时候 才可以执行拖动
            document.onmousemove = function(evt) {
                var evtObj = window.event ? event: evt;
        		if(document.all) { // is ie 
		            px = evtObj.clientX; 
		            px += document.documentElement.scrollLeft; 
		        } else { 
		            px = evtObj.clientX; 
		        } 
                if (px && document.getElementById("inneriframeclose")) {
                    document.getElementById("inneriframeclose").width = px;
                    document.getElementById("treeDemo").style.width =px+"px";
                }
            };
           }
        }
        function cancelCustomWidth() {
            document.onmousemove =null; //取消onmousemove事件
        }