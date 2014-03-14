/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptorweb.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class catches ALL request (only realy needs to catch certain request, but I do not know what they are)
 * and makes sure the apache trinidad components work when using spring webflow
 *  by redirecting them to the correct ADF components URL.
 *
 * The problem is: while loading the calendar popup of trinidad swf generates a request uri like this:
 * <myapp>/flow1?execution=e2s2& _t=fred&_red=cd&value=1256825236538&loc=de-DE&enc=UTF-8
 * instead of
 * <myapp>/faces/__ADFv__?_t=fred&_red=cd&value=1256825236538&loc=d e-DE&enc=UTF-8
 *
 *  * @author philsmart
 *
 */
public class TrinidadServletFilter implements Filter{

    static Logger log = LoggerFactory.getLogger(TrinidadServletFilter.class);

    private String facesServletMapping;
    private String searchString;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	//log.debug("Doing Trinidad Filter");
	if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String requestURI = httpRequest.getRequestURI();
	    String queryString = httpRequest.getQueryString();
	    int idx = 0;
	    if ( (idx = StringUtils.indexOf(queryString, this.searchString)) >= 0 &&
	        requestURI.contains(this.facesServletMapping) == false ) {
	      String forwardUri = httpRequest.getContextPath();
	      forwardUri = forwardUri + this.facesServletMapping + "/__ADFv__?" + queryString.substring(idx+1);
	      ((HttpServletResponse) response).sendRedirect(forwardUri);
	    } else {
	      chain.doFilter(request, response);
	    }
	  }

	}

    	@Override
	public void init(FilterConfig config) throws ServletException {
	  this.facesServletMapping = StringUtils.defaultIfEmpty(config.getInitParameter("facesServletMapping"), "/faces");
	  this.searchString = StringUtils.defaultIfEmpty(config.getInitParameter("searchString"), "&_t=fred&_red");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	    // TODO Auto-generated method stub

	}



}
