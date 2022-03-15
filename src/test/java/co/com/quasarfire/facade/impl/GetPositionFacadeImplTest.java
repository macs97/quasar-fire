package co.com.quasarfire.facade.impl;

import co.com.quasarfire.config.aws.AmazonConfig;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeResult;
import java.nio.ByteBuffer;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class GetPositionFacadeImplTest {

    @MockBean
    private AmazonConfig amazonConfig;

    private AWSLambda awsLambda;

    @Autowired
    private GetPositionFacadeImpl getPositionFacade;

    @BeforeEach
    void setup() {
        awsLambda = Mockito.mock(AWSLambda.class);
    }

    @Test
    void getPositionSuccess() {
        List<Float> distances = List.of(100f, 0f, 0f);
        List<String> positionsExpected = List.of("-475", "1550");
        String positionsInvokeExpected = "[-475,1550]";
        InvokeResult invokeResultExpected = new InvokeResult();
        invokeResultExpected.setPayload(ByteBuffer.wrap(positionsInvokeExpected.getBytes()));
        Mockito.when(amazonConfig.config()).thenReturn(awsLambda);

        Mockito.when(awsLambda.invoke(Mockito.any())).thenReturn(invokeResultExpected);

        this.getPositionFacade.initialize();
        List<String> positions = this.getPositionFacade.getPosition(distances);

        Assertions.assertEquals(positionsExpected.get(0), positions.get(0));
        Assertions.assertEquals(positionsExpected.get(1), positions.get(1));
    }

    @Test
    void getPositionFailed() {
        List<Float> distances = List.of(100f, 0f, 0f);
        List<String> positionsExpected = List.of("0", "0");
        Mockito.when(amazonConfig.config()).thenReturn(awsLambda);

        Mockito.when(awsLambda.invoke(Mockito.any())).thenReturn(null);

        this.getPositionFacade.initialize();
        List<String> positions = this.getPositionFacade.getPosition(distances);

        Assertions.assertEquals(positionsExpected.get(0), positions.get(0));
        Assertions.assertEquals(positionsExpected.get(1), positions.get(1));
    }
}
