#include <stdio.h>

enum day_of_week{
    Monday = 1,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
};


int main(){

    enum day_of_week day;
    scanf("%d", &day);

    
    switch(day){
        case Monday:
            printf("1 is Monday");
            break;
        case Tuesday:
            printf("2 is Tuesday");
            break;
        case Wednesday:
            printf("3 is Wednesday");
            break;
        case Thursday:
            printf("4 is Thursday");
            break;
        case Friday:
            printf("5 is Friday");
            break;
        case Saturday:
            printf("6 is Saturday");
            break;
        case Sunday:
            printf("7 is Sunday");
            break;
        default:
            printf("Error");
            break;
    } 

    if(day == Monday){
        printf("\nMonday");
    }
    

    return 0;
}