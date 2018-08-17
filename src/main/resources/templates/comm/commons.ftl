<!--分享-->
<div class="popbox" style="display: none">
    <div class="share-pop" >
        <h4 class="share-title">分享词条给好朋友</h4>
        <div class="ishare" id="ckepop">
            <em id="share">
                <a id="weibo"  title="分享到新浪微博" target="_blank"><span class="jtico jtico_tsina"></span>新浪微博</a>
                <a id="qzone" title="分享到QQ空间" target="_blank"><span class="jtico jtico_qzone"></span>QQ空间</a>
                <a id="weixin" title="分享到微信" target="_blank"><span class="jtico jtico_weixin"></span>微信</a>
            </em>
        </div>
        <i class="iclose">&nbsp;</i>
    </div>
</div>
<!--图片浮层-->
<div class="popup" style="display: none">
    <em class="tu-close"></em>
    <div class="swiper-container know-swiper">
        <div class="swiper-wrapper">
        </div>
        <div class="swiper-pagination know-pagination"></div>
    </div>
</div>
<!--二维码-->
<div class="pop-code" style="display: none">
    <div class="codebox">
        <i class="iclose">&nbsp;</i>
        <h3>扫码或保存</h3>

        <a href="javascript:;"><div id="qrcode"></div></a>
        <p>
            <b>长按上图保存二维码</b>，使用微信扫一扫右上角的"相册"扫码，再分享好友或朋友圈
        </p>
    </div>
</div>

<!--公用JS-->
<script type="text/javascript"  src="http://www.huimg.cn/lib/jquery-1.7.min.js"></script>
<script type="text/javascript"  src="http://www.huimg.cn/wap/know/js/swiper.min.js"></script>
<script type="text/javascript"  src="http://www.huimg.cn/lib/jquery.qrcode.min.js"></script>
<script type="text/javascript"  src="http://www.huimg.cn/wap/know/js/fastknow.js"></script>
<script type="text/javascript"  src="http://www.huimg.cn/wap/know/js/video.min.js"></script>
<script type="text/javascript">
    var swiper1 = new Swiper('.know-swiper', {
        pagination: '.know-pagination',
        paginationType : 'fraction',
        spaceBetween : 5,
        observer:true,
        observeParents:true,
    });
</script>
<script type="text/javascript">
    window._pt_lt = new Date().getTime();
    window._pt_sp_2 = [];
    _pt_sp_2.push('setAccount,4739b3d1');
    var _protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    (function() {
        var atag = document.createElement('script'); atag.type = 'text/javascript'; atag.async = true;
        atag.src = _protocol + 'js.ptengine.cn/4739b3d1.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(atag, s);
    })();
</script>
<script>
    (function() {
        var el = document.createElement("script");
        el.src = "http://ll.hudong.com/flux.js?5a3cbd6ae6563e64071dc041";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(el, s);
    })();
</script>