package sortLab;

import javax.swing.*;
import java.awt.*;

public class RadixSortDriver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // create a JFrame to hold the RadixSortPanel
            JFrame frame = new JFrame("Radix Sort Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set default close operation
            frame.setSize(1400, 1000);  // set the size of the frame
            frame.setLocationRelativeTo(null);  // center the frame on the screen
            frame.setResizable(false);  // prevent resizing of the frame
            
            // initialize the panel that will handle the radix sort visualization
            RadixSortPanel panel = new RadixSortPanel();
            frame.add(panel, BorderLayout.CENTER);  // add the panel to the center of the frame

            // make the window visible
            frame.setVisible(true);
        });
    }
}
