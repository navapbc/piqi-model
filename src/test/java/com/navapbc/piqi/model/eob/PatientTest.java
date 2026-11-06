package com.navapbc.piqi.model.eob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // Every required data class has at least one entry; a few fields carry real values.
    private static final String JSON = """
        {
          "member": {
            "memberId": "123",
            "birthSex": { "text": "Female", "codings": [ { "code": "F", "system": "HL7" } ] }
          },
          "coverage": [ { "subscriberId": "S1" } ],
          "medicalClaim": [ {} ],
          "claimServiceLine": [ {} ],
          "claimDiagnosis": [ {} ],
          "claimProcedure": [ {} ],
          "pharmacyClaim": [ {} ],
          "dentalClaim": [ {} ],
          "provider": [ {} ]
        }
        """;

    @Test
    void readsPlainStringsIntoSimpleAttributes() throws Exception {
        PiqiPatient patient = MAPPER.readValue(JSON, PiqiPatient.class);

        assertEquals("123", patient.getMember().getMemberId().getValue());
        assertEquals("Female", patient.getMember().getBirthSex().getText().getValue());
        assertEquals("F", patient.getMember().getBirthSex().getCodings().get(0).getCode().getValue());
        assertEquals("HL7", patient.getMember().getBirthSex().getCodings().get(0).getSystem().getValue());
        assertEquals("S1", patient.getCoverage().get(0).getSubscriberId().getValue());
    }

    @Test
    void writesSimpleAttributesBackAsPlainStrings() throws Exception {
        PiqiPatient patient = MAPPER.readValue(JSON, PiqiPatient.class);

        JsonNode out = MAPPER.readTree(MAPPER.writeValueAsString(patient));

        assertTrue(out.get("member").get("memberId").isTextual());
        assertEquals("123", out.get("member").get("memberId").asText());
    }

    @Test
    void roundTripKeepsTheSameObject() throws Exception {
        PiqiPatient original = MAPPER.readValue(JSON, PiqiPatient.class);

        PiqiPatient copy = MAPPER.readValue(MAPPER.writeValueAsString(original), PiqiPatient.class);

        assertEquals(original, copy);
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
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("member")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("coverage")));
    }
}