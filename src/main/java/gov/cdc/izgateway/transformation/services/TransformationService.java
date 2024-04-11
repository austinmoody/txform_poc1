package gov.cdc.izgateway.transformation.services;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import gov.cdc.izgateway.transformation.chains.Hl7TransformerChain;
import gov.cdc.izgateway.transformation.transformers.FieldCopyTransformer;
import gov.cdc.izgateway.transformation.transformers.FieldMoverTransformer;
import org.springframework.stereotype.Service;

@Service
public class TransformationService {
    /*
        * getTransformationChain(message);
        * loop over each chain
        * return message
        *
     */
    public Message transform(Message message) throws HL7Exception {
        Hl7TransformerChain hl7TransformerChain = new Hl7TransformerChain();

        hl7TransformerChain.addTransformer(new FieldCopyTransformer());
        hl7TransformerChain.addTransformer(new FieldMoverTransformer());

        hl7TransformerChain.transform(message);

        return message;
    }
}
