/**  StrVectorUtils.java
 * AUTHOR: 			Albert J. Boehmler - Oct 8, 2008
 *
 * COPYRIGHT: 	Albert J. Boehmler, 2008
 *
 * LICENSE: 		This file is distributed under the LGPL License.
            		License text is available at http://www.opensource.org/licenses/lgpl-3.0.html.
 *
 * DISCLAIMER:  This file is distributed in the hope that it will be useful,
								but WITHOUT ANY WARRANTY; without even the implied warranty
								of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

								IN NO EVENT SHALL THE COPYRIGHT OWNER OR
								CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
								INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */

package org.mm_anywhere.app;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import mmcorej.CMMCore;
import mmcorej.StrVector;

/**
 * @author ajb
 *
 */
public class MmCoreUtils {

	private static CMMCore _core;
	private static Runnable _doSnap;

	public static boolean getStrVectorContains(StrVector strVector, String toCheck) {
		for (String str : getStrVectorIterator(strVector)) {
			if (str.equals(toCheck)) {
				return true;
			}
		}
		return false;
	}

	public static Iterable<String> getStrVectorIterator(final StrVector strVector) {
		return new Iterable<String>() {
			int _index = 0;

			@Override
			public Iterator<String> iterator() {
				return new Iterator<String>() {
					@Override
					public boolean hasNext() {
						return _index < strVector.size();
					}

					@Override
					public String next() {
						String retVal = strVector.get(_index);
						_index++;
						return retVal;
					}

					@Override
					public void remove() { }
				};
			};
		};
	}
	
	public static List<String> getStrVectorList(final StrVector strVector) {
		List<String> l = new ArrayList<String>();
		for (String str : getStrVectorIterator(strVector)) {
			l.add(str);
		}
		return l;
	}
	
}
