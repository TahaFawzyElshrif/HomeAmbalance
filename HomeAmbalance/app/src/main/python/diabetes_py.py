from script import *
#diabetes
type1=Symbol("Type1")
type2=Symbol("Type2")
agedType2=Symbol("agedType1")

def aged_type2_function(age):
    if age>=40:
        return agedType2
    else:
        return Not(agedType2)

isObese=Symbol("Obese")
def isObese_function(weight,height):
    BMI=weight/(height**2)
    if BMI>=30:
        return isObese
    else:
        return Not(isObese)

def checkType2(age,wheigt,width):
    knowledge=And(Or(type1,type2),Not(And(type1,type2)))
    knowledge.add(Implication(And(isObese,agedType2),type2))
    knowledge.add(Implication(Not(agedType2),type1))
    isObese_knowledge=isObese_function(wheigt,width)
    aged_type2_knowledge=aged_type2_function(age)
    knowledge.add(isObese_knowledge)
    knowledge.add(aged_type2_knowledge)
    return model_check(knowledge,type2)









