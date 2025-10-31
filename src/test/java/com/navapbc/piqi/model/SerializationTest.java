package com.navapbc.piqi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SerializationTest {

    @Test
    public void testSerialization() throws Exception {
        Demographics demographics = new Demographics();
        demographics.setBirthDate(new SimpleAttribute("Birth Date"));
        demographics.setBirthSex(new CodeableConcept());
        demographics.setDeathDate(new SimpleAttribute("Death Date"));
        demographics.setDeceased(new CodeableConcept());
        demographics.setEthnicity(new CodeableConcept());
        demographics.setGenderIdentity(new CodeableConcept());
        demographics.setMaritalStatus(new CodeableConcept());
        demographics.setPrimaryLanguage(new CodeableConcept());
        demographics.setRaceCategory(new CodeableConcept());
        Patient originalPatient = new Patient();
        originalPatient.setDemographics(demographics);
        ObjectMapper objectMapper = new ObjectMapper();
        String originalJsonString = objectMapper.writeValueAsString(originalPatient);
        Patient deserializedPatient = objectMapper.readValue(originalJsonString, Patient.class);
        assertEquals(originalPatient, deserializedPatient);
    }
}
