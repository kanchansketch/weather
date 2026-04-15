package in.reinventing.user_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    private String path;
    private int status;
    private String error;
    private String success;
    private String data;
    private List<Map<String,Object>> fields;
    private LocalDateTime dateTime;
}
