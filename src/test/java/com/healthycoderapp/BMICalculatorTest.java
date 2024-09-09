package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BMICalculatorTest {
    private String environment = "dev"
;
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests");
    }

    @Nested
    class isDietRecommendedTests {
        @ParameterizedTest(name = "weight={0}, height={1}")
//    @ValueSource(doubles = {89.0, 95.0, 110})
//    @CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
            // arrange
            double weight = coderWeight;
            double height = coderHeight;
            // act
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // assert
            assertTrue(recommended);
        }

        @Test
        void should_ReturnFalse_When_DietNotRecommended() {
            // arrange (given)
            double weight = 89.0;
            double height = 1.72;
            // act (when)
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // assert (then)
            assertTrue(recommended);
        }

        @Test
        void should_ThrowArithmeticException_When_DietNotRecommended() {
            // arrange (given)
            double weight = 89.0;
            double height = 0.0;

            // act (when)
            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

            // assert (then)
            assertThrows(ArithmeticException.class, executable);
        }
    }

    @Nested
    @DisplayName("{{}} sample inner class display name")
    class FindCoderWithWorstBMITests {

        @Test
        @DisplayName(">>>> sample method display name")
        // @Disabled
        @DisabledOnOs(OS.WINDOWS)
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
            // arrange
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));

            // act
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // assert
            assertAll(
                    () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                    () -> assertEquals(98.0, coderWorstBMI.getWeight())
            );
        }

        @Test
        void should_ReturnNull_When_CoderListEmpty() {
            // arrange
            List<Coder> coders = new ArrayList<>();

            // act
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // assert
            assertNull(coderWorstBMI);
        }

        @Test
        void should_ReturnCoderWithWorstBMIInMs_When_CoderListHas10000Elements() {
            // arrange
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                coders.add(new Coder(Math.random() + 1.0, Math.random() + 100));
            }
            // act
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
            // assert
            assertTimeout(Duration.ofMillis(500), executable);
        }
    }

    @Nested
    class GetBmiScoresTests {
        @Test
        void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
            // arrange
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] expected = {18.52, 29.59, 19.53};

            // act
            double[] bmiScores = BMICalculator.getBMIScores(coders);

            // assert
            assertArrayEquals(expected, bmiScores);
        }
    }
}