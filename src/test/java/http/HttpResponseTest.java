package http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Test;

public class HttpResponseTest {
	private String testDirectory = "./src/test/resources/";
	
	@Test
	public void responseForward() throws Exception {
		HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
		response.forward("/index.html");
	}
	
	@Test
	public void responseRedirect() throws Exception {
		HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
		response.sendRedirect("index.html");
	}
	
	private OutputStream createOutputStream(String filename) throws Exception{
		return new FileOutputStream(new File(testDirectory + filename));
	}
}
