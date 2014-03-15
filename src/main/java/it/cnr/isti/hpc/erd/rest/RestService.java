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
package it.cnr.isti.hpc.erd.rest;

import it.cnr.isti.hpc.erd.Annotation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */

@Path("")
public class RestService {
	@POST
	@Path("/shortTrac")
	@Produces({ MediaType.TEXT_PLAIN })
	public String annotatePost(@FormParam("runID") String runId,
			@FormParam("TextID") String textId, @FormParam("Text") String text) {
		List<Annotation> annotations = annotate(runId, textId, text);

		return encodeAnnotations(annotations);
	}

	@GET
	@Path("/shortTrac")
	@Produces({ MediaType.TEXT_PLAIN })
	public String annotateGet(@QueryParam("runID") String runId,
			@QueryParam("TextID") String textId, @QueryParam("Text") String text) {
		List<Annotation> annotations = annotate(runId, textId, text);

		return encodeAnnotations(annotations);
	}

	private List<Annotation> annotate(String runId, String textID, String text) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		Annotation a = new Annotation();
		a.setQid("Q01");
		a.setInterpretationSet(0);
		a.setPrimaryId("/m/0fd4x");
		a.setMentionText("total recall");
		a.setScore(0.98f);
		Annotation b = new Annotation();
		b.setQid("Q01");
		b.setInterpretationSet(0);
		b.setPrimaryId("/m/0tc7");
		b.setMentionText("arnold schwarzenegger");
		b.setScore(0.95f);
		annotations.add(a);
		annotations.add(b);
		return annotations;
	}

	private String encodeAnnotations(List<Annotation> annotations) {
		StringBuilder sb = new StringBuilder();
		for (Annotation a : annotations) {
			sb.append(a.toTsv()).append('\n');

		}
		return sb.toString();
	}

}
