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

import it.cnr.isti.hpc.mapdb.MapDB;

import java.io.File;
import java.util.Map;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 15, 2014
 */
public class WikipediaToFreebase {
	Map<String, String> map;
	Map<String, String> labels;

	public WikipediaToFreebase(String folder) {
		File dir = new File(folder);
		File mapfile = new File(dir, "mapdb");
		MapDB db = new MapDB(mapfile);
		map = db.getCollection("index");
		labels = db.getCollection("label");

	}

	public String getLabel(String wikiid) {
		String freebase = map.get(wikiid);
		if (freebase == null)
			return null;

		String label = labels.get(freebase);
		return label;
	}

	public boolean hasEntity(String wikilabel) {
		return map.containsKey(wikilabel);
	}

	public String getFreebaseId(String wikilabel) {
		String freebase = map.get(wikilabel);
		return freebase;
	}

	public static void main(String[] args) {
		WikipediaToFreebase w2f = new WikipediaToFreebase("mapdb");
		System.out.println(w2f.getFreebaseId("Diego_Maradona"));
		System.out.println(w2f.getLabel("Diego_Maradona"));
	}

}
