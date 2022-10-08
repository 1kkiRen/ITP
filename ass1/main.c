#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// Prototypes
void error();
void go_out(char words[101][1100], int n);
void words_sorting(char words[101][1100], int n);
void checker(char words[101][1100], int n);


int main(){
    FILE * input = fopen("input.txt", "r");                 // Open input.txt

    int n;

    fscanf(input, "%d", &n);                                // Read the number of words

    if( n > 100 || n < 1){                                  // Check if the number of words is valid
        error();
    }

    char words[101][1100] = {0};

    for(int i = 0; i < n + 1; i++){
        fscanf(input, "%s\n", words[i]);                    // Read the words
    }

    checker(words, n);                                      // Call checker function

    fclose(input);                                          // Close input.txt

    return 0;

}

void error(){
    FILE * output = fopen("output.txt", "w");               // Open output.txt
    fprintf(output, "Error in the input.txt");              // Printing error message
    fclose(output);                                         // Close output.txt
    exit(0);
}


void go_out(char words[101][1100], int n){
    FILE * output = fopen("output.txt", "w");               // Open output.txt

    for(int i = 0; i < n; i++){
        fprintf(output, "%s\n", words[i]);                  // Print the sorted words
    }

    fclose(output);                                         // Close output.txt
}

void words_sorting(char words[101][1100], int n){           // Bubble sort for words
    for(int i = 0; i < n - 1; i++){
        for(int j = i + 1; j < n; j++){
            if (strcmp(words[i], words[j]) > 0){
                char temp[1100];
                strcpy(temp, words[j]);
                strcpy(words[j], words[i]);
                strcpy(words[i], temp);
            }
        }
    }
    go_out(words, n);

}

void checker(char words[101][1100], int n){
    if (strlen(words[n]) != 0){
        error();                                            // Check if the last word is empty
    }

    for(int i = 0; i < n; i++){
        int flag = 0;
        for (int j = 0; words[i][j] != '\0'; j++){          
            if((words[i][0] < 65 || words[i][0] > 90) || words[i][0] == '\0'){
                error();                                    // Check if the first letter of each word is uppercase

            }
            if (j != 0 && (words[i][j] < 97 || words[i][j] > 122)){
                error();                                    // Check if the others letters of each word are lowercase

            }
            flag = 1;
        }
        if (flag == 0){
            error();                                        // Check if the word is not empty

        }
        if( strlen(words[i]) > 1000){
            error();                                        // Check word length
        }
    } 

    words_sorting(words, n);                                // Call the function to sort the words
}