package org.example;

import java.time.LocalDate;

public class DateCalculator {
    private final ILeapYearChecker leapYearChecker;

    public DateCalculator(ILeapYearChecker leapYearChecker) {
        this.leapYearChecker = leapYearChecker;
    }

    private int getDaysInMonth(int month, int year) {
        if (month == 2) {
            return leapYearChecker.isLeapYear(year) ? 29 : 28;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        return 31;
    }

    public int daysBetweenDates(LocalDate date1, LocalDate date2) {
        if (date1.isAfter(date2)) {
            LocalDate temp = date1;
            date1 = date2;
            date2 = temp;
        }

        int days = 0;
        int y1 = date1.getYear(), m1 = date1.getMonthValue(), d1 = date1.getDayOfMonth();
        int y2 = date2.getYear(), m2 = date2.getMonthValue(), d2 = date2.getDayOfMonth();

        if (y1 == y2 && m1 == m2) {
            return d2 - d1;
        }

        days += getDaysInMonth(m1, y1) - d1;

        for (int m = m1 + 1; m <= (y1 == y2 ? m2 - 1 : 12); m++) {
            days += getDaysInMonth(m, y1);
        }

        for (int y = y1 + 1; y < y2; y++) {
            days += leapYearChecker.isLeapYear(y) ? 366 : 365;
        }

        if (y1 < y2) {
            for (int m = 1; m < m2; m++) {
                days += getDaysInMonth(m, y2);
            }
            days += d2;
        } else if (y1 == y2 && m1 < m2) {
            days += d2;
        }

        return days;
    }
}
