package telran.time;

public class TimePoint implements Comparable<TimePoint>{
    private float amount;
    private TimeUnit timeUnit;

    public TimePoint(float amount, TimeUnit timeUnit) {
        this.amount = amount;
        this.timeUnit = timeUnit;
    }

    @Override
    public int compareTo(TimePoint arg0) {
        float timeUnitValue = amount * timeUnit.getValueOfSeconds();
        float arg0Value = arg0.getAmount() * arg0.getTimeUnit().getValueOfSeconds();
        return Float.compare(timeUnitValue, arg0Value);
    }

    public float getAmount() {
        return amount;
    } 

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public boolean equals(Object object) {
        float timeUnitValue = amount * timeUnit.getValueOfSeconds();
        float objectValue = ((TimePoint)object).getAmount() * ((TimePoint)object).getTimeUnit().getValueOfSeconds();
        return Float.valueOf(timeUnitValue).equals(objectValue);
    }

    public TimePoint convert(TimeUnit timeUnit) {
        // returns new TimePoint object equaled to the "this" object but
        // with a given timeUnit
        float amountTimePoint = (this.amount * this.timeUnit.getValueOfSeconds()) / timeUnit.getValueOfSeconds();
        return new TimePoint(amountTimePoint, timeUnit);
    }

    public TimePoint with(TimePointAdjuster adjuster) {
        return adjuster.adjust(this);
    }
}
