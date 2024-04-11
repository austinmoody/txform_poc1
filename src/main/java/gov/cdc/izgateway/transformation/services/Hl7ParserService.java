package gov.cdc.izgateway.transformation.services;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
import org.springframework.stereotype.Service;

@Service
public class Hl7ParserService {
    private PipeParser pipeParser;

    public Message parse(String hl7Message) throws HL7Exception {
        return getPipeParser().parse(hl7Message);
    }

    private PipeParser getPipeParser() {
        if ( pipeParser == null ) {
            pipeParser = new PipeParser();
        }

        return pipeParser;
    }
}
