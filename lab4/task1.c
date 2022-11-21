#include <stdio.h>

int factorial(int n){
    return (n > 1) ? (n *= factorial(n - 1)) : (n = 1);
}


int main(){

    int n = 7;

    printf("%d", factorial(n));

    return 0;
}