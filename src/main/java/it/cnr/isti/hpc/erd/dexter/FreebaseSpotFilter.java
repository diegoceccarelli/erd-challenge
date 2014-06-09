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
package it.cnr.isti.hpc.erd.dexter;

import it.cnr.isti.hpc.dexter.entity.EntityMatch;
import it.cnr.isti.hpc.dexter.entity.EntityMatchList;
import it.cnr.isti.hpc.dexter.label.IdHelper;
import it.cnr.isti.hpc.dexter.label.IdHelperFactory;
import it.cnr.isti.hpc.dexter.spot.SpotMatch;
import it.cnr.isti.hpc.dexter.spot.SpotMatchList;
import it.cnr.isti.hpc.dexter.spotter.filter.SpotMatchFilter;
import it.cnr.isti.hpc.dexter.util.DexterLocalParams;
import it.cnr.isti.hpc.dexter.util.DexterParams;
import it.cnr.isti.hpc.erd.WikipediaToFreebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Jun 8, 2014
 */
public class FreebaseSpotFilter implements SpotMatchFilter {

	private final WikipediaToFreebase map = new WikipediaToFreebase(
			"/tmp/mapdb");

	private static final Logger logger = LoggerFactory
			.getLogger(FreebaseSpotFilter.class);

	private final IdHelper helper = IdHelperFactory.getStdIdHelper();

	public FreebaseSpotFilter() {

	}

	public SpotMatchList filter(DexterLocalParams params, SpotMatchList eml) {
		SpotMatchList m = new SpotMatchList();
		for (SpotMatch sm : eml) {
			EntityMatchList entities = sm.getEntities();
			EntityMatchList efiltered = new EntityMatchList();
			for (EntityMatch e : entities) {
				String wikilabel = helper.getLabel(e.getId());
				if (map.hasEntity(wikilabel)) {
					efiltered.add(e);
				}
			}
			if (efiltered.isEmpty()) {
				logger.debug("no candidates for spot [{}]", sm.getMention());
				continue;
			}
			sm.setEntities(efiltered);
			m.add(sm);

		}
		return m;
	}

	public void init(DexterParams dexterParams, DexterLocalParams initParams) {
		// TODO Auto-generated method stub

	}

}
