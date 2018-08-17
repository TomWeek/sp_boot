package com.hudong.b16live.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证整个请求是否合法,通过私钥做md5验证
 * @Title: LegalRequest.java
 * @Description: <br>
 *               <br>
 * @Company: 在线
 * @Created on 2011-9-19 下午05:33:42
 * @author zhaoc
 * @version $Revision: 1.0 $
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LegalRequestValidator.class)
@Documented
public @interface LegalRequest {

    String message() default "非法请求";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
}
