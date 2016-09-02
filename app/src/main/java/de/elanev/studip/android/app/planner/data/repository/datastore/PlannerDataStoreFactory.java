/*
 * Copyright (c) 2016 ELAN e.V.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package de.elanev.studip.android.app.planner.data.repository.datastore;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.elanev.studip.android.app.data.net.services.StudIpLegacyApiService;

/**
 * @author joern
 */
@Singleton
public class PlannerDataStoreFactory {
  private final StudIpLegacyApiService apiService;

  @Inject PlannerDataStoreFactory(StudIpLegacyApiService apiService) {
    this.apiService = apiService;
  }

  public PlannerDataStore create() {
    return new PlannerCloudDataStore(apiService);
  }
}
