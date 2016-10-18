package com.std.framework.container.c;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;

@SuppressWarnings("serial")
public class ControllerException extends RuntimeException {

	private Log logger = LogFactory.getLogger();

	public ControllerException() {
		super();
		logger.error("");
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException throwCtrlException(Integer errorCode) {
		String message = handleErrorMessage(errorCode);
		return new ControllerException(message);
	}

	public ControllerException throwCtrlException(Integer errorCode, Throwable cause) {
		String message = handleErrorMessage(errorCode);
		return new ControllerException(message, cause);
	}

	private String handleErrorMessage(Integer errorCode) {
		String errorMessage = "Unknow Controller Exception";
		switch (errorCode) {
		case 1:
			errorMessage = "";
			break;
		case 2:
			errorMessage = "";
			break;
		case 3:
			errorMessage = "";
			break;
		case 4:
			errorMessage = "";
			break;
		default:
			break;
		}
		return errorMessage;
	}

}
