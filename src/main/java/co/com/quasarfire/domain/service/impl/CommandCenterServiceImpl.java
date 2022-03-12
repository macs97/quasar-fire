package co.com.quasarfire.domain.service.impl;

import co.com.quasarfire.domain.response.Coordinates;
import co.com.quasarfire.domain.service.interfaces.CommandCenterService;
import co.com.quasarfire.facade.interfaces.GetMessageFacade;
import co.com.quasarfire.facade.interfaces.GetPositionFacade;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CommandCenterServiceImpl implements CommandCenterService {

    private final GetPositionFacade getPositionFacade;
    private final GetMessageFacade getMessageFacade;

    public CommandCenterServiceImpl(GetPositionFacade getPositionFacade,
        GetMessageFacade getMessageFacade) {
        this.getPositionFacade = getPositionFacade;
        this.getMessageFacade = getMessageFacade;
    }

    @Override
    public Coordinates getLocation(List<Float> distances) {
        List<String> coordinates = this.getPositionFacade.getPosition(distances);
        return Coordinates.builder().x(Float.parseFloat(coordinates.get(0))).y(Float.parseFloat(coordinates.get(1))).build();
    }

    @Override
    public String getMessage(Map<Integer, List<String>> messagesBySatellite) {
        return this.getMessageFacade.getMessage(messagesBySatellite);
    }

}
