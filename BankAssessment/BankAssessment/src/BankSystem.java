/**
 *
 * @author w1565407
 */
public class BankSystem {
    
    public static void main(String[] args) {
       
                                              //Current Balance: 100   AccountName   AccountNumber
        CurrentBankAccount currentAccount = new CurrentBankAccount(100 ,"Jonas", 0012343224);
        
        
        //ThreadGroups
        ThreadGroup Humans = new ThreadGroup(" is in Human GROUP");
        ThreadGroup Companies = new ThreadGroup("is in Companies GROUP");
              
        //Initialize a thread
        Student student = new Student("Student", currentAccount, Humans);
        Parent parent = new Parent("Parents", currentAccount, Humans);
        University university = new University("University", currentAccount, Companies);
        LoanCompany loan = new LoanCompany("LoanCompany", currentAccount, Companies);
                 
        //Starts threads
        student.start();
        parent.start();
        university.start();
        loan.start();
  
        //Prints thread groups 
        System.out.println("Amount of threads in Humans Group: " + "[" + Humans.activeCount() + "]" 
                                                        + " " + parent.getName() + Humans.getName());
        
        System.out.println("Amount of threads in Humans Group: " + "[" + Humans.activeCount() + "]" 
                                                        + " " + student.getName() + Humans.getName());
        
        System.out.println("Amount of threads in Companies Group: " + "[" + Companies.activeCount() + "]" 
                                                        + " " + loan.getName() + Companies.getName());
        
        System.out.println("Amount of threads in Companies Group: " + "[" + Companies.activeCount() + "]" 
                                                        + " " + university.getName() + Companies.getName());
       
       
        //All threads terminates
        try{
            student.join();
            parent.join();
            university.join();
            loan.join();
            
            //Prints final statement** 
            currentAccount.printStatement();      
            
            //Prints final balance**
            System.out.println("Final Balance: " + currentAccount.getBalance());
            System.out.println(""); 
            System.out.println("");
            
            
        }catch(InterruptedException e){
            System.out.println("Have a good day**");
        }
        
    }
    
}
