package sortLab;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Pointer {
    private int x, y;
    private Image image;

    // constructor to initialize the pointer with an image
    public Pointer(String imagePath) {
        try {
            image = new ImageIcon(imagePath).getImage();  // load the image from the given path
        } catch (Exception e) {
            e.printStackTrace();  // print any exceptions that occur while loading the image
        }
        this.x = -1;  // set initial position off-screen
        this.y = -1;
    }

    // method to animate the pointer to a new position
    public void animateTo(int newX, int newY, int delay, Runnable repaintCallback) {
        int dx = newX - this.x;  // calculate horizontal distance
        int dy = newY - this.y;  // calculate vertical distance

        int distance = Math.max(Math.abs(dx), Math.abs(dy));  // get the maximum of the two distances
        int steps = Math.max(50, distance / 5);  // calculate number of steps based on distance
        
        int stepX = dx / steps;  // calculate step size for horizontal movement
        int stepY = dy / steps;  // calculate step size for vertical movement

        // move the pointer smoothly to the new position
        for (int i = 0; i < steps; i++) {
            this.x += stepX;
            this.y += stepY;

            repaintCallback.run();  // trigger repaint to update the pointer position
            sleep(delay / 10);  // pause briefly to control animation speed
        }

        // ensure the pointer ends exactly at the target position
        this.x = newX;
        this.y = newY;
        repaintCallback.run();  // final repaint to update the position
    }

    // method to draw the pointer image at the current position
    public void draw(Graphics g) {
        if (x != -1 && y != -1) {  // only draw if the pointer is on screen
            g.drawImage(image, x - image.getWidth(null) / 2 + 10, y - image.getHeight(null) / 2, null);
        }
    }

    // utility method to pause execution for a specified amount of time
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);  // pause for the given milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // handle interruption
        }
    }
}
