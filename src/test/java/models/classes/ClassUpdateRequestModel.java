package models.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassUpdateRequestModel {
    private String url;
    private NameModel name;
    private String description;
    private Integer hitDice;
    private String casterType;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameModel {
        private String rus;
        private String eng;
    }
}
