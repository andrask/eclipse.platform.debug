/*******************************************************************************
 * Copyright (c) 2009 Wind River Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Wind River Systems - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.internal.ui.viewers.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

/**
 * {@link InternalTreeModelViewer} label provider interface.  In addition to 
 * implementing this interface, the label provider for the TreeModelViewer
 * must also extend CellLabelProvider.    
 * 
 * @since 3.5
 */
public interface ITreeModelLabelProvider extends IBaseLabelProvider {

    /**
     * Requests an label update label of the given element.
     * @param elementPath Element to update.
     * @return true if element label provider is found and update will 
     * be requested.
     */
    public boolean update(TreePath elementPath);
    
    /**
     * Registers the specified listener for view label update notifications.
     * @param listener Listener to add
     */
    public void addLabelUpdateListener(ILabelUpdateListener listener);
    
    /**
     * Removes the specified listener from view label update notifications.
     * @param listener Listener to remove
     */
    public void removeLabelUpdateListener(ILabelUpdateListener listener);

    /**
     * Returns an image for the given image descriptor or <code>null</code>. Adds the image
     * to a cache of images if it does not already exist.
     * 
     * @param descriptor image descriptor or <code>null</code>
     * @return image or <code>null</code>
     */
    public Image getImage(ImageDescriptor descriptor);
    
    /**
     * Returns a font for the given font data or <code>null</code>. Adds the font to the font 
     * cache if not yet created.
     * 
     * @param fontData font data or <code>null</code>
     * @return font font or <code>null</code>
     */
    public Font getFont(FontData fontData);

    /**
     * Returns a color for the given RGB or <code>null</code>. Adds the color to the color 
     * cache if not yet created.
     * 
     * @param rgb RGB or <code>null</code>
     * @return color or <code>null</code>
     */
    public Color getColor(RGB rgb);
}
