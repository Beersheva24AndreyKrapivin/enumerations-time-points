package telran.time;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TimePointUnitTest {
    //Tests for all being written methods
    @Test
    void TimePointTest() {
        TimePoint tpSecond = new TimePoint(120, TimeUnit.SECOND); 
        TimePoint tpSecond2 = new TimePoint(120, TimeUnit.SECOND);
        TimePoint tpMinute = new TimePoint(70, TimeUnit.MINUTE);
        TimePoint tpMinute2 = new TimePoint(2, TimeUnit.MINUTE);
        TimePoint tpHour = new TimePoint(2, TimeUnit.HOUR);
        
        assertTrue(tpMinute.compareTo(tpSecond) > 0);
        assertTrue(tpSecond.compareTo(tpMinute) < 0);
        assertTrue(tpMinute2.compareTo(tpSecond) == 0);
        assertTrue(tpHour.compareTo(tpMinute) > 0);

        assertEquals(tpSecond, tpMinute2);
        assertNotEquals(tpHour, tpMinute);

        assertEquals(tpSecond, tpMinute2.convert(TimeUnit.SECOND));
        assertEquals(tpSecond, tpSecond2.convert(TimeUnit.SECOND));
    }

    @Test
    void TimeUnitTest() {
        TimePoint p1 = new TimePoint(1, TimeUnit.HOUR); 
        TimePoint p2 = new TimePoint(61, TimeUnit.MINUTE);

        assertEquals(60, TimeUnit.SECOND.between(p1, p2));   
        assertEquals(1, TimeUnit.MINUTE.between(p1, p2));
        assertEquals(0.016666650772094727, TimeUnit.HOUR.between(p1, p2));
    }

    @Test
    void PlusTimePointAdjusterTest() {
        TimePoint tpMinute = new TimePoint(72, TimeUnit.MINUTE);
        TimePoint tpMinute2 = new TimePoint(70, TimeUnit.MINUTE);
        TimePoint tpMinute3 = new PlusTimePointAdjuster(120, TimeUnit.SECOND).adjust(tpMinute2);

        assertEquals(tpMinute, tpMinute3);
    }

    @Test
    void FutureProximityAdjusterTest() {
        TimePoint tpSecond1 = new TimePoint(120, TimeUnit.SECOND); // 120
        TimePoint tpSecond3 = new TimePoint(10000, TimeUnit.SECOND); // 10 000
        TimePoint tpSecond2 = new TimePoint(600, TimeUnit.SECOND); // 600 
        TimePoint tpMinute1 = new TimePoint(1, TimeUnit.MINUTE); // 60
        TimePoint tpMinute3 = new TimePoint(340, TimeUnit.MINUTE); // 20 400
        TimePoint tpMinute2 = new TimePoint(72, TimeUnit.MINUTE); // 4 320
        TimePoint tpMinute4 = new TimePoint(2, TimeUnit.MINUTE); // 120
        TimePoint tpMinute5 = new TimePoint(2, TimeUnit.MINUTE); // 120
        TimePoint tpMinute6 = new TimePoint(10, TimeUnit.MINUTE); // 600
        TimePoint tpHour1 = new TimePoint(1, TimeUnit.HOUR); // 3 600
        TimePoint tpHour3 = new TimePoint(3, TimeUnit.HOUR); // 10 800
        TimePoint tpHour2 = new TimePoint(2, TimeUnit.HOUR); // 7 200
        
        TimePoint[] timePoints = {tpSecond1, tpSecond2, tpSecond3, tpMinute1, tpMinute2, tpMinute3, tpHour1, tpHour2, tpHour3, tpMinute4, tpMinute5, tpMinute6};
        TimePoint[] timePointsSort = {tpMinute1, tpSecond1, tpMinute4, tpMinute5, tpSecond2, tpMinute6, tpHour1, tpMinute2, tpHour2, tpSecond3, tpHour3, tpMinute3};
        
        FutureProximityAdjuster futureAdjuster = new FutureProximityAdjuster(timePoints);
        
        assertArrayEquals(timePointsSort, futureAdjuster.timePoints);

        assertEquals(tpMinute1, futureAdjuster.adjust(new TimePoint(30, TimeUnit.SECOND)));
        assertEquals(tpSecond2, futureAdjuster.adjust(new TimePoint(500, TimeUnit.SECOND)));
        assertEquals(tpMinute3, futureAdjuster.adjust(new TimePoint(4, TimeUnit.HOUR)));
        assertEquals(tpSecond2, futureAdjuster.adjust(new TimePoint(2, TimeUnit.MINUTE)));
        
        assertEquals(null, futureAdjuster.adjust(new TimePoint(10, TimeUnit.HOUR)));
        assertEquals(null, futureAdjuster.adjust(new TimePoint(340, TimeUnit.MINUTE)));
    }
}
