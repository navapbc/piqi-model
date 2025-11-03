package com.navapbc.piqi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

    @Test
    public void testSerialization() throws Exception {
        PiqiDemographics demographics = new PiqiDemographics();
        demographics.setBirthDate(new PiqiSimpleAttribute("Birth Date"));
        demographics.setBirthSex(new PiqiCodeableConcept());
        demographics.setDeathDate(new PiqiSimpleAttribute("Death Date"));
        demographics.setDeceased(new PiqiCodeableConcept());
        demographics.setEthnicity(new PiqiCodeableConcept());
        demographics.setGenderIdentity(new PiqiCodeableConcept());
        demographics.setMaritalStatus(new PiqiCodeableConcept());
        demographics.setPrimaryLanguage(new PiqiCodeableConcept());
        demographics.setRace(new PiqiCodeableConcept());
        PiqiPatient originalPatient = new PiqiPatient();
        originalPatient.setDemographics(demographics);
        ObjectMapper objectMapper = new ObjectMapper();
        String originalJsonString = objectMapper.writeValueAsString(originalPatient);
        PiqiPatient deserializedPatient = objectMapper.readValue(originalJsonString, PiqiPatient.class);
        assertEquals(originalPatient, deserializedPatient);
    }
}
