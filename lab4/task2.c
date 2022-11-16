#include <stdio.h>

typedef struct exam_day{
    int day;
    char month[10];
    int year;
} Exam_day;


typedef struct student{
    char name[20];
    char surname[20];
    int group_number;
    Exam_day exam_day;
} Student;





int main(){

    Student student;

    scanf("%s%s%d%d%s%d", student.name, student.surname, &student.group_number, &student.exam_day.day, student.exam_day.month, &student.exam_day.year);

    printf("%s %s %d\n%d %s %d", student.name, student.surname, student.group_number, student.exam_day.day, student.exam_day.month, student.exam_day.year);


    return 0;
}

























