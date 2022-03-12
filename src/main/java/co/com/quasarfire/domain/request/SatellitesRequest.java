package co.com.quasarfire.domain.request;

import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ApiModel(description = "Información necesaria para recibir mensaje de las naves")
public class SatellitesRequest {

    @Builder.Default
    private List<SatelliteDetail> satellites = new ArrayList<>();

}
