package gov.cdc.izgateway.transformation;

import ca.uhn.hl7v2.model.Message;
import gov.cdc.izgateway.transformation.services.ProcessorService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class RestListener {

    @Autowired
    ProcessorService processorService;

    @GetMapping("/transform")
    public String transform() {
        return "Hello from TransformationController";
    }

    @PutMapping("/transform")
    public String transform(@RequestBody String incomingMessage) {
        System.out.println("incomingMessage: \n" + incomingMessage);
        try {

            Message ackNak = processorService.process(incomingMessage);

            return ackNak.encode();
        }
        catch ( Exception e) {
            e.printStackTrace();
            log.warning(e.getMessage());
            return e.getMessage();
        }
    }

}
