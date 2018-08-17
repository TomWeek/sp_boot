package com.hudong.b16live.utils;

import com.hudong.core.common.logger.HuDongErrorCodeEnum;
import com.hudong.core.common.logger.HuDongLogger;
import com.hudong.core.common.logger.HuDongLoggerFactory;
import com.tls.sigcheck.tls_sigcheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 由于生成 sig 和校验 sig 的接口使用方法类似，故此处只是演示了生成 sig 的接口调用
 * 使用的编译命令是javac -encoding utf-8 Demo.java
 * 使用的运行命令是
 * java Demo
 */

@Component
public class Sigature {

    private final static HuDongLogger logger = HuDongLoggerFactory.getLogger(Sigature.class);

    public static String FILE_NAME;

    private static String SO_PATH = "";

    @Value("${liveTls.config.file}")
    public void setFileName(String db) {
        FILE_NAME = db;
        try {
            SO_PATH = LiveUtils.searchClassLoadPath(FILE_NAME);
        } catch (Exception e) {
            logger.error(HuDongErrorCodeEnum.ProgramError, "findPathError", e);
        }
    }

    public static String getHdSign() {
        String sign = "";
        try {
            tls_sigcheck sigCheck = new tls_sigcheck();
            sigCheck.loadJniLib(SO_PATH);
            int ret = sigCheck.tls_gen_signature_ex2(SignConstant.SKDAPPID, SignConstant.IDENTIFIER, SignConstant.PRIVSTR);
            if (0 == ret) {
                sign = sigCheck.getSig();
            }
        } catch (Exception e) {
            logger.error(HuDongErrorCodeEnum.ProgramError, "getHdSign", e);
        }
        return sign;
    }

    public static String getSign(String userId) {
        String sign = "";
        try {
            tls_sigcheck sigCheck = new tls_sigcheck();
            sigCheck.loadJniLib(SO_PATH);
            int ret = sigCheck.tls_gen_signature_ex2(SignConstant.SKDAPPID, userId, SignConstant.PRIVSTR);
            if (0 == ret) {
                sign = sigCheck.getSig();
            }
        } catch (Exception e) {
            logger.error(HuDongErrorCodeEnum.ProgramError, "getSign" + userId, e);
        }
        return sign;
    }
}
