package javaservice.mockhackthon.exeption;



import javaservice.mockhackthon.model.dto.response.ApiDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Không tìm thấy tài nguyên
    @ExceptionHandler(javaservice.mockhackthon.exeption.ResourceNotFoundException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleResourceNotFound(javaservice.mockhackthon.exeption.ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiDataResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    // 409 - Dữ liệu bị trùng
    @ExceptionHandler(javaservice.mockhackthon.exeption.DuplicateResourceException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleDuplicate(javaservice.mockhackthon.exeption.DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiDataResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .status(HttpStatus.CONFLICT)
                        .build());
    }

    // 400 - Lỗi validation: trả về map chi tiết từng field lỗi
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiDataResponse.<Map<String, String>>builder()
                        .success(false)
                        .message("Dữ liệu đầu vào không hợp lệ")
                        .data(errors)
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }

    // 500 - Lỗi hệ thống không xác định
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiDataResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiDataResponse.<Void>builder()
                        .success(false)
                        .message("Lỗi hệ thống: " + ex.getMessage())
                        .data(null)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
    }
}
