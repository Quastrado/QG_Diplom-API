package models.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassResponseModel {
    private String url;
    private String description;
    private String casterType;
    private Boolean hasSubclasses;
    private NameModel name;
    private HitDiceModel hitDice;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NameModel {
        private String rus;
        private String eng;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HitDiceModel {
        private String label;
        private String value;
        private Integer maxValue;
        private Integer avg;
    }
}
