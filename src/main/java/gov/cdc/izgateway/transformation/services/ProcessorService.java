package gov.cdc.izgateway.transformation.services;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;

@Log
@Service
public class ProcessorService {

    /*
    Handle processing of inbound message (transformation, sending)
     */

    @Autowired
    TransformationService transformationService;

    @Autowired
    SenderService senderService;

    @Autowired
    Hl7ParserService parserService;

    public Message process(String inboundMessage) throws Exception {

        Message message = null;

        try {
            message = parserService.parse(inboundMessage);
            Message transformedMessage = transformationService.transform(message);

            senderService.send(transformedMessage);

            Message ack = message.generateACK(AcknowledgmentCode.AA, null);

            return ack;

        } catch (Exception e) {
            e.printStackTrace();
            log.warning(e.getMessage());

            if (message != null) {
                HL7Exception hl7Exception = new HL7Exception(e.getMessage());
                Message nak = message.generateACK(AcknowledgmentCode.AE, hl7Exception);
                return nak;
            } else {
                throw e;
            }
        }

    }
}
