import json
from pydantic import BaseModel, model_serializer
import piqi_model
import pathlib

from piqi_model import Demographics
from piqi_model import Patient
from piqi_model import SimpleAttribute
from piqi_model import CodeableConcept
from piqi_model import Coding

demographics = Demographics()
patientIdentifier = SimpleAttribute(value='1234')
#print(patientIdentifier.model_dump_json(indent=4))
demographics.patientIdentifier = patientIdentifier
coding = Coding(code=SimpleAttribute(value='N'), display=SimpleAttribute(value='No'), system=SimpleAttribute(value='http://terminology.hl7.org/CodeSystem/v2-0136'))
#print(coding.model_dump_json(indent=4))
codings: list[Coding] = []
codings.append(coding)
demographics.deceased = CodeableConcept(text=SimpleAttribute(value='No'), codings=codings)

patient = Patient()
patient.demographics = demographics


json_pretty_string = patient.model_dump_json(indent=4)
print(json_pretty_string)

# with open('/Users/bill/projects/connectathon/piqi-evaluator/output/20251230134226/Zachariah133_Monahan736_01d6ee97-ee40-4430-a9c3-e1afb54afb31.json.piqi.patient', 'r') as f:
#     # Load the file content into a Python dictionary first
#     data_dict = json.load(f)

# # Validate the dictionary against the Pydantic model
# patient_object = Patient.model_validate(data_dict)

# print(patient_object)

# file_path = pathlib.Path('/Users/bill/projects/connectathon/piqi-evaluator/output/20251230134226/Zachariah133_Monahan736_01d6ee97-ee40-4430-a9c3-e1afb54afb31.json.piqi.patient')

# # Read the file content as a string
# json_data = file_path.read_text()

# #print(json_data)

# # Create the Pydantic model
# patient_object = Patient.model_validate_json(json_data)

# print(patient_object)

