///**
// *  Copyright 2014 Diego Ceccarelli
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// * 
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//
///**
// *  Copyright 2014 Diego Ceccarelli
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// * 
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//package it.cnr.isti.hpc.erd.cli;
//
//import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
//import it.cnr.isti.hpc.erd.ErdDocument;
//import it.cnr.isti.hpc.erd.text.CharacterOffsetToByteOffsetCalculator;
//import it.cnr.isti.hpc.io.reader.RecordReader;
//import it.cnr.isti.hpc.io.reader.TsvRecordParser;
//import it.cnr.isti.hpc.io.reader.TsvTuple;
//import it.cnr.isti.hpc.property.ProjectProperties;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
// * 
// *         Created on Mar 15, 2014
// */
//public class ConvertGroundTruthFromByteOffsetToCharOffsetCLI extends
//		AbstractCommandLineInterface {
//
//	private static final Logger logger = LoggerFactory
//			.getLogger(ConvertGroundTruthFromByteOffsetToCharOffsetCLI.class);
//
//	private static String[] params = new String[] { "docs", INPUT, OUTPUT };
//	private static String usage = "java -cp $jar it.cnr.isti.hpc.erd.cli.ConvertGroundTruthFromByteOffsetToCharOffsetCLI -docs documents -input ground truth -output ground truth norm";
//
//	private static ProjectProperties properties = new ProjectProperties(
//			ConvertGroundTruthFromByteOffsetToCharOffsetCLI.class);
//
//	public ConvertGroundTruthFromByteOffsetToCharOffsetCLI(String[] args) {
//		super(args, params, usage);
//
//	}
//
//	public static void main(String[] args) throws URISyntaxException,
//			IOException {
//		ConvertGroundTruthFromByteOffsetToCharOffsetCLI cli = new ConvertGroundTruthFromByteOffsetToCharOffsetCLI(
//				args);
//		RecordReader<TsvTuple> reader = new RecordReader<TsvTuple>(
//				cli.getInput(), new TsvRecordParser("docid", "spot", "start",
//						"end"));
//		RecordReader<ErdDocument> docs = new RecordReader<ErdDocument>(
//				cli.getParam("docs"), ErdDocument.class);
//		Iterator<ErdDocument> d = docs.iterator();
//
//		Map<String, List<TsvTuple>> golden = new LinkedHashMap<String, List<TsvTuple>>();
//
//		for (TsvTuple t : reader) {
//			String docid = t.get("docid");
//			if (!golden.containsKey(docid)) {
//				golden.put(docid, new LinkedList<TsvTuple>());
//			}
//			golden.get(docid).add(t);
//		}
//		Iterator<List<TsvTuple>> tsv = golden.values().iterator();
//		Iterator<ErdDocument> r = docs.iterator();
//		cli.openOutput();
//		while (r.hasNext()) {
//			ErdDocument ed = r.next();
//			String doc = ed.getText();
//			// System.out.println("DOC ID " + ed.getTextId());
//			CharacterOffsetToByteOffsetCalculator o = new CharacterOffsetToByteOffsetCalculator()
//					.loadString(doc);
//			List<TsvTuple> tuples = tsv.next();
//			for (TsvTuple tuple : tuples) {
//				// System.out.println("tuple " + tuple.get("docid"));
//				int start = o.getOffset(tuple.getInt("start"));
//				int end = o.getOffset(tuple.getInt("end"));
//
//				cli.writeLineInOutput(tuple.get("docid") + "\t"
//						+ tuple.get("spot") + "\t" + start + "\t" + end);
//			}
//		}
//		cli.closeOutput();
//
//	}
// }
