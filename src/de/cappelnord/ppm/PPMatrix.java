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

public class PPMatrix {
	
	private boolean[][] matrix;
	private Date date;
	private long timestamp;
	
	public PPMatrix(long aTimestamp, boolean[][] aMatrix)
	{
		date = new Date(aTimestamp * 1000);
		timestamp = aTimestamp;
		matrix = aMatrix;
	}
	
	public boolean[][] getMatrix()
	{
		return matrix;
	}
	
	public boolean get(Point p)
	{
		return get(p.getX(), p.getY());
	}
	
	public boolean get(int x, int y)
	{
		return matrix[y][x]; // eventuell umdrehen
	}
	
	public void set(Point p, boolean b)
	{
		set(p.getX(), p.getY(), b);
	}
	
	public void set(int x, int y, boolean b)
	{
		matrix[y][x] = b;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public String getFormattedDate()
	{
		return PublicPixelMatrix.formatDate(date);
	}
	
	public boolean equals(PPMatrix m)
	{
		return firstDifference(m) == null;
	}
	
	public boolean equalsAt(Point p, PPMatrix m)
	{
		return get(p) == m.get(p);
	}
	
	public boolean equalsAt(int x, int y, PPMatrix m)
	{
		return get(x,y) == m.get(x,y);
	}
	
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
