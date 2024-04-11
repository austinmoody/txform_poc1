package gov.cdc.izgateway.transformation.hl7;

import lombok.Data;

@Data
public class Message {
    private String hl7Message;
    public Message(String hl7Message) {
        this.hl7Message = hl7Message;
    }

    public void append(String segment) {
        this.hl7Message += segment;
    }

    public String toString() {
        return this.hl7Message;
    }
}
