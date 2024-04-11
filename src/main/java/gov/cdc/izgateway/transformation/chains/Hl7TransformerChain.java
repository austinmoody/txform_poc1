package gov.cdc.izgateway.transformation.chains;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import gov.cdc.izgateway.transformation.transformers.Hl7V2Transformer;

public class Hl7TransformerChain {
    private Hl7V2Transformer firstTransformer;

    public void addTransformer(Hl7V2Transformer transformer) {
        if (firstTransformer == null) {
            firstTransformer = transformer;
        } else {
            Hl7V2Transformer currentTransformer = firstTransformer;
            while (currentTransformer.getNextTransformer() != null) {
                currentTransformer = currentTransformer.getNextTransformer();
            }
            currentTransformer.setNextTransformer(transformer);
        }
    }

    public void transform(Message message) throws HL7Exception {
        if (firstTransformer != null) {
            firstTransformer.transform(message);
        }
    }
}
