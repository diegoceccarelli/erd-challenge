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
package it.cnr.isti.hpc.erd.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.erd.ErdDocument;
import it.cnr.isti.hpc.erd.client.ErdClient;
import it.cnr.isti.hpc.io.reader.RecordReader;
import it.cnr.isti.hpc.property.ProjectProperties;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */
public class PerformErdAnnotationCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(PerformErdAnnotationCLI.class);

	private static String[] params = new String[] { "url", INPUT, OUTPUT };
	private static String usage = "java -cp $jar it.cnr.isti.hpc.erd.cli.PerformErdAnnotationCLI -url rest-endpoint -input erd-documents.json";

	private static ProjectProperties properties = new ProjectProperties(
			PerformErdAnnotationCLI.class);

	public PerformErdAnnotationCLI(String[] args) {
		super(args, params, usage);

	}

	public static void main(String[] args) throws URISyntaxException {
		PerformErdAnnotationCLI cli = new PerformErdAnnotationCLI(args);

		RecordReader<ErdDocument> reader = new RecordReader<ErdDocument>(
				cli.getInput(), ErdDocument.class);
		String url = cli.getParam("url");
		ErdClient client = new ErdClient(url);
		cli.openOutput();

		for (ErdDocument doc : reader) {
			doc.setRunId("lol@3");
			logger.info("annotate doc {} ", doc.getTextId());
			cli.writeInOutput(client.query(doc));
		}
		cli.closeOutput();

	}
}
