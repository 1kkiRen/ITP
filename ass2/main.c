#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


typedef enum boolean{
    false,
    true
} boolean;

typedef struct Player{
    int id;
    char name[30];
    int team_number;
    int power;
    boolean visibility;
    boolean frozen;
} Player;


typedef struct Magician{
    char name[30];
    int total_power;
} Magician;


void flip_visibility(Player *player){
    player->visibility = !player->visibility;
}

void heal(Player *player1, Player *player2){
    player2->power += ((player1->power + 1) / 2);
    player1->power -= ((player1->power + 0) / 2);
    player2->frozen = false;
}

void attack(Player *player1, Player *player2){
    if(player2->visibility == false){
        player1->frozen = true;
        player1->power = 0;
    }
    else if(player1->power > player2->power){
        player1->power += (player1->power - player2->power);
        player2->power = 0;
        player2->frozen = true;
    }
    else if (player1->power < player2->power){
        player2->power += (player2->power - player1->power);
        player1->frozen = true;
        player1->power = 0;
    }
    else if (player1->power == player2->power){
        player2->frozen = true;
        player2->power = 0;
        player1->frozen = true;
        player1->power = 0;
    }
}

void super(Player *player1, Player *player2, Player *player, int supers_count, int m){
    char temp[21] = {'S', '_'};

    char strn[19];
    sprintf(strn, "%d", supers_count);

    strcat(temp, strn);

    strcpy(player[m].name, temp);

    player[m].team_number = player1->team_number;
    player[m].power = player1->power + player2->power;
    player[m].visibility = true;
    player[m].frozen = false;
    player[m].id = m;


    strcpy(player1->name, "");
    player1->power = 0;
    player1->visibility = false;
    player1->frozen = true;
    strcpy(player2->name, "");
    player2->power = 0;
    player2->visibility = false;
    player2->frozen = true;
}

int search_team_number(Player *players, char required_name[21], int m){
    for(int i = 0; i < m; i++){
        if(strcmp(players[i].name, required_name) == 0){
            return players[i].team_number;
        }
    }
}

void sort_magicans_by_total_power(Magician *magicans, int n){
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n - 1; j++){
            if(magicans[j].total_power < magicans[j + 1].total_power){
                Magician temp = magicans[j];
                magicans[j] = magicans[j + 1];
                magicans[j + 1] = temp;
            }
        }
    }
}

void error(FILE * output){
    fclose(output);
    FILE * out = fopen("output.txt", "w");
    fprintf(out, "Invalid inputs");
    fclose(out);
    exit(0);
}

int names_check(char name[21], FILE * output){
    if (name[0] > 'Z' || name[0] < 'A'){
        error(output);
    }

    if(name[1] == '_'){
        for(int j = 2; name[j] != '\0'; j++){
            if(name[j] > '9' || name[j] < '0'){
                error(output);
            }
        }
    }else {
        for(int j = 1; name[j] != '\0'; j++){
            if((name[j] > 'z' || name[j] < 'a') && (name[j] > 'Z' || name[j] < 'A')){
                error(output);
            }
        }
    }
    return 1;
}

int find_id(Player *players, char name[21], int m){
    for(int i = 0; i < m; i++){
        if(strcmp(players[i].name, name) == 0){
            return i;
        }
    }
}

int main(){
    FILE * input = fopen("input.txt", "r"); 
    FILE * output = fopen("output.txt", "w");  


    // ========================================== Reading the input file ==========================================

    int n;
    fscanf(input, "%d", &n);
    
    if(n < 1 || n > 10){
        error(output);
    }

    Magician magician[n];

    for(int i = 0; i < n; i++){
        fscanf(input, "%s", magician[i].name);

        int L = strlen(magician[i].name);
        if(L < 1 || L > 20){
            error(output);
        }

        if (magician[i].name[0] > 'Z' || magician[i].name[0] < 'A'){
            error(output);
        }

        for(int j = 1; j < L; j++){
            if((magician[i].name[j] > 'z' || magician[i].name[j] < 'a') && (magician[i].name[j] > 'Z' || magician[i].name[j] < 'A')){
                error(output);
            }
        }
    }

    int m;
    fscanf(input, "%d", &m);

    if(m < n || m > 100){
        error(output);
    }

    Player player[m * 2];

    for(int i = 0; i < m; i++){
        fscanf(input, "%s", player[i].name);

        int L = strlen(player[i].name);
        if(L < 1 || L > 20){
            error(output);
        }

        if (player[i].name[0] > 'Z' || player[i].name[0] < 'A'){
            error(output);
        }

        for(int j = 1; j < L; j++){
            if((player[i].name[j] > 'z' || player[i].name[j] < 'a') && (player[i].name[j] > 'Z' || player[i].name[j] < 'A')){
                error(output);
            }
        }

        fscanf(input, "%d", &player[i].team_number);
        if(player[i].team_number < 0 || player[i].team_number >= n){
            error(output);
        }

        fscanf(input, "%d", &player[i].power);
        if(player[i].power < 0 || player[i].power > 1000){
            error(output);
        }

        char temp[7];

        fscanf(input, "%s", temp);

        if(strcmp(temp, "True") == 0){
            player[i].visibility = true;
        }
        else if(strcmp(temp, "False") == 0){
            player[i].visibility = false;
        }
        else {
            error(output);
        }

        player[i].id = i;

        if(player[i].power == 0){
            player[i].frozen = true;
        }
        else{
            player[i].frozen = false;
        }

    }


    // ========================================= Commands =========================================
    int supers_count = 0, command_number = 0;
    char command[30];

    for(int j = 0; fscanf(input, "%s", command) != EOF; j++){
        
        command_number++;
        if (command_number > 1000){
            fclose(input);
            fclose(output);
            error(output);
        }
        

        // fscanf(input, "%s", command);
        
        if (strcmp(command, "heal") == 0){
            char name1[21], name2[21];
            fscanf(input, "%s", name1);
            fscanf(input, "%s", name2);

            if(names_check(name1, output) && names_check(name2, output)){
                if(player[find_id(player, name1, m)].visibility == true){
                    if(player[find_id(player, name1, m)].frozen == false){
                        if(player[find_id(player, name1, m)].team_number == player[find_id(player, name2, m)].team_number){
                            if(strcmp(name1, name2) != 0)
                                heal(&player[find_id(player, name1, m)], &player[find_id(player, name2, m)]);
                                if (player[find_id(player, name2, m)].power > 0)
                                    player[find_id(player, name2, m)].frozen = false;
                            else
                                fprintf(output, "The player cannot heal itself\n");
                        }
                        else{
                            fprintf(output, "Both players should be from the same team\n");
                        }
                    } else {
                        fprintf(output, "This player is frozen\n");
                    }
                } else {
                    fprintf(output, "This player can't play\n");
                }
            }               
        }

        else if (strcmp(command, "attack") == 0){
            char name1[30], name2[30];
            fscanf(input, "%s", name1);
            fscanf(input, "%s", name2);

            if(names_check(name1, output) && names_check(name2, output)){
                if(player[find_id(player, name1, m)].visibility == true){
                    if(player[find_id(player, name1, m)].frozen == false){
                        attack(&player[find_id(player, name1, m)], &player[find_id(player, name2, m)]);                        
                    } else {
                        fprintf(output, "This player is frozen\n");
                    }
                } else {
                    fprintf(output, "This player can't play\n");
                }
            } 
        }

        else if (strcmp(command, "flip_visibility") == 0){
            char name1[30];
            fscanf(input, "%s", name1);
            if(names_check(name1, output)){                
                if(player[find_id(player, name1, m)].frozen == false){
                    flip_visibility(&player[find_id(player, name1, m)]);
                } else {
                    fprintf(output, "This player is frozen\n");
                }
            } 
        }

        else if (strcmp(command, "super") == 0){
            char name1[30], name2[30];
            fscanf(input, "%s", name1);
            fscanf(input, "%s", name2);

            if(names_check(name1, output) && names_check(name2, output)){
                if(player[find_id(player, name1, m)].visibility == true){
                    if(player[find_id(player, name1, m)].frozen == false){
                        if(player[find_id(player, name1, m)].team_number == player[find_id(player, name2, m)].team_number){
                            if(strcmp(name1, name2) != 0){
                                super(&player[find_id(player, name1, m)], &player[find_id(player, name2, m)], player, supers_count, m);
                                supers_count++;
                                m++;
                            }
                            else
                                fprintf(output, "The player cannot do super action with itself\n");
                        }
                        else{
                            fprintf(output, "Both players should be from the same team\n");
                        }
                    } else {
                        fprintf(output, "This player is frozen\n");
                    }
                } else {
                    fprintf(output, "This player can't play\n");
                }
            }   
        }

        else{
            fclose(input);
            fclose(output);
            error(output);
        }


        for(int i = 0; i < m; i++){
            if(player[i].power < 0)
                player[i].power = 0;
            
            if (player[i].power > 1000)
                player[i].power = 1000;

        }
        for (int i = 0; i < m; i++){
            printf("%s %d %d %d %d\n", player[i].name, player[i].team_number, player[i].power, player[i].visibility, player[i].frozen);
        }
        printf("\n");
    }

    // ========================================= Winner =========================================
    for(int i = 0; i < n; i++){
        magician[i].total_power = 0;
    }

    for(int i = 0; i < m; i++){
        if(player[i].frozen == false){
            magician[player[i].team_number].total_power += player[i].power;
        }
    }

    sort_magicans_by_total_power(magician, n);

    if(magician[0].total_power == magician[1].total_power){
        fprintf(output, "It's a tie\n");
    }
    else{
        fprintf(output, "The chosen wizard is %s\n", magician[0].name);
    }

    

    // ========================================= Output =========================================

}

