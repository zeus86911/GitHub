package com.xxx.utils;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * Description: 自定义 HttpDelete 支持添加请求体
 * 
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

	public final static String METHOD_NAME = "DELETE";

	public HttpDeleteWithBody() {
		super();
	}

	public HttpDeleteWithBody(final URI uri) {
		super();
		setURI(uri);
	}

	public HttpDeleteWithBody(final String uri) {
		super();
		setURI(URI.create(uri));
	}

	@Override
	public String getMethod() {
		return METHOD_NAME;
	}

}