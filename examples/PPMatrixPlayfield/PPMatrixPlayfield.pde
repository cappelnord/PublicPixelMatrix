/*
    Displays the current PublicPixelMatrix.
    
    (c) 2009 Patrick Borgeat <patrick@borgeat.de>
    
    This work is licensed under a Creative Commons Attribution-Share Alike 3.0 Unported License.
    http://creativecommons.org/licenses/by-sa/3.0/
*/

import de.cappelnord.ppm.*;

PPMatrix matrix;
int dim;

void setup()
{
  dim = PublicPixelMatrix.DIM;
  size(20 * dim,20 * dim);
  frameRate(0.25);
}

void draw()
{
  matrix = PublicPixelMatrix.readCurrentPPMatrix();
  
  float hSize = height / dim;
  float wSize = width / dim;
  
  // draws the matrix
  for(int y = 0; y < dim; y++)
  {
    for(int x = 0; x < dim; x++)
    {
      // either black or white
      fill(matrix.get(x,y) ? 0 : 255);
      rect(x * wSize, y * hSize, wSize, hSize);
    }
  }
}

