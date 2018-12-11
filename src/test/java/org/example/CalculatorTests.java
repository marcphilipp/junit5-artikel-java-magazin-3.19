/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example;

// tag::jupiter_intro[]
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// end::jupiter_intro[]
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.MessageFormat;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

// tag::jupiter_intro[]
class CalculatorTests {

    private Calculator calculator;

    @BeforeEach
    void createCalculator() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("1 + 1 = 2")
    @Tag("addition")
    void onePlusOneIsTwo() {
        long newValue = calculator.set(1).add(1).longValue();

        assertEquals(2, newValue);
    }

    @Test
    @DisplayName("(2 * 3) / 4 = 6/4 = 3/2 = 1.5")
    @Tag("multiplication")
    @Tag("division")
    void divideResultOfMultiplication() {
        BigDecimal newValue = calculator.set(2).multiply(3).divide(4).get();

        assertEquals(new BigDecimal("1.5"), newValue);
    }

    @Test
    @Tag("input-validation")
    void cannotSetValueToNull() {
        IllegalArgumentException exception
            = assertThrows(IllegalArgumentException.class, () -> calculator.set(null));

        assertEquals("cannot set value to null", exception.getMessage());
    }

// end::jupiter_intro[]

// tag::dynamic_tests[]
    @TestFactory
    @Tag("multiplication")
    @Tag("power")
    Stream<DynamicTest> powersOfTwo() {
        return IntStream.range(1, 100)
            .mapToObj(value -> dynamicTest(MessageFormat.format("{0}^2 = {0} * {0}", value), () -> {
                var expectedValue = new Calculator(value).multiply(value).get();
                var actualValue = calculator.set(value).power(2).get();
                assertEquals(expectedValue, actualValue);
            }));
    }
// end::dynamic_tests[]

// tag::parameterized_tests[]
    @ParameterizedTest(name = "sqrt({0}) = {1}")
    @CsvSource({
        "1, 1.0000000000000000",
        "2, 1.4142135623730951",
        "3, 1.7320508075688772",
        "4, 2.0000000000000000"
    })
    @Tag("sqrt")
    void sqrt(long input, double expectedResult) {
        double actualResult = calculator.set(input).sqrt().doubleValue();

        assertEquals(expectedResult, actualResult, 1e-16);
    }
// end::parameterized_tests[]

    @ParameterizedTest(name = "sqrt({0}) = {1}")
    @CsvFileSource(resources = "/sqrt.csv")
    @Tag("sqrt")
    void sqrtFromFile(long input, double expectedResult) {
        double actualResult = calculator.set(input).sqrt().doubleValue();

        assertEquals(expectedResult, actualResult, 1e-16);
    }

// tag::repeated_tests[]
    @RepeatedTest(10)
    @Tag("power")
    void flakyTest() {
        double actualResult = calculator.set(Math.random()).power(2).doubleValue();

        assertEquals(0.0, actualResult, 0.5);
    }
// end::repeated_tests[]

// tag::jupiter_intro[]
}
// end::jupiter_intro[]
