import numpy

# Data variations
k = 3
p = 22
s = 0.02 * k
B = 0.02 * p

A = numpy.matrix([[8.3, 2.62 + s, 4.1, 1.9, -10.65 + B],
                   [3.92, 8.45, 7.78-s, 2.46, 12.21],
                   [3.77, 7.21 + s, 8.04, 2.28, 15.45 - B],
                   [2.21, 3.65-s, 1.69, 6.69, -8.35]])



def gauss_method(A):
    for k in range(A.shape[0]):

        for j in range(A.shape[1]):
            A[k, j] /= A[k, k]

            for i in range(A.shape[0]):
                A[i, j] -= A[k, j] * A[i, k]

    print(A)


gauss_method(A)

for q in range(A.shape[0]):
    print('\n')
    for w in range(A.shape[1]):
        print(A[q, w])
