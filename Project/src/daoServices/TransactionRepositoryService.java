package daoServices;

import dao.TransactionDao;
import models.Transaction;

import java.sql.SQLException;

public class TransactionRepositoryService {
    private final TransactionDao transactionDao = TransactionDao.getInstance();

    public TransactionRepositoryService() throws SQLException {}

    public Transaction getTransactionById(int transactionId) throws SQLException {
        Transaction transaction = transactionDao.read(transactionId);
        if(transaction != null) {
            System.out.println(transaction);
        } else {
            System.out.println("No transaction found!");
        }

        return transaction;
    }

    public Transaction getTransaction(int transactionId) {
        Transaction transaction = null;
        try {
            transaction = getTransactionById(transactionId);
            if(transaction == null) {
                return null;
            }
        }catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return transaction;
    }

    public Transaction getLastTransaction() throws SQLException {
        Transaction transaction = transactionDao.readLastTransaction();
        if(transaction != null) {
            System.out.println(transaction);
        } else {
            System.out.println("No transaction exists!");
        }

        return transaction;
    }

    public void removeTransaction(int transactionId) throws SQLException {
        Transaction transaction = getTransaction(transactionId);
        if(transaction == null) return;

        transactionDao.delete(transaction);
        System.out.println("Removed " + transaction);
    }

    public void addTransaction(Transaction transaction) throws SQLException {
        if(transaction != null) {
            transactionDao.add(transaction);
        }
    }

    public void updateTransaction(Transaction transaction) throws SQLException {
        if(transaction != null) {
            transactionDao.update(transaction);
            System.out.println("Updated " + transaction);
        }
    }
}
