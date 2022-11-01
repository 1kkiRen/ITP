#include <stdio.h>
#include <string.h>
#include <ctype.h>
 
struct inno_players{
    char name[21];
    int team_number;
    int power;
    char visibility[10];
    int frozen;
};
 
int frozen_check(int pl, struct inno_players *players){
    if (players[pl].frozen==0){
        return 0;
    }else{
        return 1;
    } 
}
int attack(char name1[32], char name2[32], struct inno_players *players, int m, FILE *f1){
    int pl1=-1, pl2=-1;
    for (int i=0; i<m; i++){
        if(strcmp(players[i].name, name1)==0){
            pl1=i;
        }
        if (strcmp(players[i].name, name2)==0){
            pl2=i;
        }
    }
    if ((pl1<0)||(pl2<0)){
        fprintf(f1,"Invalid inputs\n");
        return 1;
    }
    else if(strlen(players[pl1].visibility)==5){
        fprintf(f1,"This player can't play\n");
    }
    else if (frozen_check(pl1,players)==1){          //S.O.S.
        fprintf(f1,"This player is frozen\n");
    }
    else if(strlen(players[pl2].visibility)==5){
        players[pl1].frozen=1;
        players[pl1].power=0;
    }else if(players[pl1].power>players[pl2].power){
        players[pl1].power+=players[pl1].power-players[pl2].power;
        players[pl2].power=0;
        players[pl2].frozen=1;
        if (players[pl1].power>1000){
            players[pl1].power=1000;
        }
    }else if(players[pl2].power>players[pl1].power){                                     //s.o.s.
        players[pl2].power+=players[pl2].power-players[pl1].power;
        players[pl1].power=0;
        players[pl1].frozen=1;
        if (players[pl2].power>1000){
            players[pl2].power=1000;
        }
    }else if(players[pl2].power==players[pl1].power){
        players[pl1].frozen=1;
        players[pl1].power=0;
        players[pl2].frozen=1;
        players[pl2].power=0;
    }
    return 0;
}
 
void flip_visibility(char name[32], struct inno_players *players, int m, FILE* f1){
    int pl;
    for (int i=0; i<m; i++){
        if(strcmp(players[i].name, name)==0){
            pl=i;
        }
    }
    if (frozen_check(pl,players)==1){
        fprintf(f1,"This player is frozen\n");
    }else if(strlen(players[pl].visibility)==5){
        strcpy(players[pl].visibility, "True");
    }else{
        strcpy(players[pl].visibility, "False");
    }
}
 
void heal(char name1[32], char name2[32], struct inno_players *players, int m, FILE* f1){
    int pl1, pl2;                                                   //PLEASE WRITE A FUNCTION
    for (int i=0; i<m; i++){
        if(strcmp(players[i].name, name1)==0){
            pl1=i;
        }
        if (strcmp(players[i].name, name2)==0){
            pl2=i;
        }
    }
    if(strlen(players[pl1].visibility)==5){
        fprintf(f1,"This player can't play\n");
    }else if (frozen_check(pl1,players)==1){          //S.O.S.
        fprintf(f1,"This player is frozen\n");
    }else if(players[pl1].team_number!=players[pl2].team_number){
        fprintf(f1,"Both players should be from the same team\n");
    }else if(pl1==pl2){
        fprintf(f1,"The player cannot heal itself\n");
    }else{
        players[pl2].power+=(players[pl1].power+1)/2;
        if (players[pl2].power>1000){
            players[pl2].power=1000;
        }
        players[pl1].power=(players[pl1].power+1)/2;
        if(frozen_check(pl2,players)==1){
            players[pl2].frozen=0;
        }
    }
}
 
int super(char name1[32], char name2[32], struct inno_players *players, int m, int count, FILE* f1){ 
    int pl1, pl2;
    for (int i=0; i<m; i++){
        if(strcmp(players[i].name, name1)==0){
            pl1=i;
        }
        if (strcmp(players[i].name, name2)==0){
            pl2=i;
        }
    }if(strlen(players[pl1].visibility)==5){
        fprintf(f1,"This player can't play\n");
    }else if (frozen_check(pl1,players)==1){          //S.O.S.
        fprintf(f1,"This player is frozen\n");
    }else if(players[pl1].team_number!=players[pl2].team_number){
        fprintf(f1,"Both players should be from the same team\n");
    }else if(pl1==pl2){
        fprintf(f1,"The player cannot do super action with itself\n");
    }else{
        char second[100];
        char first[1000]={"S_"};
        players[pl1].power+=players[pl2].power;
        sprintf(second, "%d", count);                                 //what
        strcpy(players[pl1].name, strcat(first, second));
        strcpy(players[pl2].name, "none");
        if (players[pl1].power>1000){
            players[pl1].power=1000;
        }
        players[pl2].power = 0;
        return 0;
    }
    return 1;
}

int checking_players(struct inno_players *players, int m, int n){
    for (int i=0;i<m;i++){
        if ((strlen(players[i].name)<1)||(strlen(players[i].name)>20)){
            return 1;
        }
        if(isupper(players[i].name[0])==0){
            return 1;
        }
        for (int j=1; j<strlen(players[i].name); j++){
            if (isalpha(players[i].name[j]) == 0){         //checking if all the other letters are English and lowercase
                return 1;
            }
        }
        if ((players[i].team_number<0)||(players[i].team_number>=n)){
            return 1;
        }
        if ((players[i].power<0)||(players[i].power>1000)){
            return 1;
        }
        if (strcmp(players[i].visibility,"True")!=0 && strcmp(players[i].visibility,"False")!=0){
            return 1;
        }
    }
    return 0;
}
int main(){
    
    
    FILE *f, *f1;
    f=fopen("input.txt","r");                                       //opening file input.txt for reading
    f1=fopen("output.txt","w");                                     //opening file output.txt for writing
    // if (f==NULL){                                                   //checking whether the file was opened correctly
    //     fprintf(f1, "Invalid inputs\n");
    //     return 0;
    // }
    int n;
    fscanf(f,"%d\n",&n);
    if ((n<1)||(n>10)){
        fprintf(f1,"Invalid inputs\n");
    }
 
    char magicians[n][21];
    for (int i=0;i<n;i++){
        fscanf(f,"%s\n",magicians[i]);
        if ((strlen(magicians[i])>20)||(strlen(magicians[i])<1)){
            fprintf(f1,"Invalid inputs\n");
            return 0;
        }
        else if(isupper(magicians[i][0])==0){
            fprintf(f1,"Invalid inputs\n");
            return 0;
        }
        else{
            for (int j=1; j<strlen(magicians[i]); j++){
                if (isalpha(magicians[i][j]) == 0){         //checking if all the other letters are English and lowercase
                    fprintf(f1,"Invalid inputs\n");
                    return 0;
                }
            }
        }
    }
    
    int m;
    fscanf(f,"%d\n",&m);
    
    if (m < n || m > 100){
        fprintf(f1,"Invalid inputs\n");
        return 0;
    }
    struct inno_players players[m];
    for (int i=0;i<m;i++){
        fscanf(f,"%s %d %d %s",players[i].name, &players[i].team_number, &players[i].power, players[i].visibility);
        players[i].frozen=0;
        if(players[i].power==0){
            players[i].frozen=1;
        }
    }
    if (checking_players(players, m, n)==1){
        fprintf(f1,"Invalid inputs\n");
        return 0;
    }
 
 
    char temp[30];
    int count=0;
    int s=0;
    while(!(feof(f))){                         //fscanf(f,"%s", temp)!= '\0'
        char name1[32];
        char name2[32];
        fscanf(f,"%s\n",temp);
        if (strcmp(temp,"attack")==0){
            fscanf(f,"%s %s\n",name1, name2);

            if(attack(name1, name2, players, m, f1)==1){
                f1=freopen(NULL, "w", f1);
                fprintf(f1,"Invalid inputs\n");
                return 0;
            }
        }
        else if(strcmp(temp,"flip_visibility")==0){
            fscanf(f,"%s\n",name1);
            flip_visibility(name1, players, m, f1);
        }
        else if(strcmp(temp,"heal")==0){
            fscanf(f,"%s %s\n",name1, name2);
            heal(name1, name2, players, m, f1);
        }
        else if(strcmp(temp,"super")==0){
            fscanf(f,"%s %s\n",name1, name2);
            if(super(name1, name2, players, m, count, f1)==0){
                count+=1;
            }
        }
        else{
            f1=freopen(NULL, "w", f1);
            fprintf(f1,"Invalid inputs\n");
            return 0;
        }
        s+=1;
        if (s < 0 || s > 1000){
            f1=freopen(NULL, "w", f1);
            fprintf(f1,"Invalid inputs\n");
            return 0;
        }
    }
    
 
    
    int winner_points[n+1];

    for(int i= 0; i < n + 1; i++){
        winner_points[i] = 0;
    }

    for (int i=0; i<m; i++){
        if (strcmp(players[i].name, "none")!=0){

            winner_points[players[i].team_number]+=players[i].power;
        }
    }
    int winner=0;
    int max=winner_points[0];
    for (int i=0;i<n;i++){
        if (winner_points[i]>max){
            max=winner_points[i];
            winner=i;
        }
    }
    int max_count=0;
    for (int i=0;i<n;i++){
       
        if (winner_points[i]==max){
            max_count+=1;
        }
        
    }
    if (max_count>1){
        fprintf(f1,"It's a tie\n");
    }
    else{
        fprintf(f1,"The chosen wizard is ");
        fprintf(f1,"%s\n",magicians[winner]);
    }
    fclose(f);                                                      //closing all the files
    fclose(f1);
    return 0;
}