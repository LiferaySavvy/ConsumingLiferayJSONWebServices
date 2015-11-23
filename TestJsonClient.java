package com.meera;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
public class TestJsonClient {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		ArrayList<HashMap<String, String>>mylist = new ArrayList<HashMap<String, String>>();

		//Get the data (see above)
		HttpHost targetHost = new HttpHost("localhost", 8080, "http");
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),new UsernamePasswordCredentials("test@liferay.com", "test"));
		AuthCache authCache = new BasicAuthCache();
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);
		BasicHttpContext ctx = new BasicHttpContext();
		//ctx.setAttribute(ClientContext.AUTH_CACHE,authCache);
		HttpPost post = new HttpPost("/tunnel-web/secure/json");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("serviceClassName", "com.liferay.portal.service.CountryServiceUtil"));
		params.add(new BasicNameValuePair("serviceMethodName", "getCountries"));
		params.add(new BasicNameValuePair("serviceParameters", "[]"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		resp.getEntity().writeTo(System.out);
		httpclient.getConnectionManager().shutdown();
       
	}
	
}