from numpy.linalg import solve as func
from numpy import array

p = 22
k = 3
s = 0.02 * k
B = 0.02 * p

V = array([[8.3, 2.62+s, 4.1, 1.9], 
        [3.92, 8.45, 7.78-s, 2.46], 
        [3.77, 7.21+s, 8.04, 2.28], 
        [2.21, 6.65-s, 1.69, 6.69]])

P = array([-10.65+B, 12.21, 15.45-B, -8.35])

def gauss_method(V, P):
    no_of_rows = len(V) 
    no_of_columns = len(V[0])
    T = V
    B = P
    C = V
    
    inx = list(range(4))
    w  = 0
    z = 0
    value = 0

    for k in range(no_of_rows):
        max = abs(V[k][k])
        w = k
        
        for l in range(no_of_columns-1):
            if (max < V[k][l]):
                max = V[k][l]
            z = inx[k] 
            inx[k] = inx[w]
            inx[w] = z

            
            for d in range(no_of_rows-1):


                if d < k: 
                    value = C[d][k]; C[d][k] = C[d][w]; C[d][w] = value
                else: 
                    value = V[d][k]; V[d][k] = V[d][w]; V[d][w] = value

        if d < k:  
            value = C[d][k]; C[d][k] = C[d][w]; C[d][w] = value
        else:
            value = V[d][k]; V[d][k] = V[d][w]; V[d][w] = value
    
    return func(T, B)
                
                    
if __name__ == '__main__':
    ans = gauss_method(V, P)
    print(ans)