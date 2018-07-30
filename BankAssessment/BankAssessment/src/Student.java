/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author w1565407
 */
public class Student extends Thread {
    
    //Transaction Array[] with 6 Transactions**
    private Transaction[] c1 = new Transaction[6];
    
    //Private variables
    private CurrentBankAccount currBankAccount;
    private ThreadGroup humanGroup;
    private String student_name = "Student"; 
    private final int OneSecond = 1000;
    
    //Student Constructor with transactions**
    public Student(String str, CurrentBankAccount currBankAccount, ThreadGroup group) {
        
        super (group, str);
        
        this.currBankAccount = currBankAccount;
        this.humanGroup = group;
         
        c1[0] = new Transaction(student_name, 1000);
        c1[1] = new Transaction(student_name, 700);
        c1[2] = new Transaction(student_name, 800);
        c1[3] = new Transaction(student_name, 700);
        c1[4] = new Transaction(student_name, 500);
        c1[5] = new Transaction(student_name, 1000);
        
    }
        
    //Run method to start the Thread 
    @Override
    public void run(){
       
            System.out.println("Student starts!");
            System.out.println("Current balance:" + currBankAccount.getBalance());
            
            
        for(int i = 0; i<c1.length; i++){

            if (i == 0){
                currBankAccount.deposit(c1[i]);
                System.out.println(getName() + " deposited: " + c1[i].getAmount());
            }else if( i == 1){
                currBankAccount.withdrawal(c1[i]);
                System.out.println(getName() + " withdraw: " + c1[i].getAmount());
            }else if(i == 2){
                currBankAccount.deposit(c1[i]);
                System.out.println(getName() + " deposited: " + c1[i].getAmount());
            }else if(i == 3){
                currBankAccount.deposit(c1[i]);
                System.out.println(getName() + " deposited: " + c1[i].getAmount());
            }else if(i == 4){
                currBankAccount.withdrawal(c1[i]);
                System.out.println(getName() + " withdraw: " + c1[i].getAmount());
            }else if(i == 5){
                currBankAccount.deposit(c1[i]);
                System.out.println(getName() + " deposited: " + c1[i].getAmount());
            
            }try{
                
                //Sleeps for 1 sec = 1000ms 
                sleep( (int) (Math.random() * OneSecond));
                
            }
            
            catch (InterruptedException e) {
                
            }
            
        }
        
        //Studen thread end print**
        System.out.println(getName() + ": STUDENT FINISHED");
        
        currBankAccount.printStatement();
        
        
    }
    
}
