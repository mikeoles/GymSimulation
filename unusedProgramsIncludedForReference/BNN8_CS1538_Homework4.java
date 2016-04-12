/*
 * Note to the Grader: I got the instructor's permission to use outside 
 * (cont) files/sources/classes/etc. for the number generators.
 * (cont) The files were downloaded from:
 * https://commons.apache.org/proper/commons-math/
 * downloaded from "Source" (not Binaries) and used the "org" folder from:
 * commons-math3-3.6.1-src\src\main\java
 */

/**
 *
 * @author Benjamin Nimchinsky
 */
//import org.apache.commons.math3.distribution.PoissonDistribution;
//import org.apache.commons.math3.random.ValueServer;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import java.util.Random;
//import java.lang.Math;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
public class BNN8_CS1538_Homework4 {
    static Scanner scan = new Scanner(System.in);
    
    //user editable data start{
    static boolean consolePrint=false;
    static boolean printCoachMissed=false;//prints missed coach flight passenger data
    static boolean printFirstClassMissed=false;//prints missed first class flight passenger data
    static boolean printCoachFlights=false; //prints non missed coach flight passenger data
    static boolean printFirstClassFlights=false; //prints non missed first class flight passenger data
    static boolean printCommuterFlights=false;//prints commuter flight passenger data
    static boolean printAllToFile=false;//prints all passenger data to a file
    //static boolean useIdle=true;
    static double hoursUntilStart=6; //start gathering data after this many hours
    static double hoursToRun=48;//hours to run the experment
    static int commuterSeats=50;
    static int firstClassMaxSeats=50;
    static int coachMaxSeats=150;
    static int coachCheckInAgents=3;//initially 3
    static int coachSecurityStations=2;//initially 2
    static int firstClassCheckInAgents=1;//initially 1
    static int firstClassSecurityStations=1;//initially 1
    //}user editable data end
    
    
    final static double totalRuntime=hoursUntilStart+hoursToRun;//total hours to run the experement
    static int coachSeats=0;
    static int firstClassSeats=0;
    static PassengerList commuters=new PassengerList();
    static PassengerList coachList=new PassengerList();
    static PassengerList firstClassList=new PassengerList();
    static PassengerList[] coachCheckInLine=new PassengerList[coachCheckInAgents];//initially 3
    static PassengerList[] coachSecurityLine=new PassengerList[coachSecurityStations];//initially 2
    static PassengerList[] firstClassCheckInLine=new PassengerList[firstClassCheckInAgents];//initially 1
    static PassengerList[] firstClassSecurityLine=new PassengerList[firstClassSecurityStations];//initially 1
    static PassengerList commuterWaitingLine=new PassengerList();
    static PassengerList commuterFlights=new PassengerList();
    static PassengerList coachMissedFlights=new PassengerList();
    static int coachRefunds=0;
    static PassengerList coachFlights=new PassengerList();//all  coach passengers who made their flight
    static PassengerList firstClassMissedFlights=new PassengerList();
    static int firstClassRefunds=0;
    static PassengerList firstClassFlights=new PassengerList();
    static int commuterFlightCount=0;
    static int internationalFlightCount=0;
    static double coachCheckInIdle=0;
    static double firstClassCheckInIdle=0;
    static double gateWait=0;
    
    static int runTimes=1;
    static PassengerList printData=new PassengerList();
    static File profitFile = new File("defaultProfit.txt");
    static File costFile = new File("defaultCost.txt");
    static File revenueFile = new File("defaultRevenue.txt");
    static File idleFile = new File("defaultIdle.txt");
    static File waitFile = new File("defaultGateWait.txt");
    static File coachMissedFile = new File("defaultCoachMissed.txt");
    static File firstClassMissedFile = new File("defaultFirstClassMissed.txt");    
    static File coachMadeFile = new File("defaultCoachMade.txt");
    static File firstClassMadeFile = new File("defaultFirstClassMade.txt");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("How many times do you want to run the simulation?");
        runTimes = scan.nextInt();
        System.out.println("Print general data to console? (y/n)");
        String fromUser = scan.next();
        fromUser=fromUser.trim();
        if (fromUser.equalsIgnoreCase("y"))consolePrint=true;
        else consolePrint=false;
        System.out.println("Use default settings? (y/n)");
        fromUser = scan.next();
        fromUser=fromUser.trim();
        if (fromUser.equalsIgnoreCase("n")){
            firstClassMadeFile = new File("newFirstClassMade.txt");
            coachMadeFile = new File("newCoachMade.txt");
            coachMissedFile = new File("newCoachMissed.txt");
            firstClassMissedFile = new File("newFirstClassMissed.txt");
            profitFile = new File("newProfit.txt");
            costFile = new File("newCost.txt");
            revenueFile = new File("newRevenue.txt");
            idleFile = new File("newIdle.txt");
            waitFile = new File("newGateWait.txt");
            System.out.println("How mady hours should the simulation run for before gathering data?(default "+hoursUntilStart+")");
            hoursUntilStart=scan.nextDouble();
            System.out.println("How mady hours should each simulation run for while gathering data? (default "+hoursToRun+")");
            hoursToRun=scan.nextDouble();
            System.out.println("How mady check-in agents are available for commuting and international-coach passengers? (initially 3)");
            coachCheckInAgents=scan.nextInt();
            System.out.println("How mady check-in agents are available for international-first-class-passengers? (initially 1)");
            firstClassCheckInAgents=scan.nextInt();
            System.out.println("How mady security stations are available for commuting and international-coach passengers? (initially 2)");
            coachSecurityStations=scan.nextInt();
            System.out.println("How mady security stations are available for international-first-class passengers? (initially 1)");
            firstClassSecurityStations=scan.nextInt();
            
            System.out.println("Do you want to print individual passengers of a single simulation to a file or console? (y/n)");
            fromUser = scan.next();
            fromUser=fromUser.trim();
            if (fromUser.equalsIgnoreCase("y")){
                System.out.println("Do you want to print all individual passengers to a file? (only gets data for most recent simulation) (y/n)");
                fromUser = scan.next();
                fromUser=fromUser.trim();
                if (fromUser.equalsIgnoreCase("y"))printAllToFile=true;
                else printAllToFile=false;
                System.out.println("Do you want the console to include the data of each individual international coach passengers who missed his/her flights? (y/n)");
                fromUser = scan.next();
                fromUser=fromUser.trim();
                if (fromUser.equalsIgnoreCase("y"))printCoachMissed=true;
                else printCoachMissed=false;
                System.out.println("Do you want the console to include the data of each individual international first class passenger who missed his/her flights? (y/n)");
                fromUser = scan.next();
                fromUser=fromUser.trim();
                if (fromUser.equalsIgnoreCase("y"))printFirstClassMissed=true;
                else printFirstClassMissed=false;
                System.out.println("Do you want the console to include the data of each individual international coach passenger who made his/her flights? (y/n)");
                fromUser = scan.next();
                fromUser=fromUser.trim();
                if (fromUser.equalsIgnoreCase("y"))printCoachFlights=true;
                else printCoachFlights=false;
                System.out.println("Do you want the console to include the data of each individual international first class passenger who made his/her flights? (y/n)");
                fromUser = scan.next();
                fromUser=fromUser.trim();
                if (fromUser.equalsIgnoreCase("y"))printFirstClassFlights=true;
                else printFirstClassFlights=false;
                System.out.println("Do you want the console to include the data of each individual commuter passenger? (y/n)");
                fromUser = scan.next();
                fromUser=fromUser.trim();
                if (fromUser.equalsIgnoreCase("y"))printCommuterFlights=true;
                else printCommuterFlights=false;
            }

        }
        
        
        /*if(useIdle){
            coachCheckInLine=new PassengerList[coachCheckInAgents+firstClassCheckInAgents];
        }*/
        
        //Random random;
        //PoissonDistribution commuteTimes=new PoissonDistribution(40);
        //commuteTimes.
        ExponentialDistribution commuteTimes=new ExponentialDistribution(1.0/40.0);
        double mean=75.0/60.0;
        double variance=50.0/60.0;
        double sd=Math.sqrt(variance);
        NormalDistribution internationalTimes=new NormalDistribution(mean, sd);
        
        
        //for(int i=0;i<50;i++)System.out.println(internationalTimes.sample());
        

        //double localHourlyArrivals[] = commuteTimes.sample(hoursToRun);
        //System.out.println("items:"+localHourlyArrivals.length);
        //System.out.println("mean:"+commuteTimes.getMean());
        //System.out.println("sample:"+commuteTimes.sample());
        //for(int i=0;i<localHourlyArrivals.length;i++){
        //    System.out.println(localHourlyArrivals[i]);
            
