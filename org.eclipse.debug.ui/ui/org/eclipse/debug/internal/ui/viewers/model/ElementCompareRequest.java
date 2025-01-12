/*******************************************************************************
 * Copyright (c) 2006, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Wind River Systems - Fix for viewer state save/restore [188704] 
 *******************************************************************************/
package org.eclipse.debug.internal.ui.viewers.model;

import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementCompareRequest;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ITreeModelViewer;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ModelDelta;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.ui.IMemento;

/**
 * @since 3.3
 */
public class ElementCompareRequest extends MementoUpdate implements IElementCompareRequest {

	private boolean fEqual;
    private final int fModelIndex;
	private ModelDelta fDelta;
    private boolean fKnowsHasChildren;
    private boolean fKnowsChildCount;
    private boolean fCheckChildrenRealized;
	
	
    /**
     * @param provider the content provider to use for the update
     * @param viewerInput the current input
     * @param element the element to update
     * @param elementPath the path of the element to update
     * @param memento Memento to encode result into
     * @param delta Delta to write the result comparison into.
     * @param modelIndex Index of element to compare.
     * @param knowsHasChildren Flag indicating whether provider knows the has 
     * children state of element. 
     * @param knowsChildCount Flag indicating whether provider knows the 
     * child count state of element.
     * @param checkChildrenRealized Flag indicating if any realized children should be checked
     */
	public ElementCompareRequest(TreeModelContentProvider provider, Object viewerInput, Object element, 
	    TreePath elementPath, IMemento memento, ModelDelta delta, int modelIndex, 
	    boolean knowsHasChildren, boolean knowsChildCount, boolean checkChildrenRealized) 
	{
		super(provider, viewerInput, provider.getPresentationContext(), element, elementPath, memento);
		fProvider = provider;
		fDelta = delta;
		fModelIndex = modelIndex;
		fKnowsHasChildren = knowsHasChildren;
		fKnowsChildCount = knowsChildCount;
		fCheckChildrenRealized = checkChildrenRealized;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.ui.viewers.model.provisional.IElementCompareRequest#setEqual(boolean)
	 */
	public void setEqual(boolean equal) {
		fEqual = equal;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IProgressMonitor#done()
	 */
	public void done() {
        ITreeModelViewer viewer = getContentProvider().getViewer();
        if (viewer == null) return;  // disposed
        if (viewer.getDisplay().getThread() == Thread.currentThread()) {
            fProvider.getStateTracker().compareFinished(ElementCompareRequest.this, fDelta);
        } else {
            viewer.getDisplay().asyncExec(new Runnable() {
                public void run() {
                    if (getContentProvider().isDisposed()) return;
                    fProvider.getStateTracker().compareFinished(ElementCompareRequest.this, fDelta);
                }
            });
        }
	}	    
	
	public boolean isEqual() {
		return fEqual;
	}
	
	ModelDelta getDelta() {
		return fDelta;
	}
	
	int getModelIndex() {
		return fModelIndex;
	}

	
	void setKnowsHasChildren(boolean hasChildren) {
		fKnowsHasChildren = hasChildren;
	}
	
	boolean knowsHasChildren() {
		return fKnowsHasChildren;
	}

	void setKnowsChildCount(boolean childCount) {
		fKnowsChildCount = childCount;
	}
	
	boolean knowChildCount() {
		return fKnowsChildCount;
	}

    void setCheckChildrenRealized(boolean checkChildrenRealized) {
        fCheckChildrenRealized = checkChildrenRealized; 
    }
    
    boolean checkChildrenRealized() {
        return fCheckChildrenRealized;
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("IElementCompareRequest: "); //$NON-NLS-1$
        buf.append(getElement());
        return buf.toString();
    }

}
