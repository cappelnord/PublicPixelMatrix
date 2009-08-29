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

import java.util.Date;

/**
 * {@link PPMatrix} contains the matrix and the date it was created/modified.
 * {@link PPMatrix} objects can be read from files or URLs with static methods of {@link PublicPixelMatrix}.
 * 
 * @author Patrick Borgeat
 *
 */

public class PPMatrix {
	
	private boolean[][] matrix;
	private Date date;
	private long timestamp;
	
	/**
	 * Creates an empty {@link PPMatrix}, set on the current time.
	 */
	
	public PPMatrix()
	{
		date = new Date();
		timestamp = date.getTime() / 1000;
		matrix = new boolean[PublicPixelMatrix.DIM][PublicPixelMatrix.DIM];
	}
	
	/**
	 * Creates a {@link PPMatrix} from known data. This constructor is used by
	 * the factory methods found in {@link PublicPixelMatrix}.
	 * 
	 * @param aTimestamp A unix timestamp, seconds passed since 01/01/1970
	 * @param aMatrix A two-dimensional matrix of boolean values
	 */
	
	public PPMatrix(long aTimestamp, boolean[][] aMatrix)
	{
		date = new Date(aTimestamp * 1000);
		timestamp = aTimestamp;
		matrix = aMatrix;
	}
	
	/**
	 * Returns the matrix of boolean values.
	 * 
	 * @return A two-dimensional matrix of boolean values
	 */
	
	public boolean[][] getMatrix()
	{
		return matrix;
	}
	
	/**
	 * Returns the value of the given {@link Point} object.
	 * 
	 * @param p A {@link Point} object
	 * @return boolean
	 */

	public boolean get(Point p)
	{
		return get(p.getX(), p.getY());
	}
	
	/**
	 * Returns the value of the given x and y coordinates.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return boolean
	 */
	
	public boolean get(int x, int y)
	{
		return matrix[y][x]; // eventuell umdrehen
	}
	
	/**
	 * Sets the value of the given {@link Point} object.
	 * 
	 * @param p A {@link Point} object
	 * @param b New boolean value
	 */
	
	public void set(Point p, boolean b)
	{
		set(p.getX(), p.getY(), b);
	}
	
	/**
	 * Sets the value of the given x and y coordinates.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param b New boolean value
	 */
	
	public void set(int x, int y, boolean b)
	{
		matrix[y][x] = b;
	}
	
	/**
	 * Returns the Unix timestamp (seconds passed since 01/01/1970) of the
	 * creation/modification date of this {@link PPMatrix}.
	 * 
	 * @return long
	 */
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	/**	 
	 * Returns a {@link Date} object of the creation/modification
	 * of this {@link PPMatrix}.
	 * 
	 * @return {@link Date}
	 */
	
	public Date getDate()
	{
		return date;
	}
	
	/**
	 * Formats the creation/modification date. The format pattern can be set in {@link PublicPixelMatrix}.
	 * 
	 * @return Formatted date as {@link String}
	 */
	
	public String getFormattedDate()
	{
		return PublicPixelMatrix.formatDate(date);
	}
	
	/**
	 * Compares two instances of {@link PPMatrix} for equality.
	 * 
	 * @param m Another instance of {@link PPMatrix}
	 * @return boolean
	 */
	
	public boolean equals(PPMatrix m)
	{
		return firstDifference(m) == null;
	}
	
	/**
	 * Compares a specific point of another {@link PPMatrix} specified by a {@link Point} object.
	 * 
	 * @param p A {@link Point} object
	 * @param m Another instance of {@link PPMatrix}
	 * @return boolean
	 */
	
	public boolean equalsAt(Point p, PPMatrix m)
	{
		return get(p) == m.get(p);
	}
	
	/**
	 * Compares a specific point of another {@link PPMatrix} specified by x/y coordinates.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param m Another instance of {@link PPMatrix}
	 * @return boolean
	 */
	
	public boolean equalsAt(int x, int y, PPMatrix m)
	{
		return get(x,y) == m.get(x,y);
	}
	
	/**
	 * Counts the number of differences to another {@link PPMatrix}.
	 * 
	 * @param m Another instance of {@link PPMatrix}
	 * @return Number of differences as integer
	 */
	
	public int numDifferences(PPMatrix m)
	{
		int num = 0;
		for(int y = 0; y < PublicPixelMatrix.DIM; y++)
		{
			for(int x = 0; x < PublicPixelMatrix.DIM; x++)
			{
				if(get(x,y) == m.get(x,y))
					num++;
			}
		}
		return num;
	}
	
	/**
	 * Returns the first difference to another {@link PPMatrix}.
	 * 
	 * @param m Another instance of {@link PPMatrix}
	 * @return The first difference as {@link Point}
	 */
	
	public Point firstDifference(PPMatrix m)
	{
		Point ret = null;
		
		for(int y = 0; y < PublicPixelMatrix.DIM; y++)
		{
			for(int x = 0; x < PublicPixelMatrix.DIM; x++)
			{
				if(get(x,y) != m.get(x,y))
				{
					return new Point(x,y);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Returns a human-readable representation of the matrix. Usefull for debugging.
	 * 
	 * @return {@link String}
	 */
	
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		
		b.append(getFormattedDate());		
		for(int i = 0; i < PublicPixelMatrix.DIM; i++)
		{
			b.append("\n");
			for(int e = 0; e < PublicPixelMatrix.DIM; e++)
			{
				b.append(matrix[i][e] ? '1' : '0');
			}
		}
		
		return b.toString();
	}
}
