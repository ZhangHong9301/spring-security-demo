/**
 * 
 */
package com.zxf.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Mr.ZXF
 *
 */
public class MyExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public MyExpiredSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.session.SessionInformationExpiredStrategy#onExpiredSessionDetected(org.springframework.security.web.session.SessionInformationExpiredEvent)
	 */
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}
	
	/* (non-Javadoc)
	 * @see com.zxf.security.browser.session.AbstractSessionStrategy#isConcurrency()
	 */
	@Override
	protected boolean isConcurrency() {
		return true;
	}

}
