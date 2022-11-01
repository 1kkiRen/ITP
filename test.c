#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
    int n =  -1;

    int k = 2;

    printf("%d\n", n);

    if(k <= n <= 100){
        printf("n is between 2 and 100\n");
    }
    else{
        printf("n is not between 2 and 100\n");
    }

    // (k <= n <= 100) ? printf("n is between 2 and 100") : printf("n is not between 2 and 100");

    return 0;
}