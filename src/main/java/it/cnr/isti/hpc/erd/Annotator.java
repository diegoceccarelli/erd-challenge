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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */
public class Annotator {

	public Annotator() {

	}

	public List<Annotation> annotate(String runId, String textID, String text) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		Annotation a = new Annotation();
		a.setQid(textID);
		a.setInterpretationSet(0);
		a.setPrimaryId("/m/0fd4x");
		a.setMentionText("total recall");
		a.setScore(0.98f);
		Annotation b = new Annotation();
		b.setQid(textID);
		b.setInterpretationSet(0);
		b.setPrimaryId("/m/0tc7");
		b.setMentionText("arnold schwarzenegger");
		b.setScore(0.95f);
		annotations.add(a);
		annotations.add(b);
		return annotations;
	}

}
