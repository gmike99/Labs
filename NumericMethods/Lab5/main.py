import math
import numpy
import matplotlib.pyplot as plt

Umax = 100
v = 50
R1 = 5
R2 = 4
R3 = 7
C1 = 300 * 10 ^ -6
C2 = 150 * 10 ^ -6
T = 0.2
step = 0.00001


def dUc1(Uc1, i1, Uc2):
    return (U1 - Uc1 - (U1 - Uc1 - i1 * R1 - Uc2) * R3 - Uc2) / C1 * R1


def dUc2(Uc1, i1, Uc2):
    return (U1 - Uc1 - i1 * R3 - Uc2) / C1 * R1


def i1(U1):
    return U1 / R1


Uc1 = 0
Uc2 = 0
for t in numpy.arange(0, (T - step / 2), step):
    U1 = Umax * math.sin(2 * math.pi * v * t)
    # print("\n", U1)
    cur_i1 = i1(U1)
    # print(cur_i1)
    # Euler
    Uc1_new = Uc1 + step * dUc1(Uc1, cur_i1, Uc2)
    Uc2_new = Uc2 + step*dUc2(Uc1, cur_i1, Uc2)

    Uc1 = Uc1_new
    Uc2 = Uc2_new

    print(Uc2)
    print(str(Uc2) + '\t' + str(t))
    plt.plot(t + step, Uc2, 'ro')

plt.xlabel(r'$t$')
plt.ylabel(r'$U2$')
plt.grid(True)
plt.show()

