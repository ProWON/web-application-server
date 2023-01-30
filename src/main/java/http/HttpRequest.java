package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import util.HttpRequestUtils;
import util.IOUtils;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	
	private String method;
	private String path;
	private Map<String, String> headers = Maps.newHashMap();
	private Map<String, String> params = Maps.newHashMap();
	
	HttpRequest(InputStream in){
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = br.readLine();
			
			if(line == null) return;

			processRequestLine(line);
        	
			line = br.readLine();
        	while(!"".equals(line) || line==null) {
        		log.debug("header : {}", line);
        		line = br.readLine();
        		String[] token = line.split(":");
        		headers.put(token[0], token[1].trim());
        		line = br.readLine();
        	}
        	
        	if ("POST".equals(method)) {
        		String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
        		params = HttpRequestUtils.parseQueryString(body);
        	}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	
	}
	
	private void processRequestLine(String requestLine) {
		log.debug("request line : ", requestLine);
		String[] tokens = requestLine.split(" ");
		method = tokens[0];
		
		if ("POST".equals(method)) {
			path = tokens[1];
			return;
		}
		
		int index = tokens[1].indexOf("?");
		if (index == -1) {
			path = tokens[1];
		} else {
			path = tokens[1].substring(0, index);
			params = HttpRequestUtils.parseQueryString(tokens[1].substring(index+1));
		}
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getHeader(String key) {
		return headers.get(key);
	}
	
	public String getParameter(String key) {
		return params.get(key);
	}
	
}
