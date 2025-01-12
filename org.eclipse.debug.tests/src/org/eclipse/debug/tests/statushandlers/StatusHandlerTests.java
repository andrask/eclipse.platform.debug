/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.tests.statushandlers;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.internal.core.IInternalDebugCoreConstants;
import org.eclipse.debug.internal.core.Preferences;
import org.eclipse.debug.tests.TestsPlugin;

/**
 * Tests status handlers
 */
public class StatusHandlerTests extends TestCase {
	
	/**
	 * Status for which a handler is registered.
	 */
	public static final IStatus STATUS = new Status(IStatus.ERROR, TestsPlugin.PLUGIN_ID, 333,"", null);
	
	/**
	 * Tests that a status handler extension exists
	 */
	public void testStatusHandlerExtension() {
		IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(STATUS);
		assertNotNull("missing status handler extension", handler);
		assertTrue("Unexpected handler", handler instanceof StatusHandler);
	}
	
	/**
	 * Tests that status handlers are not returned when preference is disabled
	 */
	public void testDisableStatusHandlers() {
		try {
			Preferences.setBoolean(DebugPlugin.getUniqueIdentifier(), IInternalDebugCoreConstants.PREF_ENABLE_STATUS_HANDLERS, false, new InstanceScope());
			IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(STATUS);
			assertNull("status handler extension should be disabled", handler);
		} finally {
			Preferences.setBoolean(DebugPlugin.getUniqueIdentifier(), IInternalDebugCoreConstants.PREF_ENABLE_STATUS_HANDLERS, true, new InstanceScope());
		}
	}

}
