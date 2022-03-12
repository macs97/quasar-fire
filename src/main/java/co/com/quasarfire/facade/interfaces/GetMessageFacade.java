package co.com.quasarfire.facade.interfaces;

import java.util.List;
import java.util.Map;

public interface GetMessageFacade {

    String getMessage(Map<Integer, List<String>> messagesBySatellite);
}
