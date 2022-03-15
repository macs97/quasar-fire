package co.com.quasarfire.controller;

import co.com.quasarfire.business.interfaces.CommandCenterBusiness;
import co.com.quasarfire.domain.request.SatelliteDetail;
import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommandCenterController {

    private final CommandCenterBusiness commandCenter;

    @Autowired
    public CommandCenterController(CommandCenterBusiness commandCenter) {
        this.commandCenter = commandCenter;
    }

    @PostMapping("/topsecret")
    @ApiOperation(value = "Encuentra la posicion de la nave respecto la distancia de los 3 satelites y devuelve mensaje.", httpMethod = "POST", response = CommandCenterResponse.class)
    public ResponseEntity<CommandCenterResponse> getSpaceshipPositionAndMessage(
        @RequestBody SatellitesRequest satellitesRequest) {
        log.info("Start TOPSECRET");
        return new ResponseEntity<>(this.commandCenter.getSpaceshipPositionAndMessage(satellitesRequest),
            HttpStatus.OK);
    }

    @GetMapping("/topsecret_split/{name}")
    @ApiOperation(value = "Encuentra la posicion de la nave la distancia de 1 satelite y devuelve mensaje por metodo Get. Sino tiene mensaje devuelve 'No hay suficiente informacion'", httpMethod = "GET", response = CommandCenterResponse.class)
    public ResponseEntity<CommandCenterResponse> getPositionAndMessageBySpaceship(@PathVariable String name,
        @RequestBody SatelliteDetail satelliteDetail) {
        log.info("Start TOPSECRET_SPLIT GET");
        CommandCenterResponse commandCenterResponse = this.commandCenter.getSpaceshipPositionAndMessage(
            SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().name(name).distance(satelliteDetail.getDistance())
                    .message(satelliteDetail.getMessage()).build())).build());
        if (commandCenterResponse.getMessage().isEmpty()) {
            commandCenterResponse.setMessage("No hay suficiente informacion");
        }
        return new ResponseEntity<>(commandCenterResponse, HttpStatus.OK);

    }

    @PostMapping("/topsecret_split/{name}")
    @ApiOperation(value = "Encuentra la posicion de la nave la distancia de 1 satelite y devuelve mensaje por metodo Post.", httpMethod = "POST", response = CommandCenterResponse.class)
    public ResponseEntity<CommandCenterResponse> postPositionAndMessageBySpaceship(@PathVariable String name,
        @RequestBody SatelliteDetail satelliteDetail) {
        log.info("Start TOPSECRET_SPLIT POST");
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
            SatelliteDetail.builder().name(name).distance(satelliteDetail.getDistance())
                .message(satelliteDetail.getMessage()).build())).build();
        return new ResponseEntity<>(this.commandCenter.getSpaceshipPositionAndMessage(satellitesRequest),
            HttpStatus.OK);
    }

    @GetMapping("health_check")
    @ApiOperation(value = "Metodo para verificar que la aplicacion inicia correctamente", httpMethod = "GET", response = String.class)
    public ResponseEntity<String> healthCheck() {
        log.info("Start HEALTH CHECK");
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
