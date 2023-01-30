package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import util.HttpRequestUtils;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	
	InputStream in;
	Map<String, String> headers = Maps.newHashMap();
	Map<String, String> params = Maps.newHashMap();
	
	HttpRequest(InputStream in){
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = br.readLine();
			
			if(line == null) return;

        	String[] tokens = line.split(" ");
        	headers.put("method", tokens[0]);
        	tokens = tokens[1].split("\\?");
        	headers.put("path", tokens[0] );
        	
        	int index = tokens[1].indexOf("?");
        	String queryString = tokens[1].substring(index+1);
        	
        	params = HttpRequestUtils.parseQueryString(queryString);
        	
        	while(!"".equals(line) || line==null) {
        		line = br.readLine();
        		if (line == null || line.isEmpty()) break;
        		String[] token = line.split(":");
        		headers.put(token[0], token[1].trim());
        	}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	
	}
	
	public String getMethod() {
		return headers.get("method");
	}
	
	public String getPath() {
		return headers.get("path");
	}
	
	public String getHeader(String key) {
		return headers.get(key);
	}
	
	public String getParameter(String key) {
		return params.get(key);
	}
}
