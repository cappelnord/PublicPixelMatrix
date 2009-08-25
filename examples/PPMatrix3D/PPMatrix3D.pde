/*
    A visualization example in 3D using the current history file on startup.
    
    (c) 2009 Patrick Borgeat <patrick@borgeat.de>
    
    This work is licensed under a Creative Commons Attribution-Share Alike 3.0 Unported License.
    http://creativecommons.org/licenses/by-sa/3.0/
*/

import processing.opengl.*;
import de.cappelnord.ppm.*;

PPMatrixList list;
int dim;
int size = 24;

Iterator it;

float drehI = 0.0;

PPMatrix[] history;


final static int NUM = 6;

void setup()
{
  dim = PublicPixelMatrix.DIM;
  size(1024, 576, OPENGL);
  frameRate(25);
  smooth();
  
  list = PublicPixelMatrix.readPPMatrixListFromURL("http://matrix.cappel-nord.de/data/history");  
  
  it = list.iterator();
  
  history = new PPMatrix[NUM];


}

void draw()
{
  if(it.hasNext())
  {
    background(0);
    noStroke();
    
    
    PPMatrix matrix = (PPMatrix) it.next();
    
    for(int i = history.length - 2; i >= 0; i--)
    {
       history[i+1] = history[i]; 
    }
    history[0] = matrix;

    pushMatrix();
    translate(width/2, height / 2 - ((dim * size) / 2));
    
    drehI  = drehI + 0.011;
    rotateY( PI + (sin(drehI) * (PI/4)));
    translate(-((dim * size) / 2),0);
    
    for(int i = 0; i < history.length; i++)
    {
    if(history[i] != null)
    {
      pushMatrix();
      translate(0,0,i*-80 );
      
      float alpha = 255 - (((float) i / (float) history.length) * 220);
      
      for(int y = 0; y < dim; y++)
      {
        for(int x = 0; x < dim; x++)
        {   
          fill(0,history[i].get(x,y) ? 0 : 255,0, alpha);
          rect(x * size, y * size, size , size);
        }
      }
      
      popMatrix();
    }
    }
    
    popMatrix();
    
    fill(255);
    // text(matrix.getFormattedDate(),20,34);
    // saveFrame("frame####.png");
  }
}
