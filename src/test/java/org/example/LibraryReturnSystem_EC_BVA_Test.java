package org.example;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryReturnSystem_EC_BVA_Test {

    private LibraryReturnSystem libraryReturnSystem;
    private ILeapYearChecker mockLeapYearChecker;

    @Before
    public void setup() {
        mockLeapYearChecker = mock(ILeapYearChecker.class);
        DateCalculator dateCalculator = new DateCalculator(mockLeapYearChecker);
        // Initialization: 14-day borrow limit, 1.50 fine per day
        libraryReturnSystem = new LibraryReturnSystem(dateCalculator, 14, 1.50);
    }

    // Equivalence Partitioning (EC)

    @Test
    public void test_TC_1_EC_ReturnedOnTime() {
        LocalDate borrowDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 10); // 9 days borrowed
        assertEquals(0.0, libraryReturnSystem.calculateFine(borrowDate, returnDate), 0.001);
    }

    @Test
    public void test_TC_2_EC_ReturnedLateStandardYear() {
        when(mockLeapYearChecker.isLeapYear(2023)).thenReturn(false);
        LocalDate borrowDate = LocalDate.of(2023, 2, 20);
        LocalDate returnDate = LocalDate.of(2023, 3, 10); // 18 days borrowed (4 days late)
        assertEquals(6.0, libraryReturnSystem.calculateFine(borrowDate, returnDate), 0.001);
    }

    @Test
    public void test_TC_3_EC_ReturnedLateLeapYear() {
        when(mockLeapYearChecker.isLeapYear(2024)).thenReturn(true);
        LocalDate borrowDate = LocalDate.of(2024, 2, 20);
        LocalDate returnDate = LocalDate.of(2024, 3, 10); // 19 days borrowed (5 days late)
        assertEquals(7.50, libraryReturnSystem.calculateFine(borrowDate, returnDate), 0.001);
    }

    // Boundary Value Analysis (BVA)

    @Test
    public void test_TC_1_BVA_ExactDueDate() {
        LocalDate borrowDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 15); // Exactly 14 days borrowed
        assertEquals(0.0, libraryReturnSystem.calculateFine(borrowDate, returnDate), 0.001);
    }

    @Test
    public void test_TC_2_BVA_OneDayLate() {
        LocalDate borrowDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 16); // 15 days borrowed (1 day late)
        assertEquals(1.50, libraryReturnSystem.calculateFine(borrowDate, returnDate), 0.001);
    }

    @Test
    public void test_TC_3_BVA_SameDayReturn() {
        LocalDate date = LocalDate.of(2023, 1, 1); // 0 days borrowed
        assertEquals(0.0, libraryReturnSystem.calculateFine(date, date), 0.001);
    }
}

