#include <stdio.h>

enum role{
    Student,
    TA,
    Professor
};

enum degree{
    Secondary,
    Bachelor,
    Master,
    PhD
};

struct moodle_member{
    char name[20];
    enum role role;
    enum degree degree;
};

void moodle_members_sort(struct moodle_member * members, int n){
    for(int i = 0; i < n - 1; i++){
        for(int j = i + 1; j < n; j++){
            if(members[j].role > members[i].role){
                struct moodle_member temp = members[j];
                members[j] = members[i];
                members[i] = temp;
            } 
            else if (members[j].role == members[i].role){
                if(members[j].degree > members[i].degree){
                    struct moodle_member temp = members[j];
                    members[j] = members[i];
                    members[i] = temp;
                }
            }
        }
    }
}

int main(){
    int n;
    scanf("%d", &n);

    struct moodle_member members[n];

    for(int i = 0; i < n; i++){
        scanf("%s %d %d", members[i].name, &members[i].role, &members[i].degree);
    }

    moodle_members_sort(members, n);

    for(int i = 0; i < n; i++){
        printf("\n%s", members[i].name);
        switch (members[i].role){
            case Student:
                printf(" Student");
                break;

            case TA:
                printf(" TA");
                break;

            case Professor: 
                printf(" Professor");
                break;

            default:
                break;
        }

        switch(members[i].degree){
            case Secondary:
                printf(" Secondary");
                break;

            case Bachelor:
                printf(" Bachelor");
                break;

            case Master:
                printf(" Master");
                break;

            case PhD:
                printf(" PhD");
                break;

            default:
                break;
        }
    }   
    

    return 0;
}