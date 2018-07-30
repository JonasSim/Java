/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author w1565407
 */
public class Parent extends Thread {
    
    //Transaction Array[] with 2 Transactions**
    private Transaction[] c1 = new Transaction[2];
    
    //Private variables
    private CurrentBankAccount currBankAccount;
    private ThreadGroup humanGroup;
    private String parents_names = "Parents";
    private final int OneSecond = 3000;
    
    //Parent Constructor with transactions** 
    public Parent(String str, CurrentBankAccount currBankAccount, ThreadGroup group) {
        
        super(group, str);
        
        this.currBankAccount = currBankAccount;
        this.humanGroup = group;
        
        c1[0] = new Transaction(parents_names, 300);
        c1[1] = new Transaction(parents_names, 1200);
       
        
    }
    
    //Run method** 
    public void run(){
        

            System.out.println("Parent starts!");
            
        for(int i = 0; i<c1.length; i++){

            
                currBankAccount.deposit(c1[i]);
                System.out.println(getName() + " deposited: " + c1[i].getAmount());
            
            try{
                sleep( (int) (Math.random() * OneSecond));
            }
            catch (InterruptedException e) {
                
            }
            
            
        }
        
        //Parent thread end print**
        System.out.println(getName() + ": PARENTS FINISHED");

        }


}