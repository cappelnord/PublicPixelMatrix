/*
  PublicPixelMatrix library for Processing
  
  (c) Patrick Borgeat 2009
  
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
*/

package de.cappelnord.ppm;
import java.util.*;

/**
 * {@link PPMatrixList} contains and manages a sequence of {@link PPMatrix} objects.
 * At this moment {@link PPMatrixList} is just a wrapper for a {@link java.util.List}.
 * In future releases there will be added more functionallity (filtering, output, ...).
 * 
 * @author Patrick Borgeat
 *
 */

public class PPMatrixList {
	
	List<PPMatrix> list;
	
	public PPMatrixList()
	{
		list = new ArrayList<PPMatrix>();
	}
	
	/**
	 * Returns the instance of the {@link List}.
	 * 
	 * @return {@link List}
	 */
	
	public List<PPMatrix> getListInstance()
	{
		return list;
	}
	
	/**
	 * Returns an {@link java.util.Iterator} for the {@link List}.
	 * 
	 * @return {@link Iterator}
	 */
	
	public Iterator<PPMatrix> iterator()
	{
		return list.iterator();
	}
	
	/**
	 * Adds an {@link PPMatrix} to the {@link List}.
	 * 
	 * @param o {@link PPMatrix} to add
	 */
	
	public void add(PPMatrix o)
	{
		list.add(o);
	}
	
	/**
	 * Returns the {@link PPMatrix} at the given index.
	 * 
	 * @return {@link PPMatrix}
	 * 
	 */
	
	public PPMatrix get(int index)
	{
		return list.get(index);
	}
	
	/**
	 * Returns the size of the {@link List}.
	 * 
	 * @return The size of the {@link List} as integer
	 */
	
	public int size()
	{
		return list.size();
	}
}
