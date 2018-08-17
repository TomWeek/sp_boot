package com.tls.sigcheck;

public class tls_sigcheck
{
    public void loadJniLib(String libPath) {
        System.load(libPath);
    }

    @Deprecated
    public native int tls_gen_signature_ex(
        String strExpire,
        String strAppid3rd,
        String SdkAppid,
        String strIdentifier,
        String dwAccountType,
        String strPriKey
        );
    public native int tls_gen_signature_ex2(
        String sdkAppid,
        String identifier,
        String priKey
        );

    public native int tls_gen_signature_ex2_with_expire(
        String sdkAppid,
        String identifier,
        String priKey,
		String expire
        );

    @Deprecated
	public native int tls_check_signature_ex(
        String strJsonWithSig,
        String strPubKey,
        String strAccountType,
        String str3rd,
        String Appid,
        String strIdentify
        );

	public native int tls_check_signature_ex2(
        String userSig,
        String pubKey,
        String sdkAppid,
        String identifier
        );

    protected int expireTime;
    protected int initTime;
	protected String strErrMsg;	
	protected String strJsonWithSig;

    public int getExpireTime()
    {
        return expireTime;
    }

    public int getInitTime()
    {
        return initTime;
    }

	public String getErrMsg()
	{
		return strErrMsg;
	}
	public String getSig()
	{
		return strJsonWithSig;
	}
}
