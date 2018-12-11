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

import java.math.BigDecimal;
import java.math.MathContext;

public class Calculator {

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    private BigDecimal value;

    public Calculator() {
        this(0);
    }

    public Calculator(long value) {
        set(BigDecimal.valueOf(value));
    }

    public BigDecimal get() {
        return value;
    }

    public long longValue() {
        return value.longValue();
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    public Calculator set(long value) {
        return set(BigDecimal.valueOf(value));
    }

    public Calculator set(double value) {
        return set(BigDecimal.valueOf(value));
    }

    public Calculator add(long addend) {
        return set(value.add(BigDecimal.valueOf(addend)));
    }

    public Calculator multiply(long factor) {
        return set(value.multiply(BigDecimal.valueOf(factor)));
    }

    public Calculator divide(long divisor) {
        return set(value.divide(BigDecimal.valueOf(divisor), MATH_CONTEXT));
    }

    public Calculator power(int exponent) {
        return set(value.pow(exponent, MATH_CONTEXT));
    }

    public Calculator sqrt() {
        return set(value.sqrt(MATH_CONTEXT));
    }

    public Calculator set(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("cannot set value to null");
        }
        this.value = value;
        return this;
    }
}
