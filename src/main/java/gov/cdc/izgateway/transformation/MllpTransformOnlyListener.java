package gov.cdc.izgateway.transformation;

// TODO - move to gov.cdc.izgateway.listeners ??

import ca.uhn.hl7v2.model.Message;
import gov.cdc.izgateway.transformation.services.ProcessService;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mllp.MllpComponent;
import org.apache.camel.component.mllp.MllpConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ca.uhn.hl7v2.AcknowledgmentCode.AA;


@Configuration
public class MllpTransformOnlyListener {
    private static final String MLLP_TRANSFORM_URI = "mllp://localhost:7771";
    private static final String MLLP_TRANSFORMSEND_URI = "mllp://localhost:7772";

    @Autowired
    ProcessService processService;

    @Bean
    public MllpComponent mllpTransformComponent() {
        MllpComponent mllpComponent = new MllpComponent();
        MllpConfiguration mllpConfiguration = new MllpConfiguration();
        mllpConfiguration.setCharsetName("ISO-8859-1");
        mllpComponent.setConfiguration(mllpConfiguration);
        return mllpComponent;
    }

    // Functon takes exchange as parameter to replace code inside process.
    @Bean
    public RouteBuilder mllpTransformRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(MLLP_TRANSFORM_URI)
                        .routeId("mllpRouteTransform")

                        .process(exchange -> {
                            String hl7Message = exchange.getIn().getBody(String.class);
                            Message response = processService.transformOnly(hl7Message);
                            String encodedResponse = response.encode();
                            exchange.setProperty("CamelMllpAcknowledgement", encodedResponse);
                        })
                        .log("${body}");
            }
        };
    }
}
