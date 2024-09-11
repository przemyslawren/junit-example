package com.healthycoderapp;

public class ActivityCalculator {
    private static final int WORKOUT_DURATION_MIN = 45;

    public static String rateActivityLevel(int weeklyCardioMinutes, int weeklyWorkoutSessions) {
        int totalMinutes = weeklyCardioMinutes + weeklyWorkoutSessions * WORKOUT_DURATION_MIN;
        double avgDailyActivityMinutes = totalMinutes / 7.0;

        if (weeklyCardioMinutes < 0 || weeklyWorkoutSessions < 0) {
            throw new RuntimeException("Input below 0");
        }

        if (avgDailyActivityMinutes < 20) {
            return "bad";
        } else if (avgDailyActivityMinutes < 40) {
            return "average";
        }
        return "good";
    }
}
