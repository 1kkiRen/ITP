#include <stdio.h>

void histogram_of_letters_in_word_in_ascending_order(char arr[1000]){
    int temp[26] = {0};
    int ascending_order[26] = {0};

    for (int i = 0; arr[i] != '\0'; i++){
        temp[arr[i] - 'a']++;
    }

    for(int i = 0; i < 26; i++){
        for(int j = 0; j < 26; j++){
            if (temp[j] != 0 && temp[j] < temp[i]){
                temp[i] = temp[j];
                ascending_order[i] = j;
            }
        }
    }

    for(int i = 0; i < 26; i++){
        if (temp[i] != 0){

            printf("%c ", i + 'a');
            for(int j = 0; j < temp[i]; j++){
                printf("*");
            }
            printf("\n");
        }
    }


}

int main(){
    char arr[1000];
    
    scanf("%[^\n]", arr);



    histogram_of_letters_in_word_in_ascending_order(arr);

    return 0;


}