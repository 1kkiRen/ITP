#include <stdio.h>

void triangle1(int n){
    for(int i = 1; i <= n; i++){
        for(int j = 1; j <= i; j++){
            printf("*");
        }
        printf("\n");
    }
}

void triangle2(int n){
    for(int i = 1; i <= n/2; i++){
        for(int j = 1; j <= i; j++){
            printf("*");
        }
        printf("\n");
    }

    if (n % 2 == 1){
        for(int i = 1; i <= n/2 + 1; i++){
            printf("*");
        }
        printf("\n");
    }

    for(int i = n/2; i >= 1; i--){
        for(int j = i; j >= 1; j--){
            printf("*");
        }
        printf("\n");
    }

}

void rectangle(int n){
    for(int i = 1; i <= n; i++){
        for(int j = 1; j <= 4; j++){
            printf("*");
        }
        printf("\n");
    }
}

int main(){

    int n;

    scanf("%d", &n);

    triangle1(n);
    printf("\n");
    triangle2(n);
    printf("\n");
    rectangle(n);
    

    return 0;
}