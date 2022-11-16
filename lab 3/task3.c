#include <stdio.h>

void histogram_of_letters_in_word(char arr[1000]){
    int temp[26] = {0};

    for (int i = 0; arr[i] != '\0'; i++){
        temp[arr[i] - 'a']++;
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



    histogram_of_letters_in_word(arr);

    return 0;


}