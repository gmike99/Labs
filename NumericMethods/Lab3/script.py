import numpy as np

if __name__=="__main__":

    A = np.zeros((3, 3), int)
    np.fill_diagonal(A, 1)
    print(A)