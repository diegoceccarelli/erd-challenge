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

import it.cnr.isti.hpc.erd.file.FileAnnotation;
import it.cnr.isti.hpc.io.reader.RecordReader;
import it.cnr.isti.hpc.property.ProjectProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on May 30, 2014
 */
public class CachedAnnotator {

	private static final Logger logger = LoggerFactory
			.getLogger(CachedAnnotator.class);
	Map<String, List<ErdAnnotation>> annotation;
	static ProjectProperties properties = new ProjectProperties(
			CachedAnnotator.class);

	public CachedAnnotator() {
		load(properties.get("annotations"));
	}

	private void load(String fileAnnotations) {
		annotation = new HashMap<String, List<ErdAnnotation>>();
		logger.info("loading the annotation");
		RecordReader<FileAnnotation> reader = new RecordReader<FileAnnotation>(
				fileAnnotations, FileAnnotation.class);
		for (FileAnnotation a : reader) {
			if (!annotation.containsKey(a.getDocId())) {
				annotation.put(a.getDocId(), new ArrayList<ErdAnnotation>());
				logger.info("loading doc {} ", a.getDocId());
			}
			if (a.getFreebaseId() == null) {
				logger.warn("ignoring {}, no freebaseid", a);
				continue;
			}
			ErdAnnotation erd = new ErdAnnotation();
			erd.setDocId(a.getDocId());
			erd.setEnd(a.getEnd());
			erd.setStart(a.getStart());
			erd.setMentionText(a.getMention());
			erd.setPrimaryId(a.getFreebaseId());
			erd.setSecondId(a.getEntity());
			erd.setScore1(a.getConfidence());

			annotation.get(a.getDocId()).add(erd);

		}

	}

	public CachedAnnotator(String fileAnnotations) {
		load(fileAnnotations);
	}

	public List<ErdAnnotation> annotateLongDocument(String runId,
			String textId, String text) {
		List<ErdAnnotation> annotations = Collections.emptyList();
		if (annotation.containsKey(textId)) {
			logger.info("hit! {} ", textId);
			annotations = annotation.get(textId);

		} else {
			logger.info("miss {} ", textId);
		}
		try {
			Thread.sleep(8000 + (int) (Math.random() * 4000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return annotations;
	}

	public List<Annotation> annotate(String runId, String textId, String text) {
		// TODO Auto-generated method stub
		return null;
	}

}
