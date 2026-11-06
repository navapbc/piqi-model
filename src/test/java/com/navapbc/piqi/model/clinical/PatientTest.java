package com.navapbc.piqi.model.clinical;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PatientTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    // One entry for every required data class, with a few real values.
    private static final String JSON = """
        {
          "demographics": {
            "patientIdentifier": "123",
            "birthSex": { "text": "Female", "codings": [ { "code": "F", "system": "HL7" } ] }
          },
          "coverage": [ { "subscriberIdentifier": "S1" } ]
        }
        """;

    @Test
    void readsPlainStringsIntoSimpleAttributes() throws Exception {
        PiqiPatient patient = MAPPER.readValue(JSON, PiqiPatient.class);

        assertEquals("123", patient.getDemographics().getPatientIdentifier().getValue());
        assertEquals("Female", patient.getDemographics().getBirthSex().getText().getValue());
        assertEquals("F", patient.getDemographics().getBirthSex().getCodings().get(0).getCode().getValue());
        assertEquals("S1", patient.getCoverage().get(0).getSubscriberIdentifier().getValue());
    }

    @Test
    void writesSimpleAttributesBackAsPlainStrings() throws Exception {
        PiqiPatient patient = MAPPER.readValue(JSON, PiqiPatient.class);

        JsonNode out = MAPPER.readTree(MAPPER.writeValueAsString(patient));

        assertTrue(out.get("demographics").get("patientIdentifier").isTextual());
        assertEquals("123", out.get("demographics").get("patientIdentifier").asText());
    }

    @Test
    void completePatientIsValid() throws Exception {
        PiqiPatient patient = MAPPER.readValue(JSON, PiqiPatient.class);

        assertTrue(VALIDATOR.validate(patient).isEmpty());
    }

    @Test
    void emptyPatientFailsRequiredRules() {
        Set<ConstraintViolation<PiqiPatient>> violations = VALIDATOR.validate(new PiqiPatient());

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("demographics")));
    }
}