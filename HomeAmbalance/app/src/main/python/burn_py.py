#burn
from script import *
thermal_type=Symbol("thermal_type")
chemical_type=Symbol("chemical_type")
electrical_type=Symbol("electrical_type")
radiation_type=Symbol("radiation_type")
first_deg=Symbol("first_deg")
second_deg=Symbol("second_deg")
third_deg=Symbol("third_deg")
risked=Symbol("risked")
large_area_burn=Symbol("large_area_burn")
has_risk_condition=Symbol("has_risk_condition")

def getIsRisk(typeOfBurn,degreeOfBurn,isLargeArea_bool,hasRiskCondition_bool):
    knowledge=And()#pure knowledge to prevent problems when rerun
    knowledge.add(And(
        Implication(chemical_type,risked)
        ,Implication(electrical_type,risked)
        ,Implication(large_area_burn,risked)
        ,Implication(third_deg,risked)
        ,Implication(has_risk_condition,risked)
    ))
    typeOfBurn_symbol=match_type(typeOfBurn)
    degreeOfBurn_symbol=match_degree(degreeOfBurn)
    isLargeArea_symbol=large_area_burn if isLargeArea_bool else Not(large_area_burn)
    isRisk_symbol=has_risk_condition if hasRiskCondition_bool else Not(has_risk_condition)
    knowledge.add(And(typeOfBurn_symbol,degreeOfBurn_symbol,isLargeArea_symbol,isRisk_symbol))
    return  model_check(knowledge,risked)


def match_type(typeOfBurn):
    match  typeOfBurn:
        case "thermal":
            return thermal_type
        case "chemical":
            return chemical_type
        case "electrical":
            return electrical_type
        case "radiation":
            return radiation_type

def match_degree(degreeOfBurn):
    match  degreeOfBurn:
        case "first":
            return first_deg
        case "second":
            return second_deg
        case "third":
            return third_deg
        case "largeArea":
            return second_deg #any one of fist/second deg


