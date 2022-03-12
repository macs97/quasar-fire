package co.com.quasarfire.facade.impl;

import co.com.quasarfire.config.aws.AmazonConfig;
import co.com.quasarfire.facade.interfaces.GetPositionFacade;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GetPositionFacadeImpl implements GetPositionFacade {

    @Value("${cloud.aws.lambda.quasar-fire-function-name}")
    private String functionName;
    private Gson gson;
    private AWSLambda awsLambda;
    private final AmazonConfig amazonConfig;

    @Autowired
    public GetPositionFacadeImpl(AmazonConfig amazonConfig) {
        this.amazonConfig = amazonConfig;
    }

    @PostConstruct
    public void initialize() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        gson = builder.create();
        awsLambda = this.amazonConfig.config();
    }

    @Override
    public List<String> getPosition(List<Float> distances) {
        try {
            InvokeRequest invokeRequest = new InvokeRequest().withFunctionName(functionName)
                .withPayload(this.gson.toJson(distances));
            InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
            return Arrays.stream(new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8).split(","))
                .map(this::cleanResponse).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of("0", "0");
        }
    }

    private String cleanResponse(String response) {
        return response.replace("[", "").replace("]", "");
    }
}
