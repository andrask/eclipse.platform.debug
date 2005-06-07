/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.internal.ui.actions;


import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class TerminateActionDelegate extends AbstractListenerActionDelegate {

	/**
	 * @see AbstractDebugActionDelegate#doAction(Object)
	 */
	protected void doAction(Object element) throws DebugException {
		if (element instanceof ITerminate) {
			((ITerminate)element).terminate();
		}
	}
	
	/**
	 * @see AbstractDebugActionDelegate#isRunInBackground()
	 */
	protected boolean isRunInBackground() {
		return true;
	}

	/**
	 * @see AbstractDebugActionDelegate#isEnabledFor(Object)
	 */
	protected boolean isEnabledFor(Object element) {
		return element instanceof ITerminate && ((ITerminate)element).canTerminate();
	}
	
	/**
	 * @see AbstractDebugActionDelegate#getStatusMessage()
	 */
	protected String getStatusMessage() {
		return ActionMessages.TerminateActionDelegate_Exceptions_occurred_attempting_to_terminate__2; //$NON-NLS-1$
	}

	/**
	 * @see AbstractDebugActionDelegate#getErrorDialogMessage()
	 */
	protected String getErrorDialogMessage() {
		return ActionMessages.TerminateActionDelegate_Terminate_failed__1; //$NON-NLS-1$
	}
	
	/**
	 * @see ListenerActionDelegate#doHandleDebugEvent(DebugEvent)
	 */
	protected void doHandleDebugEvent(DebugEvent event) {	
		if (event.getKind() == DebugEvent.TERMINATE || event.getKind() == DebugEvent.CREATE) {
			update(getAction(), getSelection());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.ui.actions.AbstractDebugActionDelegate#update(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	protected void update(IAction action, ISelection s) {
		// ignore non-structured selections (for example, text selection in console)
		if (s instanceof IStructuredSelection) {
			super.update(action, s);
		}
	}
	
	
}
