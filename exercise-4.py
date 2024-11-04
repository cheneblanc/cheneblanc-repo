# Write a function in Python (note the version of python used is version 3) which evaluates an arithmetic expression in Polish Notation (https://en.wikipedia.org/wiki/Polish_notation) and returns the answer as an integer or None if the expression is invalid. 

# For example,

# Input: [“+”, “3”, “3”]

# Output: 6

# Input: [“*”, “+”, “3”, “3”, “-10”]

# Output: -60

# You must complete the solution by creating a function called evaluatePNExpression  (see below for further information). You may create and use additional functions in your solution. Your solution must return an answer within 3 seconds. 

# Note that:

# The input to the function is a list of tokens (represented as strings) e.g. [“+”, “3”, “3”]
# A token is either a valid operator (see below) or an integer in the range -300 to 300 e.g. [“+”, “301”, “3”] is an invalid expression so should return None. However, [“+”, “300”, “3”] is a valid expression
# The valid operators are +, -, *, and /
# The division between two integers always truncates toward zero e.g. 1 / 2 = 0 or 12 / 7 = 1
# If the expression contains a divide by 0 the function should return None
# The input does not always represent a valid arithmetic expression in polish notation e.g. ["+", "-", "3", "4"]

# Input is a Python list, consisiting of operators and operands
# Check first entry - ensure it's an operator
# Check second entry - if it's an operator, check next entry ... keep doing this until reaching an operand
# Take the two operands following the final operator
# Apply that operator to those operands
# Update the list (or a copy of it) with a new list, deleting the operator and operands and replacing with the result


import operator

operator_dictionary = {
    "+":operator.add,
    "-":operator.sub,
    "*":operator.mul,
    "/":operator.floordiv
}

inputList = ["-","-3","-4"]

# Check that the initial list looks valid (need some additional checks in main code)

# Check that tokens in the list are all valid inputs whilst counting the number of operators  - DONE
# Check that the final two list elements are integers
# Check that the first element is an operator

def areInputsValid(inputList):

    numbersCount =0

    for i in inputList:
        if not (i=="+" or i=="-" or i=="/" or i=="*"):
            try:
                int(i)
            except ValueError: 
#                print(i)
#                print("Not a valid input - wrong type")
                return False
            else: 
                if (int(i)<-300 or int(i)>300):
#                    print("Not a valid input - out of range")
                    return False
                else:
#                    print("Digit is",i)
                    numbersCount = numbersCount +1
    if numbersCount==len(inputList):
#        print("No operators")
        return False
#    print("Inputs Valid")
    return True                

# parse the list from right to left. Add numbers to a stack (last in, first out) until hitting an operator

# when encountering an operator, perform that operation on the last two numbers in the stack
# Put that number on the top of the stack
# Go pack to parsing the list from right to lift

def evaluatePNExpression(inputList):

    if not areInputsValid(inputList):
#        print("Inputs not valid")
        return None
    
    stack = []
#    print(inputList)

    for i in reversed(inputList):
        if (i=="+" or i=="-" or i=="/" or i=="*"):
            if not stack:
#                print("no stack")
                return None
#            print("Operator=",i)  
            operand1 = int(stack.pop())
#            print("Operand1=",operand1)
            if not stack:
                print("no stack")
                return None
            operand2 = int(stack.pop())
#            print("Operand2=",operand2)
            if i=="/" and operand2 ==0:
                print("Division by Zero")
                return None
            result = (operator_dictionary[i](operand1, operand2))
#            print(result)
            stack.append(str(result))
        else:
            stack.append(int(i))
#    print((stack))
    return((result))
    

print(evaluatePNExpression(inputList))
