package gov.cdc.izgateway.transformation.services;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;

@Log
@Service
public class ProcessService {

    /*
    Handle processing of inbound message (transformation, sending)
     */

    @Autowired
    TransformationService transformationService;

    @Autowired
    SenderService senderService;

    @Autowired
    Hl7ParserService parserService;

    /*
    This ultimately should transform the message, send it downstream, and respond to the sender with the response
    received from the downstream system?
     */
    public Message transformAndSend(String inboundMessage) throws Exception {
        Message message = null;

        try {
            message = parserService.parse(inboundMessage);
            Message transformedMessage = transformationService.transform(message);

            return senderService.send(transformedMessage);

        } catch (Exception e) {
            log.warning(e.getMessage());

            if (message != null) {
                HL7Exception hl7Exception = new HL7Exception(e.getMessage());
                return message.generateACK(AcknowledgmentCode.AE, hl7Exception);
            } else {
                throw e;
            }
        }
    }

    /*
    This will only transform the message and respond to the caller with the transformed message
     */
    public Message transformOnly(String inboundMessage) throws Exception {
        Message message = parserService.parse(inboundMessage);
        return transformationService.transform(message);
    }
}
