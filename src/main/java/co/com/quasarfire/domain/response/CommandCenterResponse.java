package co.com.quasarfire.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommandCenterResponse {

    private Coordinates position;
    @Setter
    private String message;
}
