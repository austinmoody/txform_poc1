package gov.cdc.izgateway.transformation;

import ca.uhn.hl7v2.model.Message;
import gov.cdc.izgateway.transformation.services.ProcessService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mllp.MllpComponent;
import org.apache.camel.component.mllp.MllpConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MllpTransformSendListener {
    private static final String MLLP_TRANSFORMSEND_URI = "mllp://localhost:7772";

    @Autowired
    ProcessService processService;

    @Bean
    public MllpComponent mllpTransformSend() {
        MllpComponent mllpComponent = new MllpComponent();
        MllpConfiguration mllpConfiguration = new MllpConfiguration();
        mllpConfiguration.setCharsetName("ISO-8859-1");
        mllpComponent.setConfiguration(mllpConfiguration);
        return mllpComponent;
    }

    @Bean
    public RouteBuilder mllpRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(MLLP_TRANSFORMSEND_URI)
                        .routeId("mllpRouteTransformSend")
                        .process(exchange -> {
                            String hl7Message = exchange.getIn().getBody(String.class);
                            Message response = processService.transformAndSend(hl7Message);
                            String encodedResponse = response.encode();
                            exchange.setProperty("CamelMllpAcknowledgement", encodedResponse);
                        })
                        .log("${body}");
            }
        };
    }

}
