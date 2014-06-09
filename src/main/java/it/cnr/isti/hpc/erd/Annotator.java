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
package it.cnr.isti.hpc.erd;

import it.cnr.isti.hpc.dexter.rest.client.DexterRestClient;
import it.cnr.isti.hpc.dexter.rest.domain.AnnotatedDocument;
import it.cnr.isti.hpc.dexter.rest.domain.AnnotatedSpot;
import it.cnr.isti.hpc.erd.text.OffsetToLinePointConverter;
import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.property.ProjectProperties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */
public class Annotator {

	DexterRestClient client = null;
	WikipediaToFreebase map = null;

	private static BufferedWriter log = IOUtils
			.getPlainOrCompressedUTF8Writer("erd-documents"
					+ System.currentTimeMillis() + ".json");
	Gson gson = new Gson();
	private static ProjectProperties properties = new ProjectProperties(
			Annotator.class);
	private static double confidence = properties.getDouble("min.confidence");

	private static final Logger logger = LoggerFactory
			.getLogger(Annotator.class);

	public Annotator() {

		try {
			client = new DexterRestClient(properties.get("dexter.server"));

			client.setWikinames(true);

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		map = new WikipediaToFreebase("mapdb");
	}

	public void tagme() {
		client.addParams("dsb", "tagme");
		client.addParams("epsilon", "0.8");

		client.addParams("window-size", "120");
		client.addParams("alpha", "0.5");
	}

	public void wikiminer() {
		client.addParams("dsb", "wikiminer");
	}

	public void okkam() {
		client.addParams("dsb", "okkam");
	}

	/**
	 * SHORT
	 */
	public List<Annotation> annotate(String runId, String textID, String text) {

		AnnotatedDocument ad = client.annotate(text, 500);
		logger.info("text:\n\n {} \n\n", text);
		List<AnnotatedSpot> spots = ad.getSpots();
		logger.info("spots annotated:\n\n {} \n\n", spots.size());
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (AnnotatedSpot spot : spots) {
			System.out.println("considering spot " + spot);
			if (spot.getScore() < confidence) {
				logger.info("removing {} < confidence {}", spot.getMention(),
						confidence);
				continue;
			}
			String freebaseid;
			if (!map.hasEntity(spot.getWikiname())) {
				continue;
			}
			freebaseid = map.getFreebaseId(spot.getWikiname());
			logger.info("{} -> {} ", spot.getWikiname(), freebaseid);
			Annotation a = new Annotation();
			a.setQid(textID);
			a.setPrimaryId(freebaseid);
			a.setInterpretationSet(0);
			a.setMentionText(spot.getMention());
			a.setScore((float) spot.getScore());
			annotations.add(a);

		}

		return annotations;
	}

	private synchronized void writeLog(ErdDocument erdDocument) {

		try {
			log.write(gson.toJson(erdDocument));
			log.newLine();
			log.flush();
		} catch (IOException e) {
			System.out.println("writing the log file");
			e.printStackTrace();
		}
	}

	public List<ErdAnnotation> annotateLongDocument(String runId,
			String textId, String text) {
		if (runId.contains("@1")) {
			tagme();
		}
		if (runId.contains("@2")) {
			wikiminer();
		}

		if (runId.contains("@3")) {
			okkam();
		}
		if (runId.contains("jsonlong")) {
			okkam();
		}

		AnnotatedDocument ad = client.annotate(text, 500);
		ErdDocument erdDocument = new ErdDocument(runId, textId, text);
		OffsetToLinePointConverter converter = null;
		try {
			converter = new OffsetToLinePointConverter().loadString(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<AnnotatedSpot> spots;
		if (ad == null) {
			System.out.println("client return " + ad);
			spots = Collections.emptyList();

		} else {
			spots = ad.getSpots();
		}

		List<ErdAnnotation> annotations = new ArrayList<ErdAnnotation>();
		// logger.info("text:\n\n {} \n\n", text);
		// logger.info("spots annotated:\n\n {} \n\n", spots.size());
		for (AnnotatedSpot spot : spots) {
			String freebaseid;
			if (spot.getScore() < confidence) {
				logger.info("removing {} < confidence {}", spot.getMention(),
						confidence);
				continue;
			}
			if (!map.hasEntity(spot.getWikiname())) {
				// continue;
				freebaseid = "NIL";
			} else {
				freebaseid = map.getFreebaseId(spot.getWikiname());
			}
			logger.info("{} -> {} ", spot.getWikiname(), freebaseid);
			ErdAnnotation a = new ErdAnnotation();
			a.setDocId(textId);
			a.setPrimaryId(freebaseid);
			a.setSecondId(spot.getWikiname());
			a.setMentionText(spot.getMention());
			int byteoffsetStart = converter.getCodepoint(spot.getStart());
			a.setStart(byteoffsetStart);
			int byteoffsetEnd = converter.getCodepoint(spot.getEnd());

			a.setEnd(byteoffsetEnd);
			a.setScore1(spot.getScore());
			a.setScore2(spot.getCommonness());
			annotations.add(a);

		}
		erdDocument.setAnnotations(annotations);
		writeLog(erdDocument);
		return annotations;
	}

}
