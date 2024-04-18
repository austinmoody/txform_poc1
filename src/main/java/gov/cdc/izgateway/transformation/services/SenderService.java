package gov.cdc.izgateway.transformation.services;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Log
public class SenderService {

    @Autowired
    Hl7ParserService parserService;

    /*
    Send message to configured downstream receiver

    Destination would be pulled from running TxForm configuration

    Destination ultimate would NOT be MLLP I do not believe.  Would be a SOAP call to IZG.
     */

    public Message send(Message message) throws Exception {
        String host = "localhost";
        int port = 6661;

        HapiContext context = new DefaultHapiContext();

        try {

            Connection connection = context.newClient(host, port, false);
            Initiator initiator = connection.getInitiator();
            //initiator.setTimeout(30000, TimeUnit.MILLISECONDS); # in case we need to increase timeout to downstream system
            return initiator.sendAndReceive(message);
        } catch (HL7Exception e) {
            log.warning("HL7 Exception: " + e.getMessage());
            throw e;
        } catch (IOException e) {
            log.warning("IOException: " + e.getMessage());
            throw e;
        } finally {
            context.close();
        }
    }
}
