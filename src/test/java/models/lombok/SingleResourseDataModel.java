package models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)

public class SingleResourseDataModel {
    String id, name, year, color, pantone_value, url;
}