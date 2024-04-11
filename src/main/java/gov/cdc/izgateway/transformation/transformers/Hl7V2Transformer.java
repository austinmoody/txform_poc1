package gov.cdc.izgateway.transformation.transformers;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;

public interface Hl7V2Transformer {
    void transform(Message message) throws HL7Exception;
    void setNextTransformer(Hl7V2Transformer nextTransformer);
    Hl7V2Transformer getNextTransformer();
}
