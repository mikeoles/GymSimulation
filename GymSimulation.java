/**
 *
 * @author Benjamin Nimchinsky
 * @author 
 * @author 
 * @author 
 */
import java.util.Scanner;

public class GymSimulation {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean programLoop=true;
        while(programLoop){
            System.out.println("\nChoose an option ");
            System.out.println("0: Exit program");
            System.out.println("1: Show list of direct routes, distances and prices");
            System.out.println("2: Display a minimum spanning tree based on route distances");
            System.out.println("3: Display the path with the shortest distance between two cities");
            System.out.println("4: Display the path with the lowest cost between two cities");
            System.out.println("5: Display the path with least stops between two cities");
            int optionChoice = scan.nextInt();
            //fromUser=fromUser.trim();
            if (optionChoice==0){
                programLoop=false;
                System.exit(0);
            }else if (optionChoice==1){
            
            }
        }
    }
    
}
