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
public class FreebaseToWikipedia {
	Map<String, String> map;

	public FreebaseToWikipedia(String folder) {
		File dir = new File(folder);
		File mapfile = new File(dir, "mapdb");
		MapDB db = new MapDB(mapfile);
		map = db.getCollection("freebase");

	}

	public String getWikiId(String freebase) {
		String wikiname = map.get(freebase);
		if (wikiname == null)
			return null;

		return wikiname;
	}

	public boolean hasEntity(String freebase) {
		return map.containsKey(freebase);
	}

	public static void main(String[] args) {
		FreebaseToWikipedia f2w = new FreebaseToWikipedia("mapdb");
		System.out.println(f2w.getWikiId("/m/0f36_3"));
	}

}
