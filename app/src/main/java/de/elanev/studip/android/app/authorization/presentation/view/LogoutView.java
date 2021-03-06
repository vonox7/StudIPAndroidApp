/*
 * Copyright (c) 2017 ELAN e.V.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package de.elanev.studip.android.app.authorization.presentation.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * @author joern
 */

public interface LogoutView extends MvpView {
  void showError(Throwable error);

  void logoutSuccess();
}
