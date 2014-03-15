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
package it.cnr.isti.hpc.erd;

import it.cnr.isti.hpc.dexter.rest.client.DexterRestClient;
import it.cnr.isti.hpc.dexter.rest.domain.AnnotatedDocument;
import it.cnr.isti.hpc.dexter.rest.domain.AnnotatedSpot;
import it.cnr.isti.hpc.property.ProjectProperties;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */
public class Annotator {

	DexterRestClient client = null;
	WikipediaToFreebase map = null;

	private static final Logger logger = LoggerFactory
			.getLogger(Annotator.class);

	ProjectProperties properties = new ProjectProperties(Annotator.class);

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

	public List<Annotation> annotate(String runId, String textID, String text) {
		AnnotatedDocument ad = client.annotate(text, 100);
		List<AnnotatedSpot> spots = ad.getSpots();
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (AnnotatedSpot spot : spots) {
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

}
