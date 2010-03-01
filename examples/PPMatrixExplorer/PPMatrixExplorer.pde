/*
    A explorer for the complete history of the Public Pixel Matrix.
    Press space bar to play/pause.
    
    (c) 2010 Patrick Borgeat <patrick@borgeat.de>
    
    This work is licensed under a Creative Commons Attribution-Share Alike 3.0 Unported License.
    http://creativecommons.org/licenses/by-sa/3.0/
*/

import processing.opengl.*;
import de.cappelnord.ppm.*;

int squareSize = 35;
int statusHeight = 42;

PPMatrixList list;
int lsize;
PFont font;
int currentIndex = 0;
boolean isPlaying = false;

final static int DIM = PublicPixelMatrix.DIM;

void setup()
{
  size(DIM * squareSize, 
       DIM * squareSize + statusHeight,
       OPENGL);
  smooth();

  list = PublicPixelMatrix.readCurrentPPMatrixHistory();
  lsize = list.size();

  font = loadFont("ArialUnicodeMS-14.vlw");
  textFont(font, 16);

  frameRate(40);  
  noLoop();
}

void draw()
{
  PPMatrix current = list.get(currentIndex); 

  background(128);
  stroke(128);
  fill(255);

  for(int y = 0; y < DIM; y++)
  {
    for(int x = 0; x <  DIM; x++)
    {
      if(current.get(x,y)) fill(0);
      else fill(255);

      rect(x*squareSize, y*squareSize, (x+1) * squareSize, (y+1) * squareSize);
    }
  }

  // draw status
  noStroke();
  fill(128);
  rect(0, height-statusHeight, width, height);

  stroke(255,0,0);
  int linePos = (int) (((float) currentIndex / (float) lsize) * width);
  line(linePos, height - statusHeight, linePos, height);

  fill(255);
  text(current.getFormattedDate(), 4, height - statusHeight + 18);
  text(currentIndex, 4, height - statusHeight + 36);


  if(isPlaying) currentIndex++;

  if(currentIndex >= lsize)
  {
    noLoop();
    isPlaying = false;
    currentIndex--;
  }

}

void keyPressed()
{
  if(key == ' ') isPlaying = !isPlaying;

  if(isPlaying) loop();
  else noLoop();
}

void mousePressed()
{
  if(mouseY >= height - statusHeight)
  {
    currentIndex = (int) (((float) mouseX / (float) width) * lsize);
    if(currentIndex < 0) currentIndex = 0;
    if(currentIndex >= lsize) currentIndex = lsize - 1;

    redraw(); 
  }
}

void mouseDragged()
{
  mousePressed();
}




