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
        int index = binarySearchTP(timePoint);

        if (index < 0) { 
            index = -(index + 1);
        } 
        return index < timePoints.length ? timePoints[index].convert(timePoint.getTimeUnit()): null;

    }

    private int binarySearchTP(TimePoint key) {

        int first = 0;
        int last = timePoints.length - 1;
        int middleIndex = (first + last) / 2;
        int compInt = 0;

        while (first <= last) {
            compInt = timePoints[middleIndex].compareTo(key);
            if (compInt <= 0) {
                first = middleIndex + 1;
            } else {
                last = middleIndex - 1;
            }
            middleIndex = (first + last) / 2;
        }

        return first > last ? -(first + 1) : middleIndex;

    }

}
