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
package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

// tag::test_class[]
@TestInstance(Lifecycle.PER_CLASS)
class KotlinCalculatorTests {

    private lateinit var calculator: Calculator

    @BeforeAll
    fun createCalculator() {
        calculator = Calculator()
    }

    @Test
    @Tag("input-validation")
    fun `cannot set value to null`() {
        val exception = assertThrows<IllegalArgumentException> { calculator.set(null) }

        assertAll(
                { assertEquals("cannot set value to null", exception.message) },
                { assertNull(exception.cause) }
        )
    }
}
// end::test_class[]
