package com.hudong.b16live.utils;


public class SignConstant {
	public final static String SKDAPPID = "1400120205";

	public final static String IDENTIFIER = "shiliuadmin";

	public final static String PRIVSTR = "-----BEGIN PRIVATE KEY-----\n" +
				"MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgU7utN+Wq08aDE741\n" +
				"6B1pI69lLicqEnoty2kKPaWUwCihRANCAARKDqDheen4T6R5TceVTLS8rJAFumid\n" +
				"ad0cQfnlqBmpIzMcGZKjJXffcSulwruiExscG1DyAjcAe8qYKPEhm+zs\n" +
				"-----END PRIVATE KEY-----";

	public final static String PUBSTR = "-----BEGIN PUBLIC KEY-----\n" +
				"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAESg6g4Xnp+E+keU3HlUy0vKyQBbpo\n" +
				"nWndHEH55agZqSMzHBmSoyV333ErpcK7ohMbHBtQ8gI3AHvKmCjxIZvs7A==\n" +
				"-----END PUBLIC KEY-----";




	public final static String CREATEGROUPURL = "https://console.tim.qq.com/v4/group_open_http_svc/create_group?";

	public final static String SENDGROUPMSGURL = "https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg?";

	public final static String GETGROUPINFOURL = "https://console.tim.qq.com/v4/group_open_http_svc/get_group_info?";

	public final static String MODIFYGROUPMEMBERURL = "https://console.tim.qq.com/v4/group_open_http_svc/modify_group_member_info?";

	public final static String DESTROYGROUPURL = "https://console.tim.qq.com/v4/group_open_http_svc/destroy_group?";

	public final static String SIGN = "eJxlj81ugkAYRfc8BWFrU*eH0drEhTZYabCktkbTzYTCAF*BAYfBgE3f3ZSalKR3e07uzf0yTNO03rzX2yAMy0ZqrrtKWOa9aSHr5g9WFUQ80Jyq6B8UbQVK8CDWQvUQM8YIQkMHIiE1xHA16hRyaIKoADmQ6ijj-dJvi40QJoggNlQg6eHGeXlwnSNpbP*wPax34mnqOGXml9PQWcaJfN8ktjce79vsM83RbrWA5WnysSrcTrPu0R*tyzOu3XakWjrJ7bPaN7nX2kf2HHcpW8zng0kNhbjeonR2R-BseOwkVA2l7AWCMMOEop9YxrdxARqWX8Y_";

	public final static String HDLIVESIGN = "hdLiveSign";

	public final static String HDLIVEUSERSIGN = "hdLiveUserSign_";

	public final static long HDLIVECACHETIME = 3600*24*30;
	//视频拼接接口
	public final static String CONCATVIDEO = "https://vod.api.qcloud.com/v2/index.php?Action=ConcatVideo";
	//腾讯云API密钥 
	public final static String TENCENTSECRETID = "AKIDX0JRPSu4kNsHWabPAkCUaZOJGEJIFpKf";
	public final static String TENCENTSECRETKEY = "Qjs7CLNLKMSZcUCxjS8i8NRNBpIh3KQO";
}
