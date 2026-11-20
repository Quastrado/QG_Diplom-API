package models.classes;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltersResponseModel {
    private List<FilterItemModel> filters;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilterItemModel {
        private String key;
        private String name;
        private SupportsModel supports;
        private List<FilterValueModel> values;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SupportsModel {
        private Boolean mode;
        private Boolean union;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilterValueModel {
        private String id;
        private String value;
        private String name;
        private Boolean selected;
        private Object relations;
    }
}
