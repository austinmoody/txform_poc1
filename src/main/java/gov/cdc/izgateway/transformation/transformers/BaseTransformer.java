package gov.cdc.izgateway.transformation.transformers;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;

abstract class BaseTransformer implements Hl7V2Transformer {

    Hl7V2Transformer nextTransformer;

    @Override
    public void setNextTransformer(Hl7V2Transformer nextTransformer) {
        this.nextTransformer = nextTransformer;
    }

    @Override
    public Hl7V2Transformer getNextTransformer() {
        return nextTransformer;
    }

    @Override
    public abstract void transform(Message message) throws HL7Exception;
}
