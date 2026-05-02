package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

public class DateCalculator_StubTest {

    private DateCalculator dateCalculator;

    @Before
    public void setup() {
        // Driver injects the manual stub into the UUT
        dateCalculator = new DateCalculator(new LeapYearCheckerStub());
    }

    @Test
    public void testDaysBetweenWithStub() {
        LocalDate d1 = LocalDate.of(2023, 12, 1);
        LocalDate d2 = LocalDate.of(2024, 3, 1);

        int days = dateCalculator.daysBetweenDates(d1, d2);
        assertEquals(91, days);
    }

    @After
    public void tearDown() {
        dateCalculator = null;
    }
}
