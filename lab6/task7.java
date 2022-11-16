package lab6;


import java.util.Scanner;

class Time{
    int hours;
    int minutes;
    int seconds;

    public Time(int hour, int minute, int second){
        hours = hour;
        minutes = minute;
        seconds = second;
    }
}

public class task7 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        Time time1 = new Time(scan.nextInt(), scan.nextInt(), scan.nextInt());
        Time time2 = new Time(scan.nextInt(), scan.nextInt(), scan.nextInt());

        System.out.println(Math.abs(time1.hours - time2.hours) + ":" + Math.abs(time1.minutes - time2.minutes) + ":" + Math.abs(time1.seconds - time2.seconds));

        scan.close();
    }
}
