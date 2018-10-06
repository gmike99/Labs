#include <iostream>
#include <math.h>

using namespace std;

int n = 4, h, w;
int main() {

    double K = 3.0;
    double p = 22.0;
    double E = 0.02 * p;
    double s = 0.02 * K;

    int n = 4, w, z, inx[4] = {0, 1, 2, 3};
    double A[4][4] = {{8.3, 2.62+s, 4.1, 1.9},
                      {3.92, 8.45, 7.78-s, 2.46},
                      {3.77, 7.21+s, 8.04, 2.28},
                      {2.21, 3.65-s, 1.69, 6.69}};
    double B[4] = {-10.65+E, 12.21, 15.45-E, -8.35};
    double V[4][4], C[4][4], P[4], Y[4];
    double max, value;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            V[i][j] = A[i][j]; P[i] = B[i];
        }
    }

    //direct method
    for (int k = 0; k < n; k++) {
        //sorting
        max = fabs(V[k][k]); w = k;
        for (int l = k+1; l < n; l++) {
            if (max < fabs(V[k][l])) {
                max = fabs(V[k][l]);
                w = l;
            } 


            z = inx[k]; inx[k] = inx[w]; inx[w] = z;
            for (int d = 0; d < n; d++) {
                    if (d<k) {
                        value = C[d][k]; C[d][k] = C[d][w]; C[d][w] = value;
                    } else {
                        value = V[d][k]; V[d][k] = V[d][w]; V[d][w] = value;
                    }
                }
        }
        //the end of sorting
        Y[k] = P[k]/V[k][k];
        for (int i = k+1; i < n; i++) {
            P[i] += -V[i][k] * Y[k];
            for (int j = k+1; j < n; j++) {
                C[k][j] = V[k][j]/V[k][k];
                V[i][j] += - V[i][k] * C[k][j];
            }
        }
    }

    return 0;
}