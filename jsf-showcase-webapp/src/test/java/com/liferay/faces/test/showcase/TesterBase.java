/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.test.showcase;

import com.liferay.faces.test.IntegrationTesterBase;
import com.liferay.faces.test.TestUtil;


/**
 * @author  Kyle Stiemann
 */
public class TesterBase extends IntegrationTesterBase {

	// Protected Constants
	protected static final String TEST_CONTEXT_URL;

	static {

		String defaultContext = "/jsf-showcase-webapp-3.0.0-SNAPSHOT/web/guest/showcase/-/component/h";

		if (CONTAINER.contains("liferay")) {
			defaultContext = "/web/guest/jsf-showcase/-/jsf-tag/h";
		}
		else if (CONTAINER.contains("pluto")) {

			// Note: the showcase tests will not work in pluto because pluto does not support friendly URLs.
			defaultContext = DEFAULT_PLUTO_CONTEXT + "/jsf-showcase";
		}

		String context = TestUtil.getSystemPropertyOrDefault("integration.context", defaultContext);
		TEST_CONTEXT_URL = BASE_URL + context;
	}
}
