package com.xxx.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Description: httpClient工具类
 * @author zzz
 * @date 2019-03-21
 * @version 基于 HttpClient 4.5.6
 */
public class HttpClientUtils {

	// 编码格式。发送编码格式统一用UTF-8
	private static final String ENCODING = "UTF-8";

	// 设置连接超时时间，单位毫秒。
	private static final int CONNECT_TIMEOUT = 10000;

	// 请求获取数据的超时时间(即响应时间)，单位毫秒。
	private static final int SOCKET_TIMEOUT = 10000;

	/**
	 * 发送get请求；不带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url) throws Exception {
		return doGet(url, null, null);
	}

	/**
	 * 发送get请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, Object> params) throws Exception {
		return doGet(url, null, params);
	}

	/**
	 * 发送get请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建访问的地址
		URIBuilder uriBuilder = new URIBuilder(url);
		// 设置请求参数
		if (params != null) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof String) {
					uriBuilder.addParameter(entry.getKey(), (String) entry.getValue());
				}
				if (entry.getValue() instanceof List) {
					for (String strValue : (List<String>) entry.getValue()) {
						uriBuilder.addParameter(entry.getKey(), strValue);
					}
				}
			}
		}

		// 创建http对象
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。 setConnectionRequestTimeout：设置从connect
		 * Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpGet.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpGet);
		// httpGet.setHeader("Cookie", "");
		// httpGet.setHeader("Connection", "keep-alive");
		// httpGet.setHeader("Accept", "application/json");
		// httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		// httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
		// httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64)
		// AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpGet);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}
	
	/**
	 * 发送get请求下载文件
	 * 
	 * @param url 请求地址
	 * @param filePath 文件保存地址
	 * 
	 */
	public static void downLoad(String url, String filePath) throws Exception {
		InputStream ins = null;
		OutputStream outs = null;

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		try {
			// 创建httpClient对象
			httpClient = HttpClients.createDefault();

			// 创建http对象
			HttpGet httpGet = new HttpGet(url);

			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
			httpGet.setConfig(requestConfig);

			// 执行请求并获得响应结果
			httpResponse = httpClient.execute(httpGet);

			// 获取IO流
			File file = new File(filePath);
			FileUtils.forceMkdir(file.getParentFile());
			outs = new FileOutputStream(file);
			ins = httpResponse.getEntity().getContent();

			// 写本地文件
			byte[] buffer = new byte[4096];
			int len = 0;
			while ((len = ins.read(buffer)) != -1) {
				outs.write(buffer, 0, len);
			}
			
			outs.flush();
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (outs != null) {
					outs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 发送post请求；不带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url) throws Exception {
		return doPost(url, null, null);
	}

	/**
	 * 发送post请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, Object> params) throws Exception {
		return doPost(url, null, params);
	}

	/**
	 * 发送post请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpPost);

		// 封装请求参数
		packageParam(params, httpPost);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送post请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPostForFileForm(String url, Map<String, Object> params) throws Exception {
		return doPostForFileForm(url, null, params);
	}

	/**
	 * 发送post请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static HttpClientResult doPostForFileForm(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpPost);

		// 封装请求参数
		if (params != null) {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			ContentType contentType = ContentType.create("multipart/form-data", Consts.UTF_8);
			// builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); 
			builder.setContentType(contentType);

			for (Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof File) {
					builder.addBinaryBody(entry.getKey(), (File) entry.getValue(), contentType, ((File) entry.getValue()).getName());
				}
				if (entry.getValue() instanceof byte[]) {
					builder.addBinaryBody(entry.getKey(), (byte[]) entry.getValue());
				}
				if (entry.getValue() instanceof String) {
					builder.addTextBody(entry.getKey(), (String) entry.getValue(), contentType);
				}
				if (entry.getValue() instanceof List) {
					for (String strValue : (List<String>) entry.getValue()) {
						builder.addTextBody(entry.getKey(), strValue, contentType);
					}
				}
			}

			httpPost.setEntity(builder.build());
		}

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送post请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param josnStr 请求参数-json字符串
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPostForJson(String url, String josnStr) throws Exception {
		return doPostForJson(url, null, josnStr);
	}

	/**
	 * 发送post请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param josnStr 请求参数-json字符串
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPostForJson(String url, Map<String, String> headers, String josnStr) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpPost);

		// 封装请求参数
		StringEntity requestEntity = new StringEntity(josnStr, ENCODING);
		requestEntity.setContentType("application/json");
		httpPost.setEntity(requestEntity);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送put请求；不带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url) throws Exception {
		return doPut(url, null, null);
	}

	/**
	 * 发送put请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url, Map<String, Object> params) throws Exception {
		return doPut(url, null, params);
	}

	/**
	 * 发送put请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建http对象
		HttpPut httpPut = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPut.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpPut);

		// 封装请求参数
		packageParam(params, httpPut);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPut);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送delete请求；不带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url) throws Exception {
		return doDelete(url, null, null);
	}

	/**
	 * 发送delete请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url, Map<String, Object> params) throws Exception {
		return doDelete(url, null, params);
	}

	/**
	 * 发送delete请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建http对象
		HttpDeleteWithBody httpDelet = new HttpDeleteWithBody(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpDelet.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpDelet);

		// 封装请求参数
		packageParam(params, httpDelet);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpDelet);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}
	
	/**
	 * Description: 封装请求头
	 * 
	 * @param params
	 * @param httpMethod
	 */
	public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
		// 封装请求头
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				// 设置到请求头到HttpRequestBase对象中
				httpMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Description: 封装请求参数
	 * 
	 * @param params
	 * @param httpMethod
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public static void packageParam(Map<String, Object> params, HttpEntityEnclosingRequestBase httpMethod)
			throws UnsupportedEncodingException {
		// 封装请求参数
		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof String) {
					nvps.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
				}
				if (entry.getValue() instanceof List) {
					for (String strValue : (List<String>) entry.getValue()) {
						nvps.add(new BasicNameValuePair(entry.getKey(), strValue));
					}
				}
			}

			// 设置到请求的http对象中
			httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
		}
	}

	/**
	 * Description: 获得响应结果
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient,
			HttpRequestBase httpMethod) throws Exception {
		// 执行请求
		httpResponse = httpClient.execute(httpMethod);

		// 获取返回结果
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = null;
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
			} else {
				content = "";
			}
			return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
		}
		return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Description: 释放资源
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
		// 释放资源
		try {
			if (httpResponse != null) {
				httpResponse.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			if (httpClient != null) {
				httpClient.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}