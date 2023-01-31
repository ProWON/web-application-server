package http;

public enum HttpMethod {
	GET,
	POST;
	
	public Boolean isPost() {
		return this == POST;
	}
}
