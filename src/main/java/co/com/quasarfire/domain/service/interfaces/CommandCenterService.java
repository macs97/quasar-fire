package co.com.quasarfire.domain.service.interfaces;

import co.com.quasarfire.domain.response.Coordinates;
import java.util.List;
import java.util.Map;

public interface CommandCenterService {

    Coordinates getLocation(List<Float> distances);

    String getMessage(Map<Integer, List<String>> messagesBySatellite);

}
