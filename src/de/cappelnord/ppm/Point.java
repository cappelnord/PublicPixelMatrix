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

/**
 * This class provides an easy and simple 2-dimensional vector to adress specific points
 * in a {@link PPMatrix}. It provides no further functionality. This class should be
 * self explanatory.
 * 
 * @author Patrick Borgeat
 *
 */

public class Point {
	
	final private int x;
	final private int y;
	
	public Point (int aX, int aY)
	{
		x = aX;
		y = aY;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean equals(Point p)
	{
		return x == p.getX() && y == p.getY();
	}
}
