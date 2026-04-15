package in.reinventing.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
        private Integer id;

        @NotBlank(message = "Required Lat Input!")
        private String lat;

        @NotBlank(message = "Required Long Input!")
        private String lang;

        @NotBlank(message = "Location Required!")
        private String location;

        @NotBlank(message = "Location Required!")
        private String timeZone;

        private String userId;
}
