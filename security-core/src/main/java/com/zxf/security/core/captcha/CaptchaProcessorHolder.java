/**
 * 
 */
package com.zxf.security.core.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Mr.ZXF
 *
 */
@Component
public class CaptchaProcessorHolder {

	@Autowired
	private Map<String, CaptchaProcessor> captchaProcessors;

	public CaptchaProcessor findCaptchaProcessor(CaptchaType type) {
		return findCaptchaProcessor(type.toString().toLowerCase());
	}

	public CaptchaProcessor findCaptchaProcessor(String type) {
		String name = type.toLowerCase() + CaptchaProcessor.class.getSimpleName();
		CaptchaProcessor processor = captchaProcessors.get(name);
		if (processor == null) {
			throw new CaptchaException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
