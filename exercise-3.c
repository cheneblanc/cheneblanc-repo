#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>
#include <math.h>

/**/

/* tidy up by ensuring numbers outside of scope are rejected */

/* Find A + B = N, such that neither A, nor B contain digit X */

/* 10 <= N <= 10e8 */


/* For each trial, check each digit and reject if any of them are X */
/* Check if a digit is X by doing modulo 10 maths */

bool isOneDigitEqualtoX (int candidate, int X){
    int check = candidate;
    while((check > 0)){
        int digit = check % 10;
        if (digit == X){
            return true;
        }
        check = check/10;
    }
    return false;
}

/* Approach: try A=1 & B=(N-1), A=2 & B= (N-2) etc. */

void removeDigit (int n, int x, int *a_pt, int *b_pt){
    for (int i = 1; i < n; i++)
    {
        int candidateA=n-i;
        int candidateB=i;
        if(!isOneDigitEqualtoX(candidateA,x)&&!isOneDigitEqualtoX(candidateB,x)){
            *a_pt = candidateA;
            *b_pt = candidateB;
            return;
        }
    }
}

int main (void){
    int n = 650;
    int x =1;
    int a;
    int b;
    removeDigit (n, x, &a, &b);
    printf("helloworld/n");
    printf("%d and %d sum to %d and do not contain %d\n", a, b, n, x);
}