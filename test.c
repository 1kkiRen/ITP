#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
    char temp[21] = {'S', '_', };

    int k = 41;

    char strn[19];

    sprintf(strn, "%d", k);

    strcat(temp, strn);

    printf("%s", temp);
}