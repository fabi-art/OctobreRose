
//package com.example.grace.exceptions;
//
//public class CustomException extends RuntimeException {
//    private String errorType;
//
//    public CustomException(String errorType, String message) {
//        super(message);
//        this.errorType = errorType;
//    }
//
//    public String getErrorType() {
//        return errorType;
//    }
//}

package com.example.grace.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final String code; // Utiliser 'code' pour être cohérent avec le reste du code

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

