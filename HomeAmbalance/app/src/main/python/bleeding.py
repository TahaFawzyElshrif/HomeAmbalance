#bleeding
from script import *
internal=Symbol("internal")
mele=Symbol("mele")#melena
epistaxis=Symbol("epistaxis")
heama=Symbol("heama")#heamatimesis
scalp=Symbol("scalp")
ear=Symbol("ear")
has_risk_condition=Symbol("has_risk_condition")
risked=Symbol("risked")

def isrisk(typeOfBleeding,has_risk_condition_bool):
    knowledge=And()#pure knowledge to prevent problems when rerun
    knowledge.add(And(
        Implication(internal,risked),
        Implication(has_risk_condition,risked),

        Implication(ear,risked),
        Implication(scalp,risked),
        Implication(heama,risked),
        Implication(mele,risked)

    ))
    typeOfBleeding_symbol=match_type(typeOfBleeding)
    has_risk_condition_symbol=has_risk_condition if has_risk_condition_bool else Not(has_risk_condition)
    knowledge.add(And(typeOfBleeding_symbol,has_risk_condition_symbol))
    return model_check(knowledge,risked)
def match_type(typeOfBleeding):
    match  typeOfBleeding:
        case "internal":
            return internal
        case "melena":
            return mele
        case "heamatimesis":
            return heama
        case "scalp":
            return scalp
        case "ear":
            return ear
        case "epistaxis":
            return epistaxis


print(isrisk("scalp",False))