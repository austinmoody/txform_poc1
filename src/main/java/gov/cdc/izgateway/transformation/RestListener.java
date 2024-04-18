package gov.cdc.izgateway.transformation;

import ca.uhn.hl7v2.model.Message;
import gov.cdc.izgateway.transformation.services.ProcessService;
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
    ProcessService processService;

    @GetMapping("/transform")
    public String transform() {
        return "Hello from TransformationController";
    }

    /*
    User calls to just transform their HL7 message.  What gets returned to them is the transformed
    message, we assume they take this and send to IZG directly.
     */
    @PutMapping("/transform")
    public String transform(@RequestBody String incomingMessage) {
        try {
            Message response = processService.transformOnly(incomingMessage);
            return response.encode();
        }
        catch ( Exception e) {
            e.printStackTrace();
            log.warning(e.getMessage());
            return e.getMessage();
        }
    }

    /*
    User calls with their message.  We transform it and send it downstream.  What gets sent back to the caller
    here is the ACK/NAK from the downstream system.  Or RSP if it is a QBP.
     */
    @PutMapping("/transformAndSend")
    public String transformAndSend(@RequestBody String incomingMessage) {
        try {
            Message response = processService.transformAndSend(incomingMessage);
            return response.encode();
        } catch (Exception e) {
            e.printStackTrace();
            log.warning(e.getMessage());
            return e.getMessage();
        }
    }

}
