package co.com.quasarfire.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SatelliteDetail {

    @JsonInclude(Include.NON_NULL)
    private String name;
    @Builder.Default
    private float distance = 0;
    private List<String> message;
}
