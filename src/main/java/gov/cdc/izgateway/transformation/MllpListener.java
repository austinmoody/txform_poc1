package gov.cdc.izgateway.transformation;

// TODO - move to gov.cdc.izgateway.listeners ??

import ca.uhn.hl7v2.model.Message;
import gov.cdc.izgateway.transformation.services.ProcessorService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mllp.MllpComponent;
import org.apache.camel.component.mllp.MllpConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MllpListener {
    private static final String MLLP_CONSUMER_URI = "mllp://localhost:8888";

    @Autowired
    ProcessorService processorService;

    @Bean
    public MllpComponent mllp() {
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
                from(MLLP_CONSUMER_URI)
                        .routeId("mllpRoute")
                        .process(exchange -> {
                            String hl7Message = exchange.getIn().getBody(String.class);
                            Message ackNak = processorService.process(hl7Message);

                            String encodedResponse = ackNak.encode();
                            exchange.getIn().setBody(encodedResponse);
                        })
                        .log("${body}");
            }
        };
    }
}
