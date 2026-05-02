package org.example;

public class LeapYearChecker implements ILeapYearChecker {
    @Override
    public boolean isLeapYear(int year) {
        if (year % 4 != 0) return false;
        if (year % 100 != 0) return true;
        return year % 400 == 0;
    }
}