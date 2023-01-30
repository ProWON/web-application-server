package http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
	
	Map<String, String> headers = Maps.newHashMap();
	OutputStream out;
	
	public HttpResponse(OutputStream os) {
		out = os;
	}


	public void responseHeader(DataOutputStream dos) {
		
	}


	public void forward(String url) throws IOException  {
		DataOutputStream dos = new DataOutputStream(out);
		byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		responseBody(dos, body);
		
	}
	
	
    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    public void addHeader(String key, String value) {
    	headers.put(key, value);
    }


	public void sendRedirect(String url) {
		headers.
		
	}
}
