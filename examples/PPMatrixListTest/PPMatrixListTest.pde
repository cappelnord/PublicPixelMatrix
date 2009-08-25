/*
    A simple display of the PublicPixelMatrix history file.
    
    (c) 2009 Patrick Borgeat <patrick@borgeat.de>
    
    This work is licensed under a Creative Commons Attribution-Share Alike 3.0 Unported License.
    http://creativecommons.org/licenses/by-sa/3.0/
*/

import de.cappelnord.ppm.*;

PPMatrixList list;
int dim;
int size = 24;


Iterator it;

void setup()
{
  dim = PublicPixelMatrix.DIM;
  size(dim * size, dim * size);
  frameRate(40);
  
  list = PublicPixelMatrix.readPPMatrixListFromURL("http://matrix.cappel-nord.de/data/history");  
  
  it = list.iterator();
}

void draw()
{
  if(it.hasNext())
  {
    PPMatrix matrix = (PPMatrix) it.next();
    background(0);
    
    for(int y = 0; y < dim; y++)
    {
      for(int x = 0; x < dim; x++)
      {
        fill(matrix.get(x,y) ? 0 : 255);
        rect(x * size, y * size, size, size);
      }
    }
  }
}

