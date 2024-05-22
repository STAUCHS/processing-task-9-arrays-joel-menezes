import processing.core.PApplet;

/**
 * @description Program of Snow Flake Game
 * @author Joel Menezes
 */
public class Sketch extends PApplet {
  // X and Y for snowflakes
  float[] fltSnowX = new float[42];
  float[] fltSnowY = new float[42];
  float[] fltSnowDiameter = new float[42];
  int intSpeed = 2;
  int intPlayerX;
  int intPlayerY;
  int fltDiameter = 20;
  int intLives = 3;
  boolean blnHasStopped = false;

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(0);
    // Generate Random x- and y-values for snowflake
    for (int intSnowFlake = 0; intSnowFlake < fltSnowX.length; intSnowFlake++) {
      fltSnowX[intSnowFlake] = random(width);
      fltSnowY[intSnowFlake] = random(height);
      fltSnowDiameter[intSnowFlake] = fltDiameter;
      circle(fltSnowX[intSnowFlake], fltSnowY[intSnowFlake], fltSnowDiameter[intSnowFlake]);
    }
    intPlayerX = width / 2;
    intPlayerY = 350;
  }

  public void draw() {
    if (blnHasStopped) {
      // do nothing
    } else {
      // Run Game
      background(0);
      // Check Key Pressed, Faster Here than method, Makes movement for player
      if (keyPressed) {
        if (key == 'w') {
          intPlayerY -= 3;
          if (intPlayerY < 0) {
            intPlayerY = height;
          }
        } else if (key == 's') {
          intPlayerY += 3;
          if (intPlayerY > height) {
            intPlayerY = 0;
          }
        } else if (key == 'a') {
          intPlayerX -= 3;
          if (intPlayerX < 0) {
            intPlayerX = width;
          }
        } else if (key == 'd') {
          intPlayerX += 3;
          if (intPlayerX > width) {
            intPlayerX = 0;
          }
        }
      }
      // Draw Player
      fill(0, 0, 255);
      circle(intPlayerX, intPlayerY, 25);
      // Draw Snow
      snow();
      // Check Collision
      for (int intSnowFlake = 0; intSnowFlake < fltSnowX.length - 1; intSnowFlake++) {
        // Player Collision
        if (abs(dist(intPlayerX, intPlayerY, fltSnowX[intSnowFlake],
            fltSnowY[intSnowFlake])) < 15 && fltSnowDiameter[intSnowFlake] != 0) {
          fltSnowDiameter[intSnowFlake] = 0;
          intLives--;
          if (intLives <= 0) {
            kill();
          }
        }
      }
    }
    // Check Lives
    drawLives();
  }

  /**
   * @description Kills Player, and shows Game Over
   * @author Joel Menezes
   */
  public void kill() {
    blnHasStopped = true;
    background(255);
    fill(0);
    textSize(50);
    text("Game Over!", 75, height / 2);
  }

  /**
   * @description Draws Snow Flakes
   * @author Joel Menezes
   */
  public void snow() {
    fill(255);
    for (int intSnowFlake = 0; intSnowFlake < fltSnowX.length; intSnowFlake++) {
      circle(fltSnowX[intSnowFlake], fltSnowY[intSnowFlake], fltSnowDiameter[intSnowFlake] / 2);

      fltSnowY[intSnowFlake] += intSpeed;
      if (fltSnowY[intSnowFlake] >= height) {
        fltSnowY[intSnowFlake] = 0;
        fltSnowDiameter[intSnowFlake] = fltDiameter;
      }
    }
  }

  /**
   * @description Checks special Key Presses
   * @author Joel Menezes
   */
  public void keyPressed() {
    // Snow Movement
    if (keyCode == 40) {
      intSpeed = 4;
    } else if (keyCode == 38) {
      intSpeed = 1;
    }
  }

  /**
   * @description Mouse Detection
   * @author Joel Menezes
   */
  public void mouseClicked() {
    // Mouse Collision
    for (int intSnowFlake = 0; intSnowFlake < fltSnowX.length; intSnowFlake++) {
      if (abs(dist(mouseX, mouseY, fltSnowX[intSnowFlake], fltSnowY[intSnowFlake])) < fltSnowDiameter[intSnowFlake]
          / 2) {
        fltSnowDiameter[intSnowFlake] = 0;
      }
    }
  }

  /**
   * @description Draws amount of lives to screen
   * @author Joel Menezes
   */
  public void drawLives() {
    for (int intDrawnLives = 0; intDrawnLives < intLives; intDrawnLives++) {
      fill(255, 0, 0);
      square((float) 25 * intDrawnLives + 20, (float) 20, (float) 20);
    }
  }

  /**
   * @description Resets Speed settings
   * @author Joel Menezes
   */
  public void keyReleased() {
    intSpeed = 2;
  }
}