#include <stdio.h>



union number{
    unsigned long long int num;
    char bytes[8];
};

void encrypted(union number * a){
    for(int i = 0; i < 4; i++){
        char temp = a->bytes[i*2];
        a->bytes[i*2] = a->bytes[i * 2 + 1];
        a->bytes[i * 2 + 1] = temp;
    }
}

void decrypt(union number * a){
    encrypted(a);
}


int main(){

    union number number;
    scanf("%llu", &number.num);

    // printing union bytes
    encrypted(&number);

    printf("%llu\n", number.num);
    
    decrypt(&number);

    printf("%llu", number.num);


    return 0;
}