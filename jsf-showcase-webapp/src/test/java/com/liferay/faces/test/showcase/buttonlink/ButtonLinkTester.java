/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.test.showcase.buttonlink;

import java.util.Locale;

import org.junit.Assume;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.BrowserStateAsserter;
import com.liferay.faces.test.showcase.TesterBase;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class ButtonLinkTester extends TesterBase {

	protected static final String generalButton1Xpath = "//input[contains(@src,'jsf-logo-small.png')][@type='image']";
	protected static final String generalLink1Xpath =
		"//img[contains(@src,'jsf-logo-small.png')]/parent::a[contains(text(),'Text for a link')]";
	protected static final String generalLink2Xpath =
		"//a[contains(text(),'Text for a link')][not(./img[contains(@src,'jsf-logo-small.png')])]";

	protected void runButtonLinkGeneralTest(String componentName, String buttonLink1xpath, String buttonLink2xpath)
		throws Exception {

		// Skip the test if it's not the JSF showcase. other showcases should
		// include their own test for this use case.
		Assume.assumeTrue(SHOWCASE_CONTEXT_URL.contains("jsf"));

		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, componentName, "general");

		// Test that both buttons/links render on the page visibly and are clickable.
		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementDisplayed(buttonLink1xpath);
		clickButtonLink(browserDriver, buttonLink1xpath);
		browserStateAsserter.assertElementDisplayed(buttonLink2xpath);
		clickButtonLink(browserDriver, buttonLink2xpath);

		String lowerCaseComponentName = componentName.toLowerCase(Locale.ENGLISH);

		// Test that the images render on the link use cases successfully.
		if (lowerCaseComponentName.contains("link")) {
			assertImageRendered(browserDriver, browserStateAsserter, getExampleImageXpath("children"));
		}
	}

	protected void runButtonLinkImmediateTest(String componentName) throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, componentName, "immediate");

		// Test that the value submits successfully and the valueChangeListener
		// method is called during the
		// APPLY_REQUEST_VALUES phase.
		browserDriver.clickElementAndWaitForRerender(
			"(//*[contains(@onclick,'mojarra.ab')][contains(text(),'Submit') or contains(@value,'Submit')])[1]");

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementDisplayed(immediateMessage1Xpath);

		// Test that the value submits successfully and the valueChangeListener
		// method is called during the
		// PROCESS_VALIDATIONS phase.
		browserDriver.clickElementAndWaitForRerender(
			"(//*[contains(@onclick,'mojarra.ab')][contains(text(),'Submit') or contains(@value,'Submit')])[2]");
		browserStateAsserter.assertElementDisplayed("//li[contains(text(),'INVOKE_APPLICATION ')]");
	}

	protected void runButtonLinkNavigationParamTest(String componentName) throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, componentName, "navigation");

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementDisplayed("//pre/span[text()='foo=']");

		String toParamPageXpath = "//*[contains(text(),'To Param page') or contains(@value,'To Param page')]";
		String backToNavigationXpath =
			"//*[contains(text(),'Back to Navigation with foo=1234') or contains(@value,'Back to Navigation with foo=1234')]";
		testNavigationPage(browserDriver, browserStateAsserter, toParamPageXpath, backToNavigationXpath);
		testParamPage(browserDriver, browserStateAsserter, toParamPageXpath, backToNavigationXpath);

		if (componentName.startsWith("command")) {

			// Click "To Param page" and check that it opens the Param page
			String ajaxCheckbox1Xpath = "//label[contains(text(),'Ajax')]/input[@type='checkbox']";
			browserDriver.clickElementAndWaitForRerender(ajaxCheckbox1Xpath);
			testNavigationPage(browserDriver, browserStateAsserter, toParamPageXpath, backToNavigationXpath);

			// Click "Back to Navigation with foo=1234" and assert that the value "1234" submits successfully.
			browserDriver.clickElementAndWaitForRerender(ajaxCheckbox1Xpath);
			testParamPage(browserDriver, browserStateAsserter, toParamPageXpath, backToNavigationXpath);
		}
	}

	protected void runVariousStylesTest(String componentName) throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, componentName, "various-styles");

		// Test the values and classes of every button.
		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][@value='Primary' or contains(.,'Primary')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-info')][@value='Info' or contains(.,'Info')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-success')][@value='Success' or contains(.,'Success')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-warning')][@value='Warning' or contains(.,'Warning')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-danger')][@value=' Danger' or contains(.,' Danger')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-link')][@value='Link' or contains(.,'Link')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][contains(@class,'btn-lg') or contains(@class,'btn-large')][@value='Large' or contains(.,'Large')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-lg') or contains(@class,'btn-large')][@value='Large' or contains(.,'Large')][2]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][@value='Default' or contains(.,'Default')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ') or @class='btn'][@value='Default' or contains(.,'Default')][2]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][contains(@class,'btn-sm') or contains(@class,'btn-small')][@value='Small' or contains(.,'Small')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-sm') or contains(@class,'btn-small')][@value='Small' or contains(.,'Small')][2]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][contains(@class,'btn-mini') or contains(@class,'btn-xs')][@value='Mini' or contains(.,'Mini')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-mini') or contains(@class,'btn-xs')][@value='Mini' or contains(.,'Mini')][2]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][@value='Disabled' or contains(.,'Disabled')]");
		browserStateAsserter.assertElementDisplayed(
			"(//*[contains(@class,'btn ') or @class='btn'][@value='Disabled' or contains(.,'Disabled')])[2]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][@value='Block' or contains(.,'Block')]");
		browserStateAsserter.assertElementDisplayed(
			"//*[contains(@class,'btn ')][contains(@class,'btn-block')][@value='Block' or contains(.,'Block')][2]");

		// Click first button.
		clickButtonLink(browserDriver,
			"//*[contains(@class,'btn ')][contains(@class,'btn-primary')][@value='Primary' or contains(.,'Primary')]");
	}

	private void clickButtonLink(BrowserDriver browserDriver, String xpath) {

		WebElement webElement = browserDriver.findElementByXpath(xpath);
		String tagName = webElement.getTagName();
		String onclick = webElement.getAttribute("onclick");
		browserDriver.clickElement(xpath);

		// If the clicking the button/link will cause the page to reload.
		if (!"button".equals(tagName) || ((onclick != null) && !"".equals(onclick))) {

			browserDriver.waitFor(ExpectedConditions.stalenessOf(webElement));
			waitForShowcasePageReady(browserDriver);
		}
	}

	private void testNavigationPage(BrowserDriver browserDriver, BrowserStateAsserter browserStateAsserter,
		String toParamPageXpath, String backToNavigationXpath) {

		// Click "To Param page" and check that it opens the Param page.
		browserDriver.clickElement(toParamPageXpath);
		browserStateAsserter.assertElementDisplayed(backToNavigationXpath);
		waitForShowcasePageReady(browserDriver);
	}

	private void testParamPage(BrowserDriver browserDriver, BrowserStateAsserter browserStateAsserter,
		String toParamPageXpath, String backToNavigationXpath) {

		// Click "Back to Navigation with foo=1234" and assert that the value "1234" appears in the model value.
		browserDriver.clickElement(backToNavigationXpath);
		browserStateAsserter.assertElementDisplayed(toParamPageXpath);
		browserStateAsserter.assertElementDisplayed("//pre/span[text()='foo=1234']");
		waitForShowcasePageReady(browserDriver);
	}
}
