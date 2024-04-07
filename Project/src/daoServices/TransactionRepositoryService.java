package daoServices;

import dao.TransactionDao;
import models.Transaction;


public class TransactionRepositoryService {

    private TransactionDao transactionDao;

    public TransactionRepositoryService() {this.transactionDao = new TransactionDao();}

    public Transaction getTransactionByBuyer(String firstName, String lastName) {
        Transaction transaction = transactionDao.readUser(firstName, lastName);
        if(transaction != null) {
            System.out.println(transaction);
        } else {
            System.out.println("No transaction has been found!");
        }

        return transaction;
    }

    public Transaction getTransactionByVehicle(String make, String model) {
        Transaction transaction = transactionDao.read(make, model);
        if(transaction != null) {
            System.out.println(transaction);
        } else {
            System.out.println("No transaction has been found!");
        }

        return transaction;
    }

    public void removeTransaction(String transactionId) {
        Transaction transaction = transactionDao.read(transactionId);
        if(transaction == null) {
            System.out.println("No transaction has been found!");
        } else {
            transactionDao.delete(transaction);
            System.out.println("Removed " + transaction);
        }
    }

    public void addTransaction(Transaction transaction) {
        if(transaction != null) {
            transactionDao.create(transaction);
        }
    }

}
