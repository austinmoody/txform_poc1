package gov.cdc.izgateway.transformation.listeners;

/*
Was jsut trying to get a MLLP listener in place w/o overhead of Camel.
 */

//
//import ca.uhn.hl7v2.DefaultHapiContext;
//import ca.uhn.hl7v2.HL7Exception;
//import ca.uhn.hl7v2.HapiContext;
//import ca.uhn.hl7v2.app.Connection;
//import ca.uhn.hl7v2.app.HL7Service;
//import ca.uhn.hl7v2.app.Initiator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MllpListenerTransform {
//
//    @Bean
//    public HL7Service MllpListenerTransform() throws HL7Exception {
//        try {
//            HapiContext context = new DefaultHapiContext();
//            HL7Service server = context.newServer(7671, false);
//            server.startAndWait();
//
//            return server;
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
