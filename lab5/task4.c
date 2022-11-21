#include <stdio.h>

struct ingredient{
    char name[20];
    unsigned int amount;
};

struct recipe{
    char name[20];
    struct ingredient ingredients[10];
};



int main(){
    struct recipe cookbook[10];
    
    for(int i = 0; i < 10; i++){
        scanf("%s", cookbook[i].name);
        for(int j = 0; j < 10; j++){
            scanf("%s %u", cookbook[i].ingredients[j].name, &cookbook[i].ingredients[j].amount);
        }
    }

    for(int i = 0; i < 10; i++){
        printf("%s\n", cookbook[i].name);
        for(int j = 0; j < 20; j++){
            printf("%s %u\n", cookbook[i].ingredients[j].name, cookbook[i].ingredients[j].amount);
        }
    }
    

    return 0;
}