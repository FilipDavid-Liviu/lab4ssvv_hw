package org.example;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryReturnSystemIntegrationTest {

    private LibraryReturnSystem libraryReturnSystem;
    private DateCalculator dateCalculator;

    @Mock
    private ILeapYearChecker mockLeapYearChecker;

    @Before
    public void setup() {
        mockLeapYearChecker = mock(ILeapYearChecker.class);
        dateCalculator = new DateCalculator(mockLeapYearChecker);

        // Setup library system with a 14-day borrowing period and a $1.50 daily fine
        libraryReturnSystem = new LibraryReturnSystem(dateCalculator, 14, 1.50);
    }

    @Test
    public void testCalculateFine_NoFine_WhenReturnedOnTime() {
        LocalDate borrowDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 10); // 9 days borrowed

        double fine = libraryReturnSystem.calculateFine(borrowDate, returnDate);

        assertEquals(0.0, fine, 0.001);
    }

    @Test
    public void testCalculateFine_WithFine_WhenReturnedLate_NoLeapYear() {
        // Stubbing the mock for a standard year
        when(mockLeapYearChecker.isLeapYear(2023)).thenReturn(false);

        LocalDate borrowDate = LocalDate.of(2023, 2, 20);
        LocalDate returnDate = LocalDate.of(2023, 3, 10);

        // Days borrowed: 8 days in Feb (28 total) + 10 days in Mar = 18 days
        // Overdue: 18 - 14 = 4 days. Fine: 4 * 1.50 = 6.00
        double fine = libraryReturnSystem.calculateFine(borrowDate, returnDate);

        assertEquals(6.0, fine, 0.001);
    }

    @Test
    public void testCalculateFine_WithFine_WhenReturnedLate_AcrossLeapYear() {
        // Stubbing the mock for a leap year to test edge cases resulting in incorrect penalties
        when(mockLeapYearChecker.isLeapYear(2024)).thenReturn(true);

        LocalDate borrowDate = LocalDate.of(2024, 2, 20);
        LocalDate returnDate = LocalDate.of(2024, 3, 10);

        // Days borrowed: 9 days in Feb (29 total) + 10 days in Mar = 19 days
        // Overdue: 19 - 14 = 5 days. Fine: 5 * 1.50 = 7.50
        double fine = libraryReturnSystem.calculateFine(borrowDate, returnDate);

        assertEquals(7.50, fine, 0.001);
    }
}

