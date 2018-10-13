from math import cos

def binary_func_search(f, interval, fault):
    a = interval[0]
    b = interval[1]

    check = 100

    while abs(check) > fault:
        x = (a + b)/2

        fx = f(x)
        fa = f(a)
        if fx * fa > 0:
            a = x
        else:
            b = x

        x_old = x

        check = fx


    return x

def f(x):
    return -1/x + 0.25 * cos(0.25 * x)

if __name__ == "__main__":
    res = binary_func_search(f, [0.1, 4], 0.01)
    print(res)

    print(f(res))
    