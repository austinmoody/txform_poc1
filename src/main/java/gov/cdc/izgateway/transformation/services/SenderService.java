package gov.cdc.izgateway.transformation.services;

import ca.uhn.hl7v2.model.Message;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {
    /*
    Send message to configured downstream receiver

    Destination would be pulled from running TxForm configuration
     */

    @Autowired
    private ProducerTemplate producerTemplate;
    public void send(Message message) throws Exception {
        String mllpEndpoint = "mllp://localhost:6661";
        producerTemplate.sendBody(mllpEndpoint, message.encode());
    }
}
