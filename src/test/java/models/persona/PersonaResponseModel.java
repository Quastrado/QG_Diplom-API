package models.persona;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaResponseModel {
    private String id;
    private String name;
    private String image;
    private Boolean disabled;
    private String username;
    private String createdAt;
}
