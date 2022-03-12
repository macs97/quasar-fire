package co.com.quasarfire.domain.request;

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

    private String name;
    private float distance;
    private List<String> message;
}
