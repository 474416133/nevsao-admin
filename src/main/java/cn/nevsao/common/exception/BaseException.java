/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018年12月11日</p>
 * <p> Created by sven</p>
 * </body>
 * </html>
 */
package cn.nevsao.common.exception;

import lombok.Data;

/**
 *
 * @Project nevsao-admin
 * @Package cn.nevsao.common.exception
 * @ClassName BaseException
 * @Author sven
 * @Date 2018年12月11日
 * @Version 1.0
 */

@Data
public class BaseException extends RuntimeException{
    private int errorCode;

    public BaseException (int code, String msg){
        super(msg);
        this.errorCode = code;
    }

    public String getErrorMsg(){
        return getMessage();
    }


}
