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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ_WRITE;

// tag::test_class[]
@Execution(CONCURRENT)
class SharedResourcesDemo {
	// end::test_class[]
	private Properties backup;

	@BeforeEach
	void backup() {
		backup = new Properties();
		backup.putAll(System.getProperties());
	}

	@AfterEach
	void restore() {
		System.setProperties(backup);
	}

	// tag::test_class[]
	@Test
	@ResourceLock(value = "system.properties", mode = READ)
	void customPropertyIsNotSetByDefault() {
		assertNull(System.getProperty("my.prop"));
	}

	@Test
	@ResourceLock(value = "system.properties", mode = READ_WRITE)
	void canSetCustomPropertyToFoo() {
		System.setProperty("my.prop", "foo");
		assertEquals("foo", System.getProperty("my.prop"));
	}

	@Test
	@ResourceLock(value = "system.properties", mode = READ_WRITE)
	void canSetCustomPropertyToBar() {
		System.setProperty("my.prop", "bar");
		assertEquals("bar", System.getProperty("my.prop"));
	}
}
// end::test_class[]