        //}
        Random fish=new Random();    //nom nom worms
        double Bernoulli=0;
        
        
        try {
            //passengerData.createNewFile();
            profitFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(profitFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //passengerData.createNewFile();
            costFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(costFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //passengerData.createNewFile();
            revenueFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(revenueFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //passengerData.createNewFile();
            idleFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(idleFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //passengerData.createNewFile();
            waitFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(waitFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {
            //passengerData.createNewFile();
            coachMissedFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(coachMissedFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //passengerData.createNewFile();
            firstClassMissedFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(firstClassMissedFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //passengerData.createNewFile();
            firstClassMadeFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(firstClassMadeFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //passengerData.createNewFile();
            coachMadeFile.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(coachMadeFile));
            writeFile.write("");
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
         
      
      
        
        
        
        //loop all
        for(int iAmSoTired=0; iAmSoTired<runTimes;iAmSoTired++){//main loop
            //reinitialize
            coachRefunds=0;
            coachFlights=new PassengerList();//all  coach passengers who made their flight
            firstClassMissedFlights=new PassengerList();
            firstClassRefunds=0;
            firstClassFlights=new PassengerList();
            commuterFlightCount=0;
            internationalFlightCount=0;
            coachCheckInIdle=0;
            firstClassCheckInIdle=0;
            gateWait=0;
            coachCheckInLine=new PassengerList[coachCheckInAgents];//initially 3
            coachSecurityLine=new PassengerList[coachSecurityStations];//initially 2
            firstClassCheckInLine=new PassengerList[firstClassCheckInAgents];//initially 1
            firstClassSecurityLine=new PassengerList[firstClassSecurityStations];//initially 1
            commuters=new PassengerList();
            coachList=new PassengerList();
            firstClassList=new PassengerList();
            commuterWaitingLine=new PassengerList();
            commuterFlights=new PassengerList();
            coachMissedFlights=new PassengerList();
            

            //commute
            double timePassed=0;
            while (timePassed<totalRuntime){
                timePassed=timePassed+commuteTimes.sample();
                int bagCount=0;

                while(true){
                    Bernoulli=fish.nextDouble();
                    if (Bernoulli<=0.2)bagCount++;
                    else break;
                }
                commuters.add(timePassed,"commute",bagCount,-1);
                //System.out.println(commuters.size());
            } 

            //coach 1st flight at 6
            timePassed=0;
            for(int currentHour=6;currentHour<=totalRuntime;currentHour=currentHour+6){
                int usedSeats=0;
                getSeats();
                while(usedSeats<coachSeats){
                    timePassed=currentHour-internationalTimes.sample();
                    //need to add
                    //        totalRuntime/6 is
                    int bagCount=0;

                    while(true){
                        Bernoulli=fish.nextDouble();
                        if (Bernoulli<=0.4)bagCount++;
                        else break;
                    }
                    coachList.sortedAdd(timePassed,"coach",bagCount,currentHour);
                    usedSeats++;
                    //System.out.println("CurrentHour:"+currentHour+"\nTimepassed: "+timePassed);
                    //System.out.println(coachList.lastPassenger().toString());
                }
            } 

            //1st class 1st flight at 6
            timePassed=0;
            for(int currentHour=6;currentHour<=totalRuntime;currentHour=currentHour+6){
                int usedSeats=0;
                getSeats();
                while(usedSeats<firstClassSeats){
                    timePassed=currentHour-internationalTimes.sample();
                    //need to add
                    //        hoursToRun/6 is
                    int bagCount=0;

                    while(true){
                        Bernoulli=fish.nextDouble();
                        if (Bernoulli<=0.4)bagCount++;
                        else break;
                    }
                    firstClassList.sortedAdd(timePassed,"firstClass",bagCount,currentHour);
                    usedSeats++;
                    //System.out.println("CurrentHour:"+currentHour+"\nTimepassed: "+timePassed);
                    //System.out.println(coachList.lastPassenger().toString());
                }
            }


            for(int currentHour=(int)hoursUntilStart;currentHour<=totalRuntime;currentHour=currentHour+6){
                if(currentHour!=0){
                    internationalFlightCount++;
                }
            }
            for(double currentHour=(((int)hoursUntilStart)+((int)((hoursUntilStart%1)*2)))+0.5;currentHour<=totalRuntime;currentHour++){
                if(currentHour!=0){
                    commuterFlightCount++;
                }
            }

            firstClassCheckIn();
            coachCheckIn();

            security(firstClassCheckInLine,firstClassSecurityLine);
            security(coachCheckInLine,coachSecurityLine);

            board();


            //printLine(commuterFlights);
            //printLine(coachMissedFlights);
            //printLine(coachFlights);
            //printLine(firstClassMissedFlights);
            //printLine(firstClassFlights);

            double firstClassSecurityIdle=0;
            for(int i=0;i<firstClassSecurityStations;i++){
                firstClassSecurityIdle=firstClassSecurityIdle+firstClassSecurityLine[i].getIdle();
            }
            double coachSecurityIdle=0;
            for(int i=0;i<coachSecurityStations;i++){
                coachSecurityIdle=coachSecurityIdle+coachSecurityLine[i].getIdle();
            }
            if(consolePrint){
            System.out.println("International flights: "+internationalFlightCount);
            System.out.println("Commuter flights: "+commuterFlightCount);
            System.out.println("Passengers who missed First-class flights: "+firstClassMissedFlights.sizeAfter(hoursUntilStart));
            System.out.println("Passengers who made First-class flights: "+firstClassFlights.sizeAfter(hoursUntilStart));
            System.out.println("First-class refund count: "+firstClassRefunds);
            System.out.println("First Class Check in idle time: "+firstClassCheckInIdle);
            System.out.println("First Class Security idle time: "+firstClassSecurityIdle);
            System.out.println("Passengers who missed coach flights: "+coachMissedFlights.sizeAfter(hoursUntilStart));
            System.out.println("Passengers who made coach flights: "+coachFlights.sizeAfter(hoursUntilStart));
            System.out.println("Coach refund count: "+coachRefunds);
            System.out.println("Coach Check in idle time: "+coachCheckInIdle);
            System.out.println("Coach Security idle time: "+coachSecurityIdle);
            System.out.println("Total hours spent waiting at gate: "+gateWait);
            }
            double internationalFlightCost=10000*internationalFlightCount;
            double commuterFlightCost=commuterFlightCount*1000;
            //firstClassFlights.setClock(hoursUntilStart);
            double firstTickets=firstClassMissedFlights.sizeBetween(hoursUntilStart,totalRuntime)
                    +firstClassFlights.sizeAfter(hoursUntilStart);
            //coachFlights.setClock(hoursUntilStart);
            double coachTickets=coachMissedFlights.sizeAfter(hoursUntilStart)+coachFlights.sizeAfter(hoursUntilStart);
            //coachFlights.setClock(hoursUntilStart);
            double commuteTickets=commuterFlights.sizeBetween(hoursUntilStart,totalRuntime);
            double internationalRevenue=(firstTickets*1000)+(coachTickets*500);
            double commuteRevenue=(commuteTickets*200);
            int agentCount=coachCheckInAgents+firstClassCheckInAgents;
            double agentCost=agentCount*25;
            double totalRevenue=commuteRevenue+internationalRevenue;
            double refundCost=(coachRefunds*500.0)+(firstClassRefunds*1000.0);
            double initialCost=agentCost+internationalFlightCost+commuterFlightCost;
            double totalCost=initialCost+refundCost;
            double initialProfit=totalRevenue-initialCost;
            double totalProfit=totalRevenue-totalCost;
            if(consolePrint){
            System.out.println("Total Revenue: "+totalRevenue);
            System.out.println("Initial Cost: "+initialCost);
            System.out.println("Refund Cost: "+refundCost);
            System.out.println("Total Cost: "+totalCost);
            System.out.println("Initial Profit: "+initialProfit);
            System.out.println("Total Profit: "+totalProfit);
            System.out.println("\n)");
            }
            //printLine(commuters);
            if(printCoachMissed==true){
                printData.sortedMerge(coachMissedFlights);
            }
            if(printFirstClassMissed==true){
                printData.sortedMerge(firstClassMissedFlights);
            }
            if(printCoachFlights==true){
                printData.sortedMerge(coachFlights);
            }
            if(printFirstClassFlights==true){
                printData.sortedMerge(firstClassFlights);
            }
            if(printCommuterFlights==true){
                printData.sortedMerge(commuterFlights);
            }

            printPassengerLine(printData);



            if(printAllToFile){
                PassengerList allPassengers=new PassengerList();
                allPassengers.sortedMerge(coachMissedFlights);
                allPassengers.sortedMerge(firstClassMissedFlights);
                allPassengers.sortedMerge(coachFlights);
                allPassengers.sortedMerge(firstClassFlights);
                allPassengers.sortedMerge(commuterFlights);
                printToFile(allPassengers);
            }


            try {
                //passengerData.createNewFile();
                profitFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(profitFile,true));
                writeFile.write(totalProfit+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //passengerData.createNewFile();
                costFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(costFile,true));
                writeFile.write(totalCost+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //passengerData.createNewFile();
                revenueFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(revenueFile,true));
                writeFile.write(totalRevenue+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //passengerData.createNewFile();
                idleFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(idleFile,true));
                writeFile.write(coachCheckInIdle+"\t"+firstClassCheckInIdle+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //passengerData.createNewFile();
                waitFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(waitFile,true));
                writeFile.write(gateWait+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            try {
                //passengerData.createNewFile();
                coachMissedFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(coachMissedFile,true));
                writeFile.write(coachMissedFlights.sizeAfter(hoursUntilStart)+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //passengerData.createNewFile();
                firstClassMissedFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(firstClassMissedFile,true));
                writeFile.write(firstClassMissedFlights.sizeAfter(hoursUntilStart)+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //passengerData.createNewFile();
                firstClassMadeFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(firstClassMadeFile,true));
                writeFile.write(firstClassFlights.sizeAfter(hoursUntilStart)+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //passengerData.createNewFile();
                coachMadeFile.setWritable(true);
                Writer writeFile = new BufferedWriter(new FileWriter(coachMadeFile,true));
                writeFile.write(coachFlights.sizeAfter(hoursUntilStart)+"\n");
                writeFile.close();
            } catch (IOException ex) {
                System.out.println("error");
                //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }//end main loop
    }
    public static void coachCheckIn(){
        commuters.setClock(0);//start at 0 //hoursUntil start
        coachList.setClock(0);
        ExponentialDistribution printTimes=new ExponentialDistribution(1.0/30.0);
        ExponentialDistribution bagTimes=new ExponentialDistribution(1.0/60.0);//time per bag
        ExponentialDistribution delayTimes=new ExponentialDistribution(1.0/20.0);
        for(int k=0; k<commuters.sizeRemaining()+coachList.sizeRemaining();k++){
            Passenger currentPassenger=nextCoachCheckIn();
            if (currentPassenger==null)return;
            currentPassenger.setPrintTime(printTimes.sample());
            currentPassenger.setCheckingInTime(currentPassenger.getPrintTime());
            for(int i=0;i<currentPassenger.getNumberOfBags();i++)
                currentPassenger.setBagTime(currentPassenger.getBagTime()+bagTimes.sample());
            currentPassenger.setCheckingInTime(currentPassenger.getCheckingInTime()+currentPassenger.getBagTime());
            currentPassenger.setProblemTime(delayTimes.sample());
            currentPassenger.setCheckingInTime(currentPassenger.getCheckingInTime()+currentPassenger.getProblemTime());
            boolean start=false;
            for(int i=0;i<coachCheckInAgents;i++){
                if(coachCheckInLine[i]==null){
                    coachCheckInLine[i]=new PassengerList();
                    currentPassenger.setCheckInStartTime(currentPassenger.getArrivalTime());
                    currentPassenger.setCheckOutTime(currentPassenger.getCheckInStartTime()+currentPassenger.getCheckingInTime());
                    if(currentPassenger.getFlightType().equals("coach")&&
                            (currentPassenger.getFlightTime()<currentPassenger.getArrivalTime()||
                            currentPassenger.getFlightTime()<currentPassenger.getCheckInStartTime())){
                        currentPassenger.setMissed(true);
                        coachMissedFlights.sortedAdd(currentPassenger);
                        if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                            coachRefunds++;
                        }
                    }
                    else coachCheckInLine[i].add(currentPassenger);
                    coachCheckInIdle=coachCheckInIdle+coachCheckInLine[i].lastPassenger().getCheckOutTime();
                    start=true;
                    break;
                }
            }
            if(!start){
                double soonestTime=coachCheckInLine[0].lastPassenger().getCheckOutTime();
                int soonestIndex=0;
                for(int i=1;i<coachCheckInAgents;i++){
                    if(soonestTime>coachCheckInLine[i].lastPassenger().getCheckOutTime()){
                        soonestTime=coachCheckInLine[i].lastPassenger().getCheckOutTime();
                        soonestIndex=i;
                    }
                }
                if(currentPassenger.getArrivalTime()<soonestTime){
                    currentPassenger.setCheckInStartTime(soonestTime);
                    //System.out.println("soonestTime: "+soonestTime+" set: "+currentPassenger.getCheckInStartTime());
                    //System.out.println("soonestTime: "+soonestTime+" AriveTime: "+currentPassenger.getArrivalTime());

                }else{
                    currentPassenger.setCheckInStartTime(currentPassenger.getArrivalTime());
                    coachCheckInIdle=coachCheckInIdle+coachCheckInLine[soonestIndex].lastPassenger().getCheckOutTime();
                    //System.out.println("AriveTime: "+currentPassenger.getArrivalTime()+" SoonestTime: "+soonestTime);

                }
                currentPassenger.setCheckOutTime(currentPassenger.getCheckInStartTime()+currentPassenger.getCheckingInTime());
                if(currentPassenger.getFlightType().equals("coach")&&
                        (currentPassenger.getFlightTime()<currentPassenger.getArrivalTime()||
                        currentPassenger.getFlightTime()<currentPassenger.getCheckInStartTime())){
                    currentPassenger.setMissed(true);
                    coachMissedFlights.sortedAdd(currentPassenger);
                    if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                        coachRefunds++;
                    }
                }
                else coachCheckInLine[soonestIndex].add(currentPassenger);
                coachCheckInLine[soonestIndex].nextArrival();
                //System.out.println("new:\n"+coachCheckInLine[soonestIndex].currentPassenger().toString()+
                //            "\ncounter: "+soonestIndex+"\nindexTime: "+soonestTime+"\nPassenger Count: "+coachCheckInLine[soonestIndex].size()+"\n");

            }
        }
        for(int j=0; j<coachCheckInLine.length;j++)coachCheckInLine[j].setClock(0);
    }
    
    public static void firstClassCheckIn(){
        ExponentialDistribution printTimes=new ExponentialDistribution(1.0/30.0);
        ExponentialDistribution bagTimes=new ExponentialDistribution(1.0/60.0);//time per bag
        ExponentialDistribution delayTimes=new ExponentialDistribution(1.0/20.0);
        firstClassList.setClock(0);
        for(int k=0; k<firstClassList.sizeRemaining();k++){
            Passenger currentPassenger=firstClassList.currentPassenger();
            firstClassList.nextArrival();
            if (currentPassenger==null)return;
            //System.out.println("\nnewPass:\n"+currentPassenger.toString());
            //System.out.println("time:"+currentPassenger.getArrivalTime());
            currentPassenger.setPrintTime(printTimes.sample());
            currentPassenger.setCheckingInTime(currentPassenger.getPrintTime());
            //System.out.println(currentPassenger.getCheckingInTime());
            for(int i=0;i<currentPassenger.getNumberOfBags();i++)
                currentPassenger.setBagTime(currentPassenger.getBagTime()+bagTimes.sample());
            currentPassenger.setCheckingInTime(currentPassenger.getCheckingInTime()+currentPassenger.getBagTime());
            //System.out.println(currentPassenger.getCheckingInTime());
            currentPassenger.setProblemTime(delayTimes.sample());
            currentPassenger.setCheckingInTime(currentPassenger.getCheckingInTime()+currentPassenger.getProblemTime());
            //System.out.println(currentPassenger.getCheckingInTime());
            boolean start=false;
            for(int i=0;i<firstClassCheckInAgents;i++){
                if(firstClassCheckInLine[i]==null){
                    //System.out.println("start: "+i);
                    firstClassCheckInLine[i]=new PassengerList();
                    currentPassenger.setCheckInStartTime(currentPassenger.getArrivalTime());
                    currentPassenger.setCheckOutTime(currentPassenger.getCheckInStartTime()+currentPassenger.getCheckingInTime());
                    if(currentPassenger.getFlightType().equals("firstClass")&&
                            (currentPassenger.getFlightTime()<currentPassenger.getArrivalTime()||
                            currentPassenger.getFlightTime()<currentPassenger.getCheckInStartTime())){
                        currentPassenger.setMissed(true);
                        firstClassMissedFlights.sortedAdd(currentPassenger);
                        if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                            firstClassRefunds++;
                        }
                    }
                    else firstClassCheckInLine[i].add(currentPassenger);
                    firstClassCheckInIdle=firstClassCheckInIdle+firstClassCheckInLine[i].lastPassenger().getCheckOutTime();
                    start=true;
                    //System.out.println(coachCheckInLine[i].currentPassenger().toString()+
                    //        "\ncounter: "+i+"\nPassenger Count: "+coachCheckInLine[0].size()+"\n");
                    break;
                }
            }
            if(!start){
                //if (currentPassenger==null)System.out.println("poo1");
                //if (coachCheckInLine[0].lastPassenger()==null)System.out.println("poo2");
                double soonestTime=firstClassCheckInLine[0].lastPassenger().getCheckOutTime();
                int soonestIndex=0;
                for(int i=1;i<firstClassCheckInAgents;i++){
                    if(soonestTime>firstClassCheckInLine[i].lastPassenger().getCheckOutTime()){
                        soonestTime=firstClassCheckInLine[i].lastPassenger().getCheckOutTime();
                        soonestIndex=i;
                    }
                }
                if(currentPassenger.getArrivalTime()<soonestTime){
                    currentPassenger.setCheckInStartTime(soonestTime);
                    //System.out.println("soonestTime: "+soonestTime+" set: "+currentPassenger.getCheckInStartTime());
                    //System.out.println("soonestTime: "+soonestTime+" AriveTime: "+currentPassenger.getArrivalTime());

                }else{
                    currentPassenger.setCheckInStartTime(currentPassenger.getArrivalTime());
                    firstClassCheckInIdle=firstClassCheckInIdle+firstClassCheckInLine[soonestIndex].lastPassenger().getCheckOutTime();
                    //System.out.println("AriveTime: "+currentPassenger.getArrivalTime()+" SoonestTime: "+soonestTime);

                }
                currentPassenger.setCheckOutTime(currentPassenger.getCheckInStartTime()+currentPassenger.getCheckingInTime());
                if(currentPassenger.getFlightType().equals("firstClass")&&
                        (currentPassenger.getFlightTime()<currentPassenger.getArrivalTime()||
                        currentPassenger.getFlightTime()<currentPassenger.getCheckInStartTime())){
                    currentPassenger.setMissed(true);
                    firstClassMissedFlights.sortedAdd(currentPassenger);
                    if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                        firstClassRefunds++;
                    }
                }
                else firstClassCheckInLine[soonestIndex].add(currentPassenger);
                firstClassCheckInLine[soonestIndex].nextArrival();
                //System.out.println("new:\n"+coachCheckInLine[soonestIndex].currentPassenger().toString()+
                //            "\ncounter: "+soonestIndex+"\nindexTime: "+soonestTime+"\nPassenger Count: "+coachCheckInLine[soonestIndex].size()+"\n");

            }
        }
        for(int j=0; j<firstClassCheckInLine.length;j++)firstClassCheckInLine[j].setClock(0);
    }
    /*public static double nextLocalArrival(){
        Random rand=new Random();
        
    return -Math.log(1.0 - rand.nextDouble() / 40);
    }*/
    public static Passenger nextCoachCheckIn(){
        Passenger commuter=commuters.currentPassenger();
        Passenger coacher=coachList.currentPassenger();
        if(commuter==null&&coacher==null){
            //System.out.println("BothNull");
            return null;
        }
        if(commuter==null){
            coachList.nextArrival();
            return coacher;
        }
        if(coacher==null){
            commuters.nextArrival();
            return commuter;
        }
        if (commuter.getArrivalTime()<coacher.getArrivalTime()){
            commuters.nextArrival();
            return commuter;
        }else{
            coachList.nextArrival();
            return coacher;
        }
    }
    public static Passenger nextSecurity(PassengerList[] checkInLine){
        Passenger[] tempPassenger=new Passenger[checkInLine.length];
        boolean allNull=true;
        //boolean anyNull=false;
        for(int i=0;i<checkInLine.length;i++){
            tempPassenger[i]=checkInLine[i].currentPassenger();
            if(tempPassenger[i]!=null)allNull=false;
            //if(tempPassenger[i]==null)anyNull=true;
        }
        //Passenger commuter=commuters.currentPassenger();
        //Passenger coacher=coachList.currentPassenger();
        if(allNull==true){
            //System.out.println("AllNull");
            return null;
        }
        double lowestCheckOutTime=totalRuntime+1000; //extra high value for initialization
        int lowestIndex=-1;
        for (int i=0;i<tempPassenger.length;i++){
            if(tempPassenger[i]!=null){
                if (tempPassenger[i].getCheckOutTime()<lowestCheckOutTime){
                    lowestCheckOutTime=tempPassenger[i].getCheckOutTime();
                    lowestIndex=i;
                }
            }
        }
        checkInLine[lowestIndex].nextArrival();
        return tempPassenger[lowestIndex];
    }
    public static Passenger nextGate(PassengerList[] securityLine){
        Passenger[] tempPassenger=new Passenger[securityLine.length];
        boolean allNull=true;
        //boolean anyNull=false;
        for(int i=0;i<securityLine.length;i++){
            tempPassenger[i]=securityLine[i].currentPassenger();
            if(tempPassenger[i]!=null)allNull=false;
            //if(tempPassenger[i]==null)anyNull=true;
        }
        //Passenger commuter=commuters.currentPassenger();
        //Passenger coacher=coachList.currentPassenger();
        if(allNull==true){
            //System.out.println("AllNull");
            return null;
        }
        double lowestGate=totalRuntime+1000;//initialize high
        int lowestIndex=-1;
        for (int i=0;i<tempPassenger.length;i++){
            if(tempPassenger[i]!=null){
                if (tempPassenger[i].getGateTime()<lowestGate){
                    lowestGate=tempPassenger[i].getGateTime();
                    lowestIndex=i;
                }
            }
        }
        securityLine[lowestIndex].nextArrival();
        return tempPassenger[lowestIndex];
    }
    
    public static void getSeats(){
        firstClassSeats=0;
        coachSeats=0;
        Random fish=new Random();    //nom nom worms
        double Bernoulli=0;
        for(int i=0;i<firstClassMaxSeats;i++){
            Bernoulli=fish.nextDouble();
            if (Bernoulli<=0.8)firstClassSeats++;
        }
        for(int i=0;i<coachMaxSeats;i++){
            Bernoulli=fish.nextDouble();
            if (Bernoulli<=0.85)coachSeats++;
        }
    }
    public static void security(PassengerList[] checkInLine,PassengerList[] securityLine){
        ExponentialDistribution securityTime=new ExponentialDistribution(1.0/20.0);
        //for(int k=0; k<checkInLine();k++){
        while(true){
            Passenger currentPassenger=nextSecurity(checkInLine);
            if (currentPassenger==null)break;
            currentPassenger.setSecurityTime(securityTime.sample());
            
            boolean start=false;
            for(int i=0;i<securityLine.length;i++){
                if(securityLine[i]==null){
                    //System.out.println("start: "+i);
                    securityLine[i]=new PassengerList();
                    currentPassenger.setSecurityStartTime(currentPassenger.getCheckOutTime());
                    currentPassenger.setGateTime(currentPassenger.getSecurityStartTime()+currentPassenger.getSecurityTime());
                    if(currentPassenger.getFlightType().equals("firstClass")&&
                            (currentPassenger.getFlightTime()<currentPassenger.getCheckOutTime()||
                            currentPassenger.getFlightTime()<currentPassenger.getSecurityStartTime())){
                        currentPassenger.setMissed(true);
                        firstClassMissedFlights.sortedAdd(currentPassenger);
                        if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                            firstClassRefunds++;
                        }
                    }
                    else if(currentPassenger.getFlightType().equals("coach")&&
                            (currentPassenger.getFlightTime()<currentPassenger.getCheckOutTime()||
                            currentPassenger.getFlightTime()<currentPassenger.getSecurityStartTime())){
                        currentPassenger.setMissed(true);
                        coachMissedFlights.sortedAdd(currentPassenger);
                        if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                            coachRefunds++;
                        }
                    }
                    else securityLine[i].add(currentPassenger);
                    //firstClassSecurityIdle=firstClassSecurityIdle+firstClassCheckInLine[soonestIndex].lastPassenger().getCheckOutTime();
                    
                    
                    start=true;
                    //System.out.println(coachCheckInLine[i].currentPassenger().toString()+
                    //        "\ncounter: "+i+"\nPassenger Count: "+coachCheckInLine[0].size()+"\n");
                    break;
                }
            }
            if(!start){
                //if (currentPassenger==null)System.out.println("poo1");
                //if (coachCheckInLine[0].lastPassenger()==null)System.out.println("poo2");
                double soonestTime=securityLine[0].lastPassenger().getGateTime();
                int soonestIndex=0;
                for(int i=1;i<securityLine.length;i++){
                    if(soonestTime>securityLine[i].lastPassenger().getGateTime()){
                        soonestTime=securityLine[i].lastPassenger().getGateTime();;
                        soonestIndex=i;
                    }
                }
                if(currentPassenger.getCheckOutTime()<soonestTime){
                    //checkout before open security station
                    currentPassenger.setSecurityStartTime(soonestTime);
                    //System.out.println("soonestTime: "+soonestTime+" set: "+currentPassenger.getCheckInStartTime());
                    //System.out.println("soonestTime: "+soonestTime+" AriveTime: "+currentPassenger.getArrivalTime());

                }else{
                    securityLine[soonestIndex].addIdle(currentPassenger.getCheckOutTime()-soonestTime);
                    currentPassenger.setSecurityStartTime(currentPassenger.getCheckOutTime());
                    //System.out.println("AriveTime: "+currentPassenger.getArrivalTime()+" SoonestTime: "+soonestTime);

                }
                currentPassenger.setGateTime(currentPassenger.getSecurityStartTime()+currentPassenger.getSecurityTime());
                securityLine[soonestIndex].add(currentPassenger);
                securityLine[soonestIndex].nextArrival();
                //System.out.println("new:\n"+coachCheckInLine[soonestIndex].currentPassenger().toString()+
                //            "\ncounter: "+soonestIndex+"\nindexTime: "+soonestTime+"\nPassenger Count: "+coachCheckInLine[soonestIndex].size()+"\n");

            }
            
        }
        for(int j=0; j<securityLine.length;j++)securityLine[j].setClock(0);
    }
    public static void board(){
        for(int j=0; j<firstClassSecurityLine.length;j++)firstClassSecurityLine[j].setClock(0);
        while(true){
            Passenger currentPassenger=nextGate(firstClassSecurityLine);
            if (currentPassenger==null)break;
            //currentPassenger.setGat(securityTime.sample());
            if(currentPassenger.getFlightTime()<currentPassenger.getGateTime()){
                currentPassenger.setMissed(true);
                firstClassMissedFlights.sortedAdd(currentPassenger);
                if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                    firstClassRefunds++;
                }
            }else{
                firstClassFlights.add(currentPassenger);
            }
        }
        for(int j=0; j<coachSecurityLine.length;j++)coachSecurityLine[j].setClock(0);
        while(true){
            Passenger currentPassenger=nextGate(coachSecurityLine);
            if (currentPassenger==null)break;
            //currentPassenger.setGat(securityTime.sample());
            if (currentPassenger.getFlightType().equalsIgnoreCase("coach")){
                if(currentPassenger.getFlightTime()<currentPassenger.getGateTime()){
                    currentPassenger.setMissed(true);
                    coachMissedFlights.sortedAdd(currentPassenger);
                    if(currentPassenger.getArrivalTime()<(currentPassenger.getFlightTime()-1.5)){
                        coachRefunds++;
                    }
                }else{
                    coachFlights.add(currentPassenger);
                }
            }else if (currentPassenger.getFlightType().equalsIgnoreCase("commute")){
                commuterWaitingLine.add(currentPassenger);
            }
        }
        double flightTime=0.5;
        commuterWaitingLine.setClock(hoursUntilStart);
        Passenger currentPassenger=commuterWaitingLine.currentPassenger();
        while(true){
            if (currentPassenger==null)break;
            //double startTime=0.5;
            //commuterWaitingLine.setClock(hoursUntilStart);
            
            currentPassenger=commuterWaitingLine.currentPassenger();
            commuterWaitingLine.nextArrival();
            
            for(int i=0;i<commuterSeats;i++){
                
                if (currentPassenger==null)break;
                if (currentPassenger.getGateTime()<flightTime){
                    currentPassenger.setFlightTime(flightTime);
                    gateWait=gateWait+(currentPassenger.getFlightTime()-currentPassenger.getGateTime());
                    commuterFlights.add(currentPassenger);
                    currentPassenger=commuterWaitingLine.currentPassenger();
                    commuterWaitingLine.nextArrival();
                }else{
                    flightTime=flightTime+1;
                    i=i-1;
                }
                
            }
            
        }
    }
    public static void printPassengerLine(PassengerList line){
        if (line==null)return;
        if(line.size()==0)return;
        line.setClock(hoursUntilStart);
        for(int i=0; i<line.size();i++){
            if (line.currentPassenger()!=null)
            System.out.println("\n"+line.currentPassenger().toString());
            line.nextArrival();
        }
        //System.out.println("\nPassenger Line Size:"+line.size());
        //line.setClock(hoursUntilStart);
    }
    
    public static void printToFile(PassengerList line){
        if (line==null)return;
        if(line.size()==0)return;
        line.setClock(hoursUntilStart);
        String data="";
        for(int i=0; i<line.size();i++){
            if (line.currentPassenger()!=null){
            if (i==0) data=data+line.currentPassenger().toString()+"\n";
            else data=data+"\n"+line.currentPassenger().toString()+"\n";
            }
            line.nextArrival();
        }
        File passengerData = new File("passengerData.txt");
        try {
            passengerData.createNewFile();
            passengerData.setWritable(true);
            Writer writeFile = new BufferedWriter(new FileWriter(passengerData));
            writeFile.write(data);
            writeFile.close();
        } catch (IOException ex) {
            System.out.println("error");
            //Logger.getLogger(Homework3.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("\nPassenger Line Size:"+line.size());
        //line.setClock(hoursUntilStart);
    }
    
}
