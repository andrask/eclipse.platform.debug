/*******************************************************************************
 * Copyright (c) 2002, 2003 Object Factory Inc.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *		Object Factory Inc. - Initial implementation
 *******************************************************************************/
package org.eclipse.ui.externaltools.internal.ant.dtd.schema;

import org.eclipse.ui.externaltools.internal.ant.dtd.IAtom;
import org.eclipse.ui.externaltools.internal.ant.dtd.util.Factory;
import org.eclipse.ui.externaltools.internal.ant.dtd.util.FactoryObject;

/**
 * Non-deterministic finite state machine node.<p>
 * 
 * Following Aho & Ullman, nfm nodes contain two
 * transition links. The graph is constructed so
 * that no node requires more than two links.
 * There are exactly these kinds of nodes:
 * <pre>
 *		Symbol	Next1	Next2	Meaning
 *		  "a"	 fwd	 null	Regexp "a"
 *		  null	 null	 null	Accepting node
 *		  null	 fwd1	 fwd2	Start node of ? and * regexp
 *								fwd2 points to stop node
 *		  null	 fwd	 bkw	Internal node of + and * regexp
 *								fwd points to stop node
 *								bkw points, e.g., in "a*" to start node of "a"
 *		  null	 fwd1	 fwd2	Start node of | regexp, e.g., "a|b",
 *								fwd nodes point to start nodes of "a" and "b".
 *		  null	 fwd	 null	Internal node of |.
 *								fwd points to stop node.
 * </pre>
 * See Nfm for pictures of how nodes are used.
 * @author Bob Foster
 */
public class NfmNode implements FactoryObject {

	public IAtom symbol;
	public NfmNode next1;
	public NfmNode next2;
	public Dfm dfm;
	public int mark;
	
	private NfmNode() {
	}
	
	public static NfmNode nfmNode(IAtom symbol, NfmNode next) {
		NfmNode nfm = getFree();
		nfm.symbol = symbol;
		nfm.next1 = next;
		return nfm;
	}
	
	public static NfmNode nfmNode(NfmNode next) {
		NfmNode nfm = getFree();
		nfm.next1 = next;
		return nfm;
	}
	
	public static NfmNode nfmNode() {
		return getFree();
	}
	
	/**
	 * Free all NfmNodes in use.
	 */
	public static void freeAll() {
		while (fUsed != null) {
			FactoryObject nfm = fUsed;
			fUsed = nfm.next();
			setFree((NfmNode)nfm);
		}
	}
	
	// Below here is factory stuff
	
	/**
	 * @see com.objfac.util.FactoryObject#next()
	 */
	public FactoryObject next() {
		return next;
	}

	/**
	 * @see com.objfac.util.FactoryObject#next(FactoryObject)
	 */
	public void next(FactoryObject obj) {
		next = (NfmNode) obj;
	}
	private NfmNode next;
	private static Factory fFactory = new Factory();
	private static FactoryObject fUsed = null;
	private static NfmNode getFree() {
		NfmNode nfm = (NfmNode) fFactory.getFree();
		if (nfm == null)
			nfm = new NfmNode();
		nfm.next(fUsed);
		fUsed = nfm;
		return nfm;
	}
	private static void setFree(NfmNode nfm) {
		nfm.symbol = null;
		nfm.next1 = nfm.next2 = null;
		nfm.dfm = null;
		nfm.mark = 0;
		fFactory.setFree(nfm);
	}
}
