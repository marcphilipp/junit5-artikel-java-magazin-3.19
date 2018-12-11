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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::test_class[]
@ExtendWith(MockitoExtension.class)
class CurrencyConverterTests {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency USD = Currency.getInstance("USD");

    @Test
    void convertsEurToUsd(@Mock ExchangeRateService exchangeRateService) {
        var originalAmount = new MonetaryAmount("100.00", EUR);
        when(exchangeRateService.getRate("EUR", "USD")).thenReturn(1.139157);

        var currencyConverter = new CurrencyConverter(exchangeRateService);
        var convertedAmount = currencyConverter.convert(originalAmount, USD);

        assertEquals(new BigDecimal("113.92"), convertedAmount.getValue());
        assertEquals(USD, convertedAmount.getCurrency());
    }
}
// end::test_class[]
