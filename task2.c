#include <stdio.h>

void print(int n);
int summary(int a, int b);
int difference(int a, int b);
int product(int a, int b);
int quotient(int a, int b);
int remainder(int a, int b);



int main(){
    int a, b;
    scanf("%d%d", &a, &b);
    print(summary(a, b));
    print(difference(a, b));
    print(product(a, b));
    print(quotient(a, b));
    print(remainder(a, b));
    return 0;
}




void print(int n){
    printf("%d\n", n); 
}

int summary(int a, int b){
    return a + b;
}

int difference(int a, int b){
    return a - b;
}

int product(int a, int b){
    return a * b;
} 

int quotient(int a, int b){
    return a / b;
}

int remainder(int a, int b){
    return a % b;
}