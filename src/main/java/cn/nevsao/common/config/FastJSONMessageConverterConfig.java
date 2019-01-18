/**
 * <html>
 * <body>
 *  <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 *  <p> 粤ICP备16046232号-1 </p>
 *  <p> Created on 2018年5月22日</p>
 *  <p> Created by benson</p>
 * </body>
 * </html> 
 */
package cn.nevsao.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * spring-mvc json消息转换器配置
 * 
 * @Project yiheni-framework
 * @Package com.yiheni.framework.common.spring.config
 * @ClassName FastJSONMessageConverter
 * @Author benson
 * @Date 2018年5月22日
 * @Version 1.0
 */
@Configuration
public class FastJSONMessageConverterConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);

		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty,
				/*SerializerFeature.WriteNullNumberAsZero,*/ SerializerFeature.WriteNullBooleanAsFalse,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteEnumUsingToString,
				SerializerFeature.WriteMapNullValue,
				//全局date 默认输出格式为"yyyy-MM-dd HH:mm:ss"  可通过修改com.alibaba.fastjson.Json.DEFFAULT_DATE_FORMAT设置
				SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);

		fastConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(fastConverter);
	}
}