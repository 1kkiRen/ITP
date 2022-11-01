#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


typedef enum boolean{
    false,
    true
} boolean;

typedef struct Player{    // Player struct
    int id;
    char name[30];
    int team_number;
    int power;
    boolean visibility;
    boolean frozen;
} Player;


typedef struct Magician{    // Magician struct
    char name[30];
    int total_power;
} Magician;


// Prototypes

void flip_visibility(Player *player);
void heal(Player *player1, Player *player2);
void attack(Player *player1, Player *player2);
void super(Player *player1, Player *player2, Player *player, int supers_count, int m);
int search_team_number(Player *players, char required_name[21], int m);
void sort_magicans_by_total_power(Magician *magicans, int n);
void error(FILE * output);
int names_check(char name[21], FILE * output);
int find_id(Player *players, char name[21], int m);


int main(){
    FILE * input = fopen("input.txt", "r");                 // open input file
    FILE * output = fopen("output.txt", "w");               // open output file


    // ========================================== Reading magicians' data ==========================================

    int n;                                                  // read number of magicans
    fscanf(input, "%d", &n);
    
    if(n < 1 || n > 10){                                    // check if n is valid
        error(output);      
    }

    Magician magician[n];

    for(int i = 0; i < n; i++){                             // enter magician's name and check if it is valid
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


    // ========================================== Reading players' data ==========================================

    int m;                                                  // read number of players      
    fscanf(input, "%d", &m);

    if(m < n || m > 100){                                   // check if m is valid 
        error(output);
    }

    Player player[m * 2];

    for(int i = 0; i < m; i++){                             // enter player's name and check if it is valid
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

    for(int j = 0; fscanf(input, "%s", command) != EOF; j++){               // read commands and check if there is end of file
        
        command_number++;
        if (command_number > 1000){                                         // check if command number is valid
            fclose(input);
            fclose(output);
            error(output);
        }
        
        if (strcmp(command, "heal") == 0){                                  // heal command
            char name1[21], name2[21];
            fscanf(input, "%s", name1);                                     // reading two player names
            fscanf(input, "%s", name2);

            if(names_check(name1, output) && names_check(name2, output)){   // check if names are valid
                if(player[find_id(player, name1, m)].visibility == true){                                                           // check if player1 status is valid
                    if(player[find_id(player, name1, m)].frozen == false){                                                          // check if player1 status is valid
                        if(player[find_id(player, name1, m)].team_number == player[find_id(player, name2, m)].team_number){         // check if player1 and player2 are in the same team
                            if(strcmp(name1, name2) != 0){
                                heal(&player[find_id(player, name1, m)], &player[find_id(player, name2, m)]);                       // call heal function
                                if (player[find_id(player, name2, m)].power > 0)
                                    player[find_id(player, name2, m)].frozen = false;
                                }
                            else
                                fprintf(output, "The player cannot heal itself\n");                                 // print error messages
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

        else if (strcmp(command, "attack") == 0){                               // attack command
            char name1[30], name2[30];
            fscanf(input, "%s", name1);                                         // reading two player names          
            fscanf(input, "%s", name2);

            if(names_check(name1, output) && names_check(name2, output)){       // check if names are valid
                if(player[find_id(player, name1, m)].visibility == true){                                                    // check if player1 status is valid
                    if(player[find_id(player, name1, m)].frozen == false){                                                   // check if player1 status is valid
                        attack(&player[find_id(player, name1, m)], &player[find_id(player, name2, m)]);                      // call attack function                      
                    } else {
                        fprintf(output, "This player is frozen\n");                                                 // print error messages
                    }
                } else {
                    fprintf(output, "This player can't play\n");
                }
            } 
        }

        else if (strcmp(command, "flip_visibility") == 0){                      // flip_visibility command
            char name1[30];
            fscanf(input, "%s", name1);                                         // reading player name

            if(names_check(name1, output)){                                     // check if name is valid
                if(player[find_id(player, name1, m)].frozen == false){          // check if player status is valid
                    flip_visibility(&player[find_id(player, name1, m)]);        // call flip_visibility function
                } else {
                    fprintf(output, "This player is frozen\n");                 // print error message
                }
            } 
        }

        else if (strcmp(command, "super") == 0){                                // super command
            char name1[30], name2[30];              
            fscanf(input, "%s", name1);                                         // reading two player names
            fscanf(input, "%s", name2);

            if(names_check(name1, output) && names_check(name2, output)){       // check if names are valid
                if(player[find_id(player, name1, m)].visibility == true){                                                       // check if player1 status is valid
                    if(player[find_id(player, name1, m)].frozen == false){                                                      // check if player1 status is valid 
                        if(player[find_id(player, name1, m)].team_number == player[find_id(player, name2, m)].team_number){     // check if player1 and player2 are in the same team
                            if(strcmp(name1, name2) != 0){
                                super(&player[find_id(player, name1, m)], &player[find_id(player, name2, m)], player, supers_count, m); // call super function
                                supers_count++;
                                m++;
                            }
                            else
                                fprintf(output, "The player cannot do super action with itself\n");                // print error messages
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

        else{                                                                   // if command is not valid we call error function
            fclose(input);
            fclose(output);
            error(output);
        }


        for(int i = 0; i < m; i++){                                             // power normalization
            if(player[i].power < 0)
                player[i].power = 0;
            
            if (player[i].power > 1000)
                player[i].power = 1000;

        }
    }

    // ========================================= Winner =========================================
    for(int i = 0; i < n; i++){                                                 
        magician[i].total_power = 0;                                            // makes total power of each magician 0
    }

    for(int i = 0; i < m; i++){
        if(player[i].frozen == false){
            magician[player[i].team_number].total_power += player[i].power;     // calculates total power of each magician
        }
    }

    sort_magicans_by_total_power(magician, n);                                  // sorts magician structures by total power

    if(magician[0].total_power == magician[1].total_power){                     // if total powers are equal we print "It's a tie"
        fprintf(output, "It's a tie\n");
    }
    else{
        fprintf(output, "The chosen wizard is %s\n", magician[0].name);         // else we print the name of the winner
    }

}


// ========================================= Functions' realisation =========================================

void flip_visibility(Player *player){
    player->visibility = !player->visibility;
}

void heal(Player *player1, Player *player2){                            
    player2->power += ((player1->power + 1) / 2);                               
    player1->power -= ((player1->power + 0) / 2);
    player2->frozen = false;
}

void attack(Player *player1, Player *player2){
    if(player2->visibility == false){                                            // if player2 is invisible we can't attack him
        player1->frozen = true;                                                  // so we freeze player1
        player1->power = 0;
    }
    else if(player1->power > player2->power){                                    // if player1 power is greater than player2 power
        player1->power += (player1->power - player2->power);                     // we add the difference to player1 power       
        player2->power = 0;                                                      // and make player2 power 0 
        player2->frozen = true;                                                  // and freeze player2
    }
    else if (player1->power < player2->power){                                   // if player1 power is less than player2 power
        player2->power += (player2->power - player1->power);                     // we add the difference to player2 power
        player1->power = 0;                                                      // and make player1 power 0
        player1->frozen = true;                                                  // and freeze player1
    }
    else if (player1->power == player2->power){                                  // if player1 power is equal to player2 power
        player2->frozen = true;                                                  // we freeze both players
        player2->power = 0;                                                      // and make their power 0
        player1->frozen = true;
        player1->power = 0;
    }
}

void super(Player *player1, Player *player2, Player *player, int supers_count, int m){
    char temp[21];
    sprintf(temp, "S_%d", supers_count);                                        // converts supers_count to string

    strcpy(player[m].name, temp);                                               // player[m].name = S_supers_count

    player[m].team_number = player1->team_number;                                   
    player[m].power = player1->power + player2->power;
    player[m].visibility = true;
    player[m].frozen = false;
    player[m].id = m;


    strcpy(player1->name, "");                                                  // player1 and player2 names are empty as they are one super player
    player1->power = 0;                                                         // same for power
    player1->visibility = false;                                                // same for visibility
    player1->frozen = true;                                                     // same for frozen
    strcpy(player2->name, "");
    player2->power = 0;
    player2->visibility = false;
    player2->frozen = true;
}

int search_team_number(Player *players, char required_name[21], int m){         // function to find team number of a player by name
    for(int i = 0; i < m; i++){
        if(strcmp(players[i].name, required_name) == 0){
            return players[i].team_number;
        }
    }
}

void sort_magicans_by_total_power(Magician *magicans, int n){                   // bubble sort for magician structures
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

void error(FILE * output){                                                  // error function
    fclose(output);                                                        
    FILE * out = fopen("output.txt", "w");                                  // rewrite output file
    fprintf(out, "Invalid inputs");
    fclose(out);
    exit(0);
}

int names_check(char name[21], FILE * output){                              // checks if name is valid
    if (name[0] > 'Z' || name[0] < 'A'){                                    // checks if first letter is capital
        error(output);                                                       
    }

    if(name[1] == '_'){                                                     // checks if second letter is '_'
        for(int j = 2; name[j] != '\0'; j++){                               // if this is true we check if the rest of the name is number
            if(name[j] > '9' || name[j] < '0'){
                error(output);
            }
        }
    }else {
        for(int j = 1; name[j] != '\0'; j++){                               // if second letter is not '_' we check if the rest of the name is letter
            if((name[j] > 'z' || name[j] < 'a') && (name[j] > 'Z' || name[j] < 'A')){
                error(output);
            }
        }
    }
    return 1;                                                               // if name is valid we return 1
}

int find_id(Player *players, char name[21], int m){                         // finds id of the player with given name
    for(int i = 0; i < m; i++){
        if(strcmp(players[i].name, name) == 0){
            return i;
        }
    }
}