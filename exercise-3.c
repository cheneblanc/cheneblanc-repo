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

bool isOneDigitEqualtoX (int candidate, int x){
    while((candidate > 0)){
        int digit = candidate % 10;
        if (digit == x){
            return true;
        }
        candidate = candidate/10;
    }
    return false;
}

/* Approach: try A=1 & B=(N-1), A=2 & B= (N-2) etc. */

void removeDigit (int x, int n, int* a_pt, int* b_pt){
    if((n<10) || (n>100000000) || (x<0) || (x>9) || !a_pt || !b_pt){
        return;
    }
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
    int n = 99999999;
    int x =9;
    int a =0;
    int b =0;
    removeDigit (x, n, &a, &b);
    printf("helloworld/n");
    printf("%d and %d sum to %d and do not contain %d\n", a, b, n, x);
}