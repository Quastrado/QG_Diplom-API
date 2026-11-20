package models.common;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseModel {
    private Integer status;
    private String error;
    private String message;
}
