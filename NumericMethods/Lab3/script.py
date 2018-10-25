from sympy import *
import numpy as np
import copy

X1_START = 1
X2_START = 1
e = 0.1

def find_Jacobi(expressions, variables):
    res_li = [[] for exp in expressions]
    for i, expression in enumerate(expressions):
        sym_exp = sympify(expression)
        for var in variables:
            res_li[i].append(sym_exp.diff(Symbol(var)))

    return res_li

def calculate_inverse_Jacobi(matrix, var_dict):
    matrix1 = copy.deepcopy(matrix)
    for i, row in enumerate(matrix1):
        for j, element in enumerate(row):
            matrix1[i][j] = float(element.subs({sympify(k): v for k, v in var_dict.items()}))

    matrix1 = np.linalg.inv(matrix1)

    return matrix1



if __name__=="__main__":
    expression_li = []

    expression_li.append('x1**2 - x2**2 - x1 - 0.1')
    expression_li.append('2*x1*x2 + 0.1 - x2')

    var_str = 'x1 x2'
    # has_next = True
    # while has_next:
    #     expr = input('Input your expression:   ')
    #     expression_li.append(expr)
    #     flag = input('Do you want to input another expr? \n y/N:    ')
    #     if flag == 'N':
    #         has_next = False
    # var_str = input('Input your variables:   ')
    variables = var_str.split()

    J = find_Jacobi(expression_li, variables)
    print(J)

    var_dict = {'x1': 1, 'x2': 1}
    res_list = [0, 0]
    old_res_list = [1, 1]
    summ = 0
    check = 1
    precise_enough = False
    expression_li = [sympify(func) for func in expression_li]
    while not precise_enough:
        JNum = calculate_inverse_Jacobi(J, var_dict)

        function_v_list = []
        for function in expression_li:
            function_v_list.append(float(function.subs({sympify(k): v for k, v in var_dict.items()})))
        
        for i, res in enumerate(res_list):
            for j in range(len(JNum[0])):
                summ += JNum[i][j] * old_res_list[j]
            # print(summ)
            res_list[i] = old_res_list[i] - summ

        # print(res_list)
    
        # check
        calc = []
        for i in range(len(res_list)):
            calc.append(abs((res_list[i] - old_res_list[i])/res_list[i]) * 100)
            old_res_list[i] = res_list[i]
            print(calc[i])

        var_dict = {'x1': res_list[0], 'x2': res_list[1]}
        precise_enough = all(num > e for num in calc)
        # print(precise_enough)


    print(res_list) 


def 