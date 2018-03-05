import processing.core.PApplet;

public class Mandelbrot extends PApplet{
  public static void main(String[] args) {
    PApplet.main("Mandelbrot");
  }

  public void settings(){
    size(displayWidth, displayHeight);
  }

  public void setup(){
    noLoop();
    background(255);
    colorMode(HSB, 255, 100, 100);

    // Establish a range of values on the complex plane
    // A different range will allow us to "zoom" in or out on the fractal

    // It all starts with the width, try higher or lower values
    double w = 0.0000001;
    double h = (w * height) / width;

    // Start at negative half the width and height
    double xmin = (-w/2)-0.728;
    double ymin = (-h/2)-0.25;

    // Make sure we can write to the pixels[] array.
    // Only need to do this once since we don't do any other drawing.
    loadPixels();

    // Maximum number of iterations for each point on the complex plane
    int maxiterations = 150;

    // x goes from xmin to xmax
    double xmax = xmin + w;
    // y goes from ymin to ymax
    double ymax = ymin + h;

    // Calculate amount we increment x,y for each pixel
    double dx = (xmax - xmin) / (width);
    double dy = (ymax - ymin) / (height);

    // Start y
    double y = ymin;
    for (int j = 0; j < height; j++) {
      // Start x
      double x = xmin;
      for (int i = 0; i < width; i++) {

        // Now we test, as we iterate z = z^2 + cm does z tend towards infinity?
        double a = x;
        double b = y;
        int n = 0;
        while (n < maxiterations) {
          double aa = a * a;
          double bb = b * b;
          double twoab = (2.0 * a * b);
          a = aa - bb + x;
          b = twoab + y;
          // Infinty in our finite world is simple, let's just consider it 16
          if (dist((float)aa, (float)bb, 0, 0) > 12.0) {
            break;  // Bail
          }
          n++;
        }

        // We color each pixel based on how long it takes to get to infinity
        // If we never got there, let's pick the color black
        if (n == maxiterations) {
          pixels[i+j*width] = color(0);
        } else {
          // Gosh, we could make fancy colors here if we wanted
          double norm = map(n, 0, maxiterations, 0, 1);
          pixels[i+j*width] = color(map(sqrt((float)norm), 0, 1, 255, 55), 100, 100);
        }
        x += dx;
      }
      y += dy;
    }
    updatePixels();
  }
}
