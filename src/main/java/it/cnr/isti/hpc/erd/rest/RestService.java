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
import it.cnr.isti.hpc.erd.Annotator;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */

@Path("")
public class RestService {

	private final Annotator annotator = new Annotator();

	@POST
	@Path("/shortTrack")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.TEXT_PLAIN })
	public String annotatePost(@FormDataParam("runID") String runId,
			@FormDataParam("TextID") String textId,
			@FormDataParam("Text") String text) {

		List<Annotation> annotations = annotator.annotate(runId, textId, text);

		return encodeAnnotations(annotations);
	}

	@GET
	@Path("/shortTrack")
	@Produces({ MediaType.TEXT_PLAIN })
	public String annotateGet(@QueryParam("runID") String runId,
			@QueryParam("TextID") String textId, @QueryParam("Text") String text) {
		List<Annotation> annotations = annotator.annotate(runId, textId, text);

		return encodeAnnotations(annotations);
	}

	private String encodeAnnotations(List<Annotation> annotations) {
		StringBuilder sb = new StringBuilder();
		for (Annotation a : annotations) {
			sb.append(a.toTsv()).append('\n');

		}
		return sb.toString();
	}

}
