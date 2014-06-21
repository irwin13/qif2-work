package id.co.quadras.qif.ui;

import com.irwin13.winwork.basic.WinWorkConstants;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author irwin Timestamp : 01/02/13 15:05
 */
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    public static final String HTTP_PROTOCOL = "http://";

    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_DELETE = "DELETE";


    /**
     * Connection timeout, time given to established HTTP connection
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 30; // in milliseconds, so it means 30 seconds

    /**
     * Socket timeout, time to wait for data transfer to be completed.
     * In this case, the HTTP connection already established, this is the timeout for data transfer.
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 1000 * 300; // in milliseconds, so it means 300 seconds / 5 minutes

    /**
     * Method to invoke HTTP GET method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String get(String url, String nullValue, Map<String, String> httpHeader) throws IOException {
        return get(url, nullValue, httpHeader,
                DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    public String get(String url, String nullValue) throws IOException {
        return get(url, nullValue, null,
                DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    public String get(String url) throws IOException {
        return get(url, null, null,
                DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * Method to invoke HTTP GET method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param connectionTimeout timeout value in millisecond, 0 mean no timeout
     * @param socketTimeout timeout value in millisecond, 0 mean no timeout
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String get(String url, String nullValue, Map<String, String> httpHeader,
                      int connectionTimeout, int socketTimeout) throws IOException {

        String result = nullValue;

        DefaultHttpClient httpClient = getDefault(connectionTimeout, socketTimeout);

        HttpGet httpGet = new HttpGet(url);
        setHeader(httpGet, httpHeader);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            result = httpClient.execute(httpGet, responseHandler);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        LOGGER.trace("GET Result from URL {} = {}", url, result);
        return result;
    }

    /**
     * Method to invoke HTTP POST method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpParameter parameter supplied to HTTP invoke
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String post(String url, String nullValue, Map<String, String> httpParameter, Map<String, String> httpHeader)
            throws IOException {
        return post(url, nullValue, httpParameter, httpHeader,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String post(String url, String nullValue, Map<String, String> httpParameter)
            throws IOException {
        return post(url, nullValue, httpParameter, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String post(String url, Map<String, String> httpParameter)
            throws IOException {
        return post(url, null, httpParameter, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * Method to invoke HTTP POST method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpParameter parameter supplied to HTTP invoke
     * @param connectionTimeout timeout value in millisecond, 0 mean no timeout
     * @param socketTimeout timeout value in millisecond, 0 mean no timeout
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String post(String url, String nullValue, Map<String, String> httpParameter, Map<String, String> httpHeader,
                       int connectionTimeout, int socketTimeout)
            throws IOException {

        String result = nullValue;
        DefaultHttpClient httpClient = getDefault(connectionTimeout, socketTimeout);

        HttpPost httpPost = new HttpPost(url);
        setHeader(httpPost, httpHeader);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        List<NameValuePair> nameValuePairList = createNameValuePair(httpParameter);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, WinWorkConstants.UTF_8));
            result = httpClient.execute(httpPost, responseHandler);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        LOGGER.trace("POST Result from URL {} = {}", url, result);
        return result;
    }

    /**
     * Method to invoke HTTP POST method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpBody string to be placed on HTTP body
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String post(String url, String nullValue, String httpBody, Map<String, String> httpHeader)
            throws IOException {
        return post(url, nullValue, httpBody, httpHeader,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String post(String url, String nullValue, String httpBody)
            throws IOException {
        return post(url, nullValue, httpBody, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String post(String url, String httpBody)
            throws IOException {
        return post(url, null, httpBody, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * Method to invoke HTTP POST method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpBody string to be placed on HTTP body
     * @param connectionTimeout timeout value in millisecond, 0 mean no timeout
     * @param socketTimeout timeout value in millisecond, 0 mean no timeout
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String post(String url, String nullValue, String httpBody, Map<String, String> httpHeader,
                       int connectionTimeout, int socketTimeout)
            throws IOException {

        String result = nullValue;
        DefaultHttpClient httpClient = getDefault(connectionTimeout, socketTimeout);

        HttpPost httpPost = new HttpPost(url);
        setHeader(httpPost, httpHeader);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            httpPost.setEntity(new StringEntity(httpBody));
            result = httpClient.execute(httpPost, responseHandler);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        LOGGER.trace("POST Result from URL {} = {}", url, result);
        return result;
    }

    /**
     * Method to invoke HTTP PUT method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpParameter parameter supplied to HTTP invoke
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String put(String url, String nullValue, Map<String, String> httpParameter, Map<String, String> httpHeader)
            throws IOException {

        return put(url, nullValue, httpParameter, httpHeader,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String put(String url, String nullValue, Map<String, String> httpParameter)
            throws IOException {

        return put(url, nullValue, httpParameter, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String put(String url, Map<String, String> httpParameter)
            throws IOException {

        return put(url, null, httpParameter, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * Method to invoke HTTP PUT method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpParameter parameter supplied to HTTP invoke
     * @param connectionTimeout timeout value in millisecond, 0 mean no timeout
     * @param socketTimeout timeout value in millisecond, 0 mean no timeout
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String put(String url, String nullValue, Map<String, String> httpParameter, Map<String, String> httpHeader,
                       int connectionTimeout, int socketTimeout)
            throws IOException {

        String result = nullValue;
        DefaultHttpClient httpClient = getDefault(connectionTimeout, socketTimeout);

        HttpPut httpPut = new HttpPut(url);
        setHeader(httpPut, httpHeader);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        List<NameValuePair> nameValuePairList = createNameValuePair(httpParameter);

        try {
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairList, WinWorkConstants.UTF_8));
            result = httpClient.execute(httpPut, responseHandler);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        LOGGER.trace("PUT Result from URL {} = {}", url, result);
        return result;
    }

    /**
     * Method to invoke HTTP PUT method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpBody string to be placed on HTTP body
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String put(String url, String nullValue, String httpBody, Map<String, String> httpHeader)
            throws IOException {
        return put(url, nullValue, httpBody, httpHeader,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String put(String url, String nullValue, String httpBody)
            throws IOException {
        return put(url, nullValue, httpBody, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    public String put(String url, String httpBody)
            throws IOException {
        return put(url, null, httpBody, null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * Method to invoke HTTP PUT method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param httpBody string to be placed on HTTP body
     * @param connectionTimeout timeout value in millisecond, 0 mean no timeout
     * @param socketTimeout timeout value in millisecond, 0 mean no timeout
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String put(String url, String nullValue, String httpBody, Map<String, String> httpHeader,
                       int connectionTimeout, int socketTimeout)
            throws IOException {

        String result = nullValue;
        DefaultHttpClient httpClient = getDefault(connectionTimeout, socketTimeout);

        HttpPut httpPut = new HttpPut(url);
        setHeader(httpPut, httpHeader);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            httpPut.setEntity(new StringEntity(httpBody));
            result = httpClient.execute(httpPut, responseHandler);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        LOGGER.trace("POST Result from URL {} = {}", url, result);
        return result;
    }

    /**
     * Method to invoke HTTP DELETE method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String delete(String url, String nullValue, Map<String, String> httpHeader) throws IOException {
        return delete(url, nullValue, httpHeader,
                DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    public String delete(String url, String nullValue) throws IOException {
        return delete(url, nullValue, null,
                DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    public String delete(String url) throws IOException {
        return delete(url, null, null,
                DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * Method to invoke HTTP DELETE method.
     * @param url url to be invoked
     * @param nullValue return value if the result is null or caught by exception
     * @param connectionTimeout timeout value in millisecond, 0 mean no timeout
     * @param socketTimeout timeout value in millisecond, 0 mean no timeout
     * @return String response from HTTP invoke
     * @throws java.io.IOException
     */
    public String delete(String url, String nullValue, Map<String, String> httpHeader,
                      int connectionTimeout, int socketTimeout) throws IOException {

        String result = nullValue;

        DefaultHttpClient httpClient = getDefault(connectionTimeout, socketTimeout);

        HttpDelete httpDelete = new HttpDelete(url);
        setHeader(httpDelete, httpHeader);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            result = httpClient.execute(httpDelete, responseHandler);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        LOGGER.trace("DELETE Result from URL {} = {}", url, result);
        return result;
    }

    /**
     * Construct HTTP URL from separate string, for example http://localhost:8080/app/mypath
     * localhost is the host, 8080 will is th port, hbm.app is the context and mypath is the serlvetPath
     * @param host host destination
     * @param port port destination
     * @param context context destination
     * @param servletPath serlvetPath destination
     * @return qualified HTTP URL
     */
    public String buildHttpUrl(String host, int port, String context, String servletPath) {
        return HTTP_PROTOCOL + host + ':' + port + '/' + context + '/' + servletPath;
    }

    private List<NameValuePair> createNameValuePair(Map<String, String> httpParameter) {
        List<NameValuePair> nameValuePairList = new LinkedList<NameValuePair>();
        if (httpParameter != null) {
            for (Map.Entry<String, String> entry : httpParameter.entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return nameValuePairList;
    }

    private DefaultHttpClient getDefault(int connectionTimeout, int socketTimeout) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);

        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
        return httpClient;
    }

    private void setHeader(HttpRequestBase httpRequestBase, Map<String, String> httpHeader) {
        if (httpHeader != null) {
            for (Map.Entry<String, String> entry : httpHeader.entrySet()) {
                httpRequestBase.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public InputStream download(String url, Map<String, String> httpHeader) throws IOException {

        DefaultHttpClient httpClient = getDefault(0, 0);

        HttpGet httpGet = new HttpGet(url);
        setHeader(httpGet, httpHeader);

        BufferedInputStream bufferInput = null;
        try {
            bufferInput = new BufferedInputStream(httpClient.execute(httpGet).getEntity().getContent());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return bufferInput;
    }
    
    public void downloadWithTemp(String url, OutputStream outputStream) throws IOException {
    	String tempDir = System.getProperty("java.io.tmpdir");
    	long current = System.currentTimeMillis();
    	File file = new File(tempDir + File.separator + String.valueOf(current));
    	FileOutputStream fos = new FileOutputStream(file);
    	
    	try {
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());			
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			IOUtils.copy(new FileInputStream(file), outputStream);
    	} finally {
    		if (fos != null) {
    			fos.close();
    			fos.flush();
    		}
    		
    		if (file.exists()) {
    			file.delete();
    		}
    	}
		
    }
    
}
