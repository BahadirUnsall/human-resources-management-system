package org.musketeers.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    COMMENT_NOT_FOUND(5001, "Comment not found with this id!", HttpStatus.NOT_FOUND),       // 404
    INVALID_TOKEN(5004, "Invalid token!", HttpStatus.UNAUTHORIZED),
    SERVICE_NOT_RESPONDING(5010, "Your request cannot be processed at the moment", HttpStatus.SERVICE_UNAVAILABLE);                      // 401

    private int code;
    private String message;
    private HttpStatus httpStatus;

}
