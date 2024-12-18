package model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private String email;
    private Timestamp transactionDate;
    private double transactionAmount;

    public Transaction() {
        // Default constructor
    }

    public Transaction(int transactionId, String email, Timestamp transactionDate, double transactionAmount) {
        this.transactionId = transactionId;
        this.email = email;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
