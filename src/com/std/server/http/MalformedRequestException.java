package com.std.server.http;

/**
 * MalformedRequestException
 * <p>
 * An exception representing a failure to process an incoming HTTP request.
 *
 * @author LUOXIAO
 * @since 1.0
 */
public class MalformedRequestException extends RuntimeException {

	private static final long serialVersionUID = -2390720763190202369L;

	public MalformedRequestException() {
	}

	public MalformedRequestException(String message) {
		super(message);
	}

	public MalformedRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public MalformedRequestException(Throwable cause) {
		super(cause);
	}

	public MalformedRequestException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
