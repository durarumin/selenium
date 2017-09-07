// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.openqa.selenium.support.pagefactory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openqa.selenium.testing.TestUtilities.catchThrowable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class ByAllTest {

  private WebDriver driver;

  @Before
  public void initDriver() {
    driver = mock(WebDriver.class);
  }

  @Test
  public void findElementZeroBy() {
    ByAll by = new ByAll();
    Throwable t = catchThrowable(() -> by.findElement(driver));
    assertThat(t, instanceOf(NoSuchElementException.class));
  }

  @Test
  public void findElementsZeroBy() {
    ByAll by = new ByAll();
    assertTrue(by.findElements(driver).isEmpty());
  }

  @Test
  public void findElementOneBy() {
    final WebElement elem1 = mock(WebElement.class, "webElement1");
    final WebElement elem2 = mock(WebElement.class, "webElement2");
    final List<WebElement> elems12 = new ArrayList<>();
    elems12.add(elem1);
    elems12.add(elem2);

    when(driver.findElements(By.name("cheese"))).thenReturn(elems12);

    ByAll by = new ByAll(By.name("cheese"));
    assertThat(by.findElement(driver), equalTo(elem1));
  }

  @Test
  public void findElementsOneBy() {
    final WebElement elem1 = mock(WebElement.class, "webElement1");
    final WebElement elem2 = mock(WebElement.class, "webElement2");
    final List<WebElement> elems12 = new ArrayList<>();
    elems12.add(elem1);
    elems12.add(elem2);

    when(driver.findElements(By.name("cheese"))).thenReturn(elems12);

    ByAll by = new ByAll(By.name("cheese"));
    assertThat(by.findElements(driver), equalTo(elems12));
  }

  @Test
  public void findElementOneByEmpty() {
    final List<WebElement> elems = new ArrayList<>();

    when(driver.findElements(By.name("cheese"))).thenReturn(elems);

    ByAll by = new ByAll(By.name("cheese"));
    Throwable t = catchThrowable(() -> by.findElement(driver));
    assertThat(t, instanceOf(NoSuchElementException.class));
  }

  @Test
  public void findElementsOneByEmpty() {
    when(driver.findElements(By.name("cheese"))).thenReturn(new ArrayList<>());

    ByAll by = new ByAll(By.name("cheese"));
    assertTrue(by.findElements(driver).isEmpty());
  }

  @Test
  public void findFourElementBy() {
    final WebElement elem1 = mock(WebElement.class, "webElement1");
    final WebElement elem2 = mock(WebElement.class, "webElement2");
    final WebElement elem3 = mock(WebElement.class, "webElement3");
    final WebElement elem4 = mock(WebElement.class, "webElement4");
    final List<WebElement> elems12 = new ArrayList<>();
    elems12.add(elem1);
    elems12.add(elem2);
    final List<WebElement> elems34 = new ArrayList<>();
    elems34.add(elem3);
    elems34.add(elem4);

    when(driver.findElements(By.name("cheese"))).thenReturn(elems12);
    when(driver.findElements(By.name("photo"))).thenReturn(elems34);

    ByAll by = new ByAll(By.name("cheese"), By.name("photo"));
    assertThat(by.findElement(driver), equalTo(elem1));
  }

  @Test
  public void findFourElementsByAny() {
    final WebElement elem1 = mock(WebElement.class, "webElement1");
    final WebElement elem2 = mock(WebElement.class, "webElement2");
    final WebElement elem3 = mock(WebElement.class, "webElement3");
    final WebElement elem4 = mock(WebElement.class, "webElement4");
    final List<WebElement> elems12 = new ArrayList<>();
    elems12.add(elem1);
    elems12.add(elem2);
    final List<WebElement> elems34 = new ArrayList<>();
    elems34.add(elem3);
    elems34.add(elem4);
    final List<WebElement> elems1234 = new ArrayList<>();
    elems1234.addAll(elems12);
    elems1234.addAll(elems34);

    when(driver.findElements(By.name("cheese"))).thenReturn(elems12);
    when(driver.findElements(By.name("photo"))).thenReturn(elems34);

    ByAll by = new ByAll(By.name("cheese"), By.name("photo"));
    assertThat(by.findElements(driver), equalTo(elems1234));
  }

  @Test
  public void testEquals() {
    assertThat(new ByAll(By.id("cheese"), By.name("photo")),
        equalTo(new ByAll(By.id("cheese"), By.name("photo"))));
  }
}
