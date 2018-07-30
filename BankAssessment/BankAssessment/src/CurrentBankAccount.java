/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author w1565407
 */


public class CurrentBankAccount implements BankAccount {

    //Private variables
    private int balance;
    private int accNumber;
    private String AccountHolder;
    private Statement statm;
    
    //CurrentBankAccount Constructor 
    public CurrentBankAccount(int initializeBalance, String AccountHolder, int accNumber){
        this.balance = initializeBalance;
        this.accNumber = accNumber;
        this.AccountHolder = AccountHolder;
        this.statm = new Statement(this.AccountHolder , this.accNumber);
    }     
    
    //BankAccoun interface implementation 
    @Override
    public synchronized int getBalance() {
        return balance;
    }
    
    //BankAccoun interface implementation
    @Override
    public synchronized int getAccountNumber() {
      return accNumber;
    }
    
    //BankAccoun interface implementation
    @Override
    public synchronized String getAccountHolder() {
        return AccountHolder;
    }
    
    //BankAccoun interface implementation
    @Override
    public synchronized void deposit(Transaction t) {
        this.balance = this.balance + t.getAmount();
        statm.addTransaction(t.getCID(), t.getAmount(), balance);
        notifyAll();  //Calls other threads
    }
    
    //BankAccoun interface implementation
    @Override
    public synchronized void withdrawal(Transaction t) {

            while(this.balance < t.getAmount()){
                try{
                    System.out.println("WAITING FOR MORE MONEY");
                    
                    wait(); //Puts thread to sleep..
                    
                }catch(InterruptedException e){
                    
                }
               
            }
            
            this.balance = this.balance - t.getAmount();
            statm.addTransaction(t.getCustomerID(), t.getAmount(), balance);
            notifyAll();

        }
        
    
    //BankAccoun interface implementation
    @Override
    public synchronized boolean isOverdrawn() {
        
        return (0 >= balance);
    }

    //BankAccoun interface implementation
    @Override
    public synchronized void printStatement() {
        
       statm.print();
    
    }


}