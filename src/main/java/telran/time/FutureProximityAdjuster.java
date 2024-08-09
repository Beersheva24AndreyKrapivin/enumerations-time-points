package telran.time;

import java.util.Arrays;

public class FutureProximityAdjuster implements TimePointAdjuster {
    TimePoint[] timePoints;

    public FutureProximityAdjuster(TimePoint[] timePoints) {
        // copy a given array
        // sort copy and assign to the field timePoint
        // using Java standart Arrays class
        // repeated time points are possible
        this.timePoints = Arrays.copyOf(timePoints, timePoints.length);
        Arrays.sort(this.timePoints, Comparable::compareTo);
    }

    @Override
    public TimePoint adjust(TimePoint timePoint) {
        // returns a time point only in future (greater than a given time point) from
        // the field timePoints
        // nearest to a given timePoint
        TimePoint tpFound = new PlusTimePointAdjuster(1, TimeUnit.SECOND).adjust(timePoint);
        int index = Arrays.binarySearch(timePoints, tpFound, Comparable::compareTo);

        if (-index <= timePoints.length) {
            index = -(index + 1);
        }

        return index < 0 ? null : timePoints[index];


    }

}
