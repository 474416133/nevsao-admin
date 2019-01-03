/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019年01月03日</p>
 * <p> Created by sven</p>
 * </body>
 * </html>
 */
package cn.nevsao.common.mvc.advice;

import cn.nevsao.common.mvc.vo.ResponseBo;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.mvc.advice
 * @ClassName ResponseBoAdvice
 * @Author sven
 * @Date 2019年01月03日
 * @Version 1.0
 */
//@ControllerAdvice
public class ResponseBoAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.hasMethodAnnotation(ResponseBody.class)){
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (o instanceof ResponseBo){
            return o;
        }else{
            return ResponseBo.ok(o);
        }
    }
}
