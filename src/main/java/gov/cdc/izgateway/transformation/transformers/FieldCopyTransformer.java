package gov.cdc.izgateway.transformation.transformers;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.util.Terser;

public class FieldCopyTransformer extends BaseTransformer implements Hl7V2Transformer {

    @Override
    public void transform(Message message) throws HL7Exception {
        Terser terser = new Terser(message);

        String msh3 = terser.get("/.MSH-3");
        terser.set("/.MSH-3", msh3 + "-MODIFIED");

        if (nextTransformer != null) {
            nextTransformer.transform(message);
        }
    }
}
