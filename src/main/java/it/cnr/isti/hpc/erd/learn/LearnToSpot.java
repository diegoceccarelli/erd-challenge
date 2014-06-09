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
package it.cnr.isti.hpc.erd.learn;

import it.cnr.isti.hpc.erd.ErdDocument;
import it.cnr.isti.hpc.io.reader.RecordReader;
import it.cnr.isti.hpc.io.reader.TsvRecordParser;
import it.cnr.isti.hpc.io.reader.TsvTuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Jun 5, 2014
 */
public class LearnToSpot {

	public static void main(String[] args) {
		RecordReader<ErdDocument> documents = new RecordReader<ErdDocument>(
				"/tmp/erd-document.json", ErdDocument.class);
		RecordReader<TsvTuple> assessment = new RecordReader<TsvTuple>(
				"/tmp/goldentruth.tsv", new TsvRecordParser("docid", "spot",
						"start", "end"));
		Map<String, List<TsvTuple>> goldenTruth = new HashMap<String, List<TsvTuple>>();
		for (TsvTuple tuple : assessment) {
			String docid = tuple.get("docid");
			if (!goldenTruth.containsKey(docid)) {
				goldenTruth.put(docid, new ArrayList<TsvTuple>());
			}
			goldenTruth.get(docid).add(tuple);
		}
		for (ErdDocument d : documents) {
			if (!goldenTruth.containsKey(d)) {
				continue;
			}

		}

	}

}
