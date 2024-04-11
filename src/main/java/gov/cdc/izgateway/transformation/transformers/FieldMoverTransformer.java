package gov.cdc.izgateway.transformation.transformers;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;

public class FieldMoverTransformer extends BaseTransformer implements Hl7V2Transformer {

    @Override
    public void transform(Message message) throws HL7Exception {

        if (nextTransformer != null) {
            nextTransformer.transform(message);
        }
    }
}
