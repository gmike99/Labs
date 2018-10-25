from math import log



def F(x):
    return 1/(x * (3 + 2 * x))

def FInt(x):
    return (-1/3) * log((3 + 2 * x)/ x)
 
if __name__=='__main__':
    n = 30
    Integral = 0
    a = int(input('Input a: '))
    b = int(input('Input b: '))
    h = (b - a)/n
    x = a + h
    Fa = F(a)
    Fb = F(b)
    for i in range(1, n-1):
        Integral = Integral + F(x)
        x += h

    Integral = h * ((Fa + Fb)/2 + Integral)
    print(Integral)   

    check = FInt(b) - FInt(a)
    print('Check:  %f' % check) 
