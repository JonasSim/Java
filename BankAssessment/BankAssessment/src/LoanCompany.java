
import static java.lang.Thread.sleep;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author w1565407
 */
public class LoanCompany extends Thread {
    
    //Transaction Array[] with 3 Transactions**
    private Transaction[] c1 = new Transaction[3];
    
    //Private variables
    private CurrentBankAccount currBankAccount;
    private String university_names = "Loan Company";
    private ThreadGroup companyGroup;
    private final int OneSecond = 3000;
    
    //Loan Company Constructor with transactions** 
    public LoanCompany(String str, CurrentBankAccount currBankAccount, ThreadGroup group) {
        
        super(group, str);
        
        this.currBankAccount = currBankAccount;
        this.companyGroup = group; 
        
        c1[0] = new Transaction(university_names, 2550);
        c1[1] = new Transaction(university_names, 500);
        c1[2] = new Transaction(university_names, 1500);
       
        
    }
    
    //Run method
    public void run(){
        

            System.out.println("Loan Company starts!");
            
        for(int i = 0; i<c1.length; i++){

            
                currBankAccount.deposit(c1[i]);
                System.out.println(getName() + " Deposite: " + c1[i].getAmount());
            
            try{
                sleep( (int) (Math.random() * OneSecond));
            }
            catch (InterruptedException e) {
                
            }
                       
        }
   
        //Loan Company thread end print**
        System.out.println(getName() + ": LOAN COMPANY FINISHED");

        }

}
