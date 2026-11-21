package models.persona;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaResponseModel {
    private String id;
    private String name;
    private String image;
    private Boolean disabled;
}
