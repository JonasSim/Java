/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author w1565407
 */
public class University extends Thread {
    
    //Transaction Array[] with 6 Transactions**
    private Transaction[] c1 = new Transaction[3];
    
    //Private variables
    private CurrentBankAccount currBankAccount;
    private String university_names = "University";
    private ThreadGroup universityGroup;
    private final int TwoSeconds = 3000;
    
    //University constructor
    public University(String str, CurrentBankAccount currBankAccount, ThreadGroup group) {
        
        super(group, str);
        
        this.currBankAccount = currBankAccount;
        this.universityGroup = group;
        
        c1[0] = new Transaction(university_names, 5000);
        c1[1] = new Transaction(university_names, 2000);
        c1[2] = new Transaction(university_names, 1150);
       
        
    }
    
    //Run method
    public void run(){
        

            System.out.println("University starts!");
            
        for(int i = 0; i<c1.length; i++){

            
                currBankAccount.withdrawal(c1[i]);
                System.out.println(getName() + " withdar: " + c1[i].getAmount());
            
            try{
                sleep( (int) (Math.random() * TwoSeconds));
            }
            catch (InterruptedException e) {
                
            }
                       
        }
   
        //University thread end print**
        System.out.println(getName() + ": UNIVERSITY FINISHED");

        }

}
