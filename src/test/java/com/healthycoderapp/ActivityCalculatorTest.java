package com.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCalculatorTest {

    @Test
    void should_ReturnBad_When_AvgBelow20() {
        // arrange
        int weeklyCardioMinutes = 40;
        int weeklyWorkouts = 1;

        // act
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMinutes, weeklyWorkouts);

        // arrange
        assertEquals("bad", actual);
    }

    @Test
    void should_ReturnAverage_When_AvgBetween20And40() {
        // arrange
        int weeklyCardioMinutes = 40;
        int weeklyWorkouts = 3;

        // act
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMinutes, weeklyWorkouts);

        // arrange
        assertEquals("average", actual);
    }

    @Test
    void should_ReturnBad_When_AverageAbove40() {
        // arrange
        int weeklyCardioMinutes = 40;
        int weeklyWorkouts = 7;

        // act
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMinutes, weeklyWorkouts);

        // arrange
        assertEquals("good", actual);
    }

    @Test
    void should_ThrowException_When_InputBelowZero() {
        // arrange
        int weeklyCardioMinutes = -40;
        int weeklyWorkouts = 1;

        // act
        Executable executable = () -> ActivityCalculator.rateActivityLevel(weeklyCardioMinutes, weeklyWorkouts);

        // arrange
        assertThrows(RuntimeException.class, executable);
    }

}