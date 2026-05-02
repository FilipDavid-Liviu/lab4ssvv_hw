package org.example;

import java.time.LocalDate;

public class LibraryReturnSystem {
    private final DateCalculator dateCalculator;
    private final int allowedBorrowDays;
    private final double dailyFineRate;

    public LibraryReturnSystem(DateCalculator dateCalculator, int allowedBorrowDays, double dailyFineRate) {
        this.dateCalculator = dateCalculator;
        this.allowedBorrowDays = allowedBorrowDays;
        this.dailyFineRate = dailyFineRate;
    }

    public double calculateFine(LocalDate borrowDate, LocalDate returnDate) {
        int daysBorrowed = dateCalculator.daysBetweenDates(borrowDate, returnDate);
        int overdueDays = daysBorrowed - allowedBorrowDays;

        if (overdueDays > 0) {
            return overdueDays * dailyFineRate;
        }
        return 0.0;
    }
}

