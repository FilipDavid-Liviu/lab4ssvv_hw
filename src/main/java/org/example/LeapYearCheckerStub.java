package org.example;

public class LeapYearCheckerStub implements ILeapYearChecker {
    @Override
    public boolean isLeapYear(int year) {
        return (year == 2024);
    }
}
