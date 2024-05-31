package utils;

public class ClockDisplay {
    private final TimeCounting clock;

    public ClockDisplay(TimeCounting clock) {
        this.clock = clock;
    }

    private String withLeadingZero(int val) {
        return val > 9 ? String.valueOf(val) : "0" + val;
    }

    public String getFormattedTime() {
        var totalSeconds = clock.getSeconds();

        var minutes = totalSeconds / 60;
        var seconds = totalSeconds % 60;

        return withLeadingZero(minutes) + ":" + withLeadingZero(seconds);
    }
}
