package fr.asterox.UserManagement.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserException extends RuntimeException {

	public NoUserException(String msg) {
		super(msg);
	}

}
