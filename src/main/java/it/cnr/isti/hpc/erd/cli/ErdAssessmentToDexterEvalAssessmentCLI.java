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
import it.cnr.isti.hpc.dexter.rest.client.DexterRestClient;
import it.cnr.isti.hpc.erd.FreebaseToWikipedia;
import it.cnr.isti.hpc.io.reader.RecordReader;
import it.cnr.isti.hpc.io.reader.TsvRecordParser;
import it.cnr.isti.hpc.io.reader.TsvTuple;
import it.cnr.isti.hpc.property.ProjectProperties;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */
public class ErdAssessmentToDexterEvalAssessmentCLI extends
		AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(ErdAssessmentToDexterEvalAssessmentCLI.class);

	private static String[] params = new String[] { "input", "output" };
	private static String usage = "java -cp $jar it.cnr.isti.hpc.erd.cli.ErdAssessmentToDexterEvalAssessmentCLI -input erdassessment.tsv -output dexter-eval-golden-truth.tsv";

	private static ProjectProperties properties = new ProjectProperties(
			ErdAssessmentToDexterEvalAssessmentCLI.class);

	public ErdAssessmentToDexterEvalAssessmentCLI(String[] args) {
		super(args, params, usage);

	}

	public static void main(String[] args) throws URISyntaxException {
		ErdAssessmentToDexterEvalAssessmentCLI cli = new ErdAssessmentToDexterEvalAssessmentCLI(
				args);

		RecordReader<TsvTuple> reader = new RecordReader<TsvTuple>(
				cli.getInput(), new TsvRecordParser("doc-id", "start", "end",
						"freebase-id", "freebase-uri", "spot"));
		FreebaseToWikipedia f2w = new FreebaseToWikipedia("mapdb");
		DexterRestClient client = new DexterRestClient(
				properties.get("dexter.server"));
		cli.openOutput();
		for (TsvTuple tuple : reader) {
			String freebaseid = tuple.get("freebase-id");
			freebaseid = freebaseid.replaceAll("https://www.freebase.com/", "");
			String wikiname = f2w.getWikiId(freebaseid);

			if (wikiname == null) {
				logger.warn("no wikiname for freebaseid {} ", freebaseid);
				continue;
			}
			int wikiid = client.getId(wikiname);
			cli.writeInOutput(tuple.get("doc-id"));
			cli.writeInOutput("\t");
			cli.writeInOutput(tuple.get("spot"));
			cli.writeInOutput("\t");
			cli.writeInOutput(tuple.get("start"));
			cli.writeInOutput("\t");
			cli.writeInOutput(tuple.get("end"));

			cli.writeInOutput("\t");
			cli.writeInOutput(String.valueOf(wikiid));
			cli.writeInOutput("\t");
			cli.writeInOutput(wikiname);
			cli.writeInOutput("\t");
			cli.writeInOutput(String.valueOf(1));
			cli.writeLineInOutput("");
		}

		cli.closeOutput();

	}
}
