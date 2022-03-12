package co.com.quasarfire.business.interfaces;

import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;

public interface CommandCenterBusiness {

    CommandCenterResponse getSpaceshipPositionAndMessage(SatellitesRequest satellitesRequest);
}
