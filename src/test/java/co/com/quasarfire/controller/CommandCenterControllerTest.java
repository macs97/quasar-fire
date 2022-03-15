package co.com.quasarfire.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.com.quasarfire.business.interfaces.CommandCenterBusiness;
import co.com.quasarfire.domain.request.SatelliteDetail;
import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;
import co.com.quasarfire.domain.response.Coordinates;
import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CommandCenterController.class)
class CommandCenterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandCenterBusiness commandCenterBusiness;

    @Test
    void getSpaceshipPositionAndMessageSuccessTest() throws Exception {
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().name("kenobi").distance(100).message(List.of("este", "", "", "mensaje", ""))
                    .build(),
                SatelliteDetail.builder().name("skywalker").distance(115.5f).message(List.of("", "es", "", "", "secreto"))
                    .build(),
                SatelliteDetail.builder().name("sato").distance(142.7f).message(List.of("este", "", "un", "", "")).build()))
            .build();

        CommandCenterResponse commandCenterResponse = CommandCenterResponse.builder()
            .position(Coordinates.builder().x(-487.29f).y(1557.01f).build()).message("este es un mensaje secreto")
            .build();

        Mockito.when(this.commandCenterBusiness.getSpaceshipPositionAndMessage(Mockito.any(SatellitesRequest.class)))
            .thenReturn(commandCenterResponse);

        this.mockMvc.perform(
                post("/topsecret").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(satellitesRequest))
                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(new Gson().toJson(commandCenterResponse)));
    }

    @Test
    void getSpaceshipPositionAndMessageFailedTest() throws Exception {
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().build();

        Mockito.when(this.commandCenterBusiness.getSpaceshipPositionAndMessage(Mockito.any(SatellitesRequest.class)))
            .thenThrow(NullPointerException.class);

        this.mockMvc.perform(
            post("/topsecret").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(satellitesRequest))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void postPositionAndMessageBySpaceshipSuccessTest() throws Exception {
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().distance(100).message(List.of("este", "", "", "mensaje", ""))
                    .build()))
            .build();

        CommandCenterResponse commandCenterResponse = CommandCenterResponse.builder()
            .position(Coordinates.builder().x(-475f).y(1550).build()).message("este mensaje")
            .build();

        Mockito.when(this.commandCenterBusiness.getSpaceshipPositionAndMessage(Mockito.any(SatellitesRequest.class)))
            .thenReturn(commandCenterResponse);

        this.mockMvc.perform(
                post("/topsecret_split/kenobi").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(satellitesRequest))
                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(new Gson().toJson(commandCenterResponse)));
    }

    @Test
    void getPositionAndMessageBySpaceshipSuccessTest() throws Exception {
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().distance(100).message(List.of("este", "", "", "mensaje", ""))
                    .build()))
            .build();

        CommandCenterResponse commandCenterResponse = CommandCenterResponse.builder()
            .position(Coordinates.builder().x(-475f).y(1550).build()).message("este mensaje")
            .build();

        Mockito.when(this.commandCenterBusiness.getSpaceshipPositionAndMessage(Mockito.any(SatellitesRequest.class)))
            .thenReturn(commandCenterResponse);

        this.mockMvc.perform(
                get("/topsecret_split/kenobi").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(satellitesRequest))
                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(new Gson().toJson(commandCenterResponse)));
    }

    @Test
    void getPositionAndMessageBySpaceshipCantObtainInformationSuccessTest() throws Exception {
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().distance(100).message(List.of("", "", "", "", ""))
                    .build()))
            .build();

        CommandCenterResponse commandCenterResponse = CommandCenterResponse.builder()
            .position(Coordinates.builder().x(-475f).y(1550).build()).message("")
            .build();

        Mockito.when(this.commandCenterBusiness.getSpaceshipPositionAndMessage(Mockito.any(SatellitesRequest.class)))
            .thenReturn(commandCenterResponse);


        this.mockMvc.perform(
                get("/topsecret_split/kenobi").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(satellitesRequest))
                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(new Gson().toJson(commandCenterResponse)));
    }
}
