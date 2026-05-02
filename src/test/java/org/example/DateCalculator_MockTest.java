package org.example;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateCalculator_MockTest {

    private DateCalculator dateCalculator;

    @Mock
    private ILeapYearChecker mockLeapYearChecker;

    @Before
    public void setup() {
        mockLeapYearChecker = mock(ILeapYearChecker.class);
        dateCalculator = new DateCalculator(mockLeapYearChecker);
    }

    @Test
    public void testDaysBetweenWithMock() {
        when(mockLeapYearChecker.isLeapYear(2023)).thenReturn(true);

        LocalDate d1 = LocalDate.of(2023, 2, 28);
        LocalDate d2 = LocalDate.of(2023, 3, 1);

        int days = dateCalculator.daysBetweenDates(d1, d2);
        assertEquals(2, days);
    }
}
