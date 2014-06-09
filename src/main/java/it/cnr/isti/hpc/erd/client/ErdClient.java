/**
 *  Copyright 2014 Diego Ceccarelli
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package it.cnr.isti.hpc.erd.client;

import it.cnr.isti.hpc.erd.ErdDocument;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Jun 5, 2014
 */
public class ErdClient {

	private final String url;

	private static final Logger logger = LoggerFactory
			.getLogger(ErdClient.class);
	Client c = null;
	String runId = "tagme@2";

	private static int count = 0;

	public ErdClient(String url) {
		this.url = url;
		ClientConfig cc = new DefaultClientConfig();
		cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		c = Client.create(cc);

	}

	public String query(ErdDocument doc) {

		logger.info("runId {}", runId);
		logger.info("{} TextId {}", count++, doc.getTextId());
		// logger.info("Text {}...", doc.getText().substring(0, 100));
		WebResource r = c.resource(url);
		Form part = new Form();
		part.add("runID", runId);
		part.add("TextID", doc.getTextId());
		part.add("Text", doc.getText());
		String response = r.type(MediaType.APPLICATION_FORM_URLENCODED).post(
				String.class, part);

		System.out.println(response);
		return response;
	}
}
