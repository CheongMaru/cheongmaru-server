package com.cheongmaru.global.api;

import com.cheongmaru.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResult<T> {

    private final boolean success;
    private final T data;
    private final ApiError error;

    private ApiResult(boolean success, T data, ApiError error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, data, null);
    }

    public static <T> ApiResult<T> error(HttpStatus status, String message) {
        return new ApiResult<>(false, null, new ApiError(status.value(), message));
    }

    public static <T> ApiResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getStatus(), errorCode.getMessage());
    }
}
