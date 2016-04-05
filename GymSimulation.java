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
            System.out.println("1: ");
            System.out.println("2: ");
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
