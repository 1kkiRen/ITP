#include <stdio.h>

struct birthday{
    unsigned short day:5;
    unsigned short month:4;
    unsigned short year:7;
};


int main(){
    struct birthday date;

    date.day = 31;
    date.month = 11;
    date.year = 99;

    printf("%u %u %u", date.day, date.month, date.year);

    unsigned int size = sizeof(date);

    printf("\n%u", size);

    return 0;
}