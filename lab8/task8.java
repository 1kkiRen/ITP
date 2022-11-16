import java.util.Scanner;

public class task8 {
    public static void main(String [] args){
        StringBuilder sb = new StringBuilder("");
        Scanner scan = new Scanner(System.in);

        while(true){
            System.out.println("[0] = exit");
            System.out.println("[1] = print current string");
            System.out.println("[2] = append string");
            System.out.println("[3] = insert the string");
            System.out.println("[4] = reverse current string");
            System.out.println("[5] = delete substring from the current string");
            System.out.println("[6] = replace substring in the current string");


            int choice = scan.nextInt();

            switch(choice){
                case 0:
                    scan.close();
                    System.exit(0);
                    break;
                case 1:
                    System.out.println(sb);
                    break;
                case 2:
                    System.out.println("Enter string to append");
                    String str = scan.next();
                    sb.append(str);
                    break;
                case 3:
                    System.out.println("Enter string to insert");
                    String str1 = scan.next();
                    System.out.println("Enter index to insert");
                    int index = scan.nextInt();
                    sb.insert(index, str1);
                    break;
                case 4:
                    sb.reverse();
                    break;
                case 5:
                    System.out.println("Enter substring to delete");
                    String str2 = scan.next();
                    sb.delete(sb.indexOf(str2), sb.indexOf(str2) + str2.length());
                    break;
                case 6:
                    System.out.println("Enter substring to replace");
                    String str3 = scan.next();
                    System.out.println("Enter substring to replace with");
                    String str4 = scan.next();
                    sb.replace(sb.indexOf(str3), sb.indexOf(str3) + str3.length(), str4);
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        }
    }
}
