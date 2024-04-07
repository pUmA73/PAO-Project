package dao;

import models.Transaction;
import java.util.List;
import java.util.ArrayList;

public class TransactionDao {

    private static List<Transaction> transactions = new ArrayList<>();

    // Search for transactions finalized for a specific vehicle
    public Transaction read(String make, String model) {
        if(!transactions.isEmpty()) {
            for(Transaction t : transactions) {
                if(t.getSoldVehicle().getModel().equals(model) &&
                   t.getSoldVehicle().getMake().equals(make)) {
                    return t;
                }
            }
        }
        return null;
    }

    public void delete(Transaction transaction) {
        transactions.remove(transaction);
    }

    public void create(Transaction transaction) {
        transactions.add(transaction);
    }

}
