package sortLab;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class RadixSortPanel extends JPanel {
    private JSlider speedSlider;
    private JComboBox<String> sortTypeSelector;
    private JButton loadFileButton, startButton;
    private ArrayList<Integer> numbers = new ArrayList<>();

    private int[] counts = new int[10];
    private int[] outputArray;
    private ArrayList<Integer> toDraw; // temporary array for animation
    private Pointer pointer;
    private int animationSpeed = 500;
    private boolean isLSD = true;
    private ArrayList<Integer> highlightedIndexes = new ArrayList<>();
    public boolean movingPtr = false;
    private ArrayList<ArrayList<Integer>> msdBuckets;
    int msdIndex = 0;

    // find the highest place value in the list of numbers
    public static int findHighestPlaceValue(ArrayList<Integer> numbers) {
        int maxNumber = Integer.MIN_VALUE;

        // find the largest number in the list
        for (int number : numbers) {
            if (number > maxNumber) {
                maxNumber = number;
            }
        }

        // find the highest place value
        int placeValue = 1;
        while (maxNumber >= 10) {
            placeValue *= 10;
            maxNumber /= 10;
        }

        return placeValue;
    }
    
    // load numbers from a file into the numbers list
    public void loadNumbersFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                numbers.clear();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        try {
                            numbers.add(Integer.parseInt(line));
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Invalid number format: " + line);
                            return;
                        }
                    }
                }
                // initialize outputArray when numbers are loaded
                outputArray = new int[numbers.size()];
                repaint();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file.");
            }
        }
    }

    
    // constructor for the RadixSortPanel
    public RadixSortPanel() {
        setLayout(new BorderLayout());

        // controls panel for the UI components
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        loadFileButton = new JButton("Load Numbers");
        loadFileButton.addActionListener(e -> loadNumbersFromFile());
        controls.add(loadFileButton);

        sortTypeSelector = new JComboBox<>(new String[]{"LSD Radix Sort", "MSD Radix Sort"});
        controls.add(sortTypeSelector);

        startButton = new JButton("Start Sorting");
        startButton.addActionListener(e -> startSorting());
        controls.add(startButton);

        JLabel speedLabel = new JLabel("Animation Speed:");
        controls.add(speedLabel);

        speedSlider = new JSlider(50, 1000, animationSpeed);
        speedSlider.addChangeListener(e -> animationSpeed = 1000 - speedSlider.getValue());
        controls.add(speedSlider);

        add(controls, BorderLayout.NORTH);

        // initialize pointer with image
        pointer = new Pointer("zhang.png");
    }

    // start sorting based on selected type
    private void startSorting() {
        if (numbers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No numbers loaded!");
            return;
        }

        String sortType = (String) sortTypeSelector.getSelectedItem();
        if (sortType != null) {
            new Thread(() -> {
                if (sortType.equals("LSD Radix Sort")) {
                    isLSD = true;
                    lsdRadixSort();
                } else {
                    isLSD = false;
                    msdIndex = 0;
                    int place = findHighestPlaceValue(numbers);
                    msdBuckets = new ArrayList<>(10);
                    for (int i = 0; i < 10; i++) {
                        msdBuckets.add(new ArrayList<>());
                    }
                    msdSort(new ArrayList<>(numbers), place);
                }

                JOptionPane.showMessageDialog(this, "Sorting Complete!");
            }).start();
        }
    }

    // LSD Radix Sort
    private void lsdRadixSort() {
        int max = Collections.max(numbers);
        int exp = 1;
        outputArray = new int[numbers.size()];
        counts = new int[10];

        while (max / exp > 0) {
            repaint();
            sleep(animationSpeed);

            Arrays.fill(counts, 0); // Reset count array

            // Step 1: Populate the count array
            for (int i = 0; i < numbers.size(); i++) {
                movePointerTo(i, getHeight() / 3 + 50); // Point to current element
                sleep(animationSpeed);

                int digit = (numbers.get(i) / exp) % 10;
                movePointerTo(digit, getHeight() / 2 + 50); // Move to the corresponding count index
                sleep(animationSpeed);

                counts[digit]++;
                repaint();
                sleep(animationSpeed);
            }

            movePointerTo(numbers.size() - 1, getHeight() / 3 + 50);
            sleep(animationSpeed);

            // Step 2: Create cumulative count array
            for (int i = 1; i < counts.length; i++) {
                highlightCounts(i - 1, i);
                sleep(animationSpeed);
                counts[i] += counts[i - 1];
                repaint();
                sleep(animationSpeed);
            }

            // Step 3: Populate the output array
            for (int i = numbers.size() - 1; i >= 0; i--) {
                movePointerTo(i, getHeight() / 3 + 50);
                sleep(animationSpeed);

                int digit = (numbers.get(i) / exp) % 10;
                movePointerTo(digit, getHeight() / 2 + 50);
                sleep(animationSpeed);

                int pos = --counts[digit];
                movePointerTo(pos, getHeight() * 2 / 3 + 50);
                sleep(animationSpeed);

                outputArray[pos] = numbers.get(i);
                repaint();
                sleep(animationSpeed);
            }

            for (int i = 0; i < outputArray.length; i++) {
            	numbers.set(i, outputArray[i]);
            }
            exp *= 10;
        }

        repaint();
    }

 // MSD (Most Significant Digit) Radix Sort
    private void msdSort(ArrayList<Integer> numbers, int place) {
        if (numbers.size() <= 1 || place < 1) return;

        msdBuckets = new ArrayList<>(10); // initialize buckets
        for (int i = 0; i < 10; i++) msdBuckets.add(new ArrayList<>());
        highlightSection(msdIndex, msdIndex + numbers.size());
        // step 1: distribute numbers into buckets based on the current digit
        for (int i = 0; i < numbers.size(); i++) {
            movePointerTo(i, getHeight() / 3 + 50); // point to current element
            sleep(animationSpeed);

            int digit = (numbers.get(i) / place) % 10;
            movePointerTo(digit, getHeight() / 2 + 50); // move to the corresponding bucket index
            sleep(animationSpeed);

            counts[digit]++;
            msdBuckets.get(digit).add(numbers.get(i));
            repaint();
            sleep(animationSpeed);
        }

        // step 2: recursively sort each non-empty bucket
        for (ArrayList<Integer> bucket : msdBuckets) {
            if (bucket.size() > 0) {
                // update toDraw array to visualize this bucket
                toDraw = bucket;
                repaint();  // redraw the current bucket

                Arrays.fill(counts, 0);  // reset counts for next recursion
                msdSort(bucket, place / 10);  // recurse on the next digit
                
                // after recursion, merge the sorted bucket back into the original list
                for (int num : bucket) {
                    if (bucket.size() == 1)
                        this.numbers.set(msdIndex++, num);  // place sorted numbers back
                }
                
            }
        }

        repaint();  // final repaint after merging
    }




    // move pointer to a specific index
    private void movePointerTo(int index, int yPosition) {
        if (pointer != null) {
            int xPosition = index * getWidth() / Math.max(numbers.size(), 1);
            pointer.animateTo(xPosition, yPosition, 10, this::repaint);
        }
    }

    // sleep for animation speed
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    
 // highlight two count indices in the count array
    private void highlightCounts(int firstIndex, int secondIndex) {
        // avoid potential issues if index is out of bounds
        if (firstIndex < 0 || firstIndex >= counts.length || secondIndex < 0 || secondIndex >= counts.length)
            return;

        // add the indexes to the highlightedIndexes list (only 2 for now)
        highlightedIndexes.clear(); // clear any previous highlights
        highlightedIndexes.add(firstIndex);
        highlightedIndexes.add(secondIndex);
        
        // request a repaint so the new highlights are drawn
        repaint();
        sleep(animationSpeed); // pause to show the effect

        // clear the highlighted indexes after the drawing step
        highlightedIndexes.clear();
        repaint(); // repaint after clearing highlights to remove them
        sleep(animationSpeed); // pause before continuing
    }
   
    
    private void visualizeBucketsForMSD(Graphics g, ArrayList<ArrayList<Integer>> buckets) {
        int width = getWidth() / Math.max(numbers.size(), 1);  // divide width based on number of buckets
        int height = 50;  // height of each bucket section
        
        // draw the original array
        for (int i = 0; i < numbers.size(); i++) {
        	Color color = (i < msdIndex) ? Color.GREEN : Color.CYAN; // Use green for sorted elements
            g.setColor(color);
            g.fillRect(i * width, getHeight() / 3 - height / 2, width - 5, height);

            g.setColor(Color.BLACK);
            // draw value
            g.drawString(String.valueOf(numbers.get(i)), i * width + width / 4, getHeight() / 3);

            // draw index
            g.drawString(String.valueOf(i), i * width + width / 4, getHeight() / 3 + 20);

            // draw a line above the index
            int xStart = i * width;
            int yLine = getHeight() / 3 + 10; // adjust the height of the line
            g.drawLine(xStart, yLine, xStart + width - 5, yLine);
        }
        
        // draw the counts array
        int yPosition = getHeight() / 2;
        for (int i = 0; i < counts.length; i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(i * width, yPosition - height / 2, width - 5, height);
            
            g.setColor(Color.BLACK);
            // draw count value
            g.drawString(String.valueOf(counts[i]), i * width + width / 4, yPosition);
            
            // draw index
            g.drawString(String.valueOf(i), i * width + width / 4, yPosition + 20);
            
            // draw a line above the index
            int xStart = i * width;
            int yLine = yPosition + 10;
            g.drawLine(xStart, yLine, xStart + width - 5, yLine);
        }
        
        // draw numbers under their respective counts
        int originalYPosition = getHeight() * 2 / 3;
        for (int i = 0; i < counts.length; i++) {
            int xStart = i * width;
            if (!buckets.get(i).isEmpty()) {
                for (int j = 0; j < buckets.get(i).size(); j++) {
                    g.setColor(Color.ORANGE);
                    g.fillRect(xStart, originalYPosition - height / 2, width - 5, height);
                    
                    g.setColor(Color.BLACK);
                    // draw the number value under its respective count
                    g.drawString(String.valueOf(buckets.get(i).get(j)), xStart + width / 4, originalYPosition);
                    originalYPosition += height;  // adjust Y position for the next number
                }
                originalYPosition = getHeight() * 2 / 3;  // reset Y position for the next bucket
            }
        }
        
        repaint();
        sleep(animationSpeed);  
    }


    
    @Override
    // graphical stuff
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth() / Math.max(numbers.size(), 1);
        int height = 50;

        if (isLSD) {
            // Draw numbers and count array for LSD
            drawNumbers(g);
            drawHighlightedCounts(g);
        } else {
            // Draw MSD Buckets
            visualizeBucketsForMSD(g, msdBuckets);
            drawHighlightedSection(g);
        }

        // Draw pointer
        pointer.draw(g);
    }


    // draws highlighted count boxes in red to emphasize specific indexes
    private void drawHighlightedCounts(Graphics g) {
        // check if there are highlighted indexes to draw
        if (!highlightedIndexes.isEmpty()) {
            int width = getWidth() / 20;  // width of each count box
            int yPosition = getHeight() / 2;  // Y position for the count boxes
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);  // set color to red for highlighting
            g2d.setStroke(new BasicStroke(3));  // set a thicker outline for the highlighted boxes

            // draw a rectangle for each highlighted index
            for (int index : highlightedIndexes) {
                g2d.drawRect(index * width, yPosition - 15, width - 5, 30);
            }
//            g2d.dispose();  // clean up the graphics context (optional)
        }
    }
    
    // highlights a section of the array from start to end for visualization
    private void highlightSection(int start, int end) {
        // avoid potential issues if the indexes are out of bounds
        if (start < 0 || start >= end || end > numbers.size())
            return;

        // clear any previous highlights and add the new range to the highlightedIndexes list
        highlightedIndexes.clear();
        for (int i = start; i < end; i++) {
            highlightedIndexes.add(i);
        }
        repaint();  // trigger a repaint to update the visualization
    }

    // draws a highlighted section to visualize the range being processed
    private void drawHighlightedSection(Graphics g) {
        // check if there are highlighted indexes to draw
        if (!highlightedIndexes.isEmpty()) {
            int width = getWidth() / Math.max(numbers.size(), 1);  // width of each count box
            int yPosition = getHeight() / 2;  // Y position for the count boxes
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);  // set color to red for highlighting
            g2d.setStroke(new BasicStroke(3));  // set a thicker outline for the highlighted boxes

            // calculate the starting and ending index for the highlighted section
            int startIdx = highlightedIndexes.get(0);
            int endIdx = highlightedIndexes.get(highlightedIndexes.size() - 1);
            int highlightWidth = (endIdx - startIdx + 1) * width;

            // draw a rectangle for the highlighted section
            g2d.drawRect(startIdx * width, yPosition - 195, highlightWidth - 5, 60);
        }
    }

    // draws the numbers, counts, and output arrays for visualization
    private void drawNumbers(Graphics g) {
        int width = getWidth() / Math.max(numbers.size(), 1);  // width of each number box
        int height = 50;  // height of each number box

        // draw the numbers array
        for (int i = 0; i < numbers.size(); i++) {
            g.setColor(Color.CYAN);  // set color to cyan for the numbers
            g.fillRect(i * width, getHeight() / 3 - height / 2, width - 5, height);  // draw the number box

            g.setColor(Color.BLACK);  // set color to black for text
            // draw the number value inside the box
            g.drawString(String.valueOf(numbers.get(i)), i * width + width / 4, getHeight() / 3);
            
            // draw the index of the number
            g.drawString(String.valueOf(i), i * width + width / 4, getHeight() / 3 + 20);

            // draw a line above the index for visual separation
            int xStart = i * width;
            int yLine = getHeight() / 3 + 10;
            g.drawLine(xStart, yLine, xStart + width - 5, yLine);
        }
        
        // draw the count sort array
        int yPosition = getHeight() / 2;
        for (int i = 0; i < counts.length; i++) {
            g.setColor(Color.LIGHT_GRAY);  // set color to light gray for the counts
            g.fillRect(i * width, yPosition - height / 2, width - 5, height);  // draw the count box
            
            g.setColor(Color.BLACK);  // set color to black for text
            // draw the count value inside the box
            g.drawString(String.valueOf(counts[i]), i * width + width / 4, yPosition);

            // draw the index of the count
            g.drawString(String.valueOf(i), i * width + width / 4, yPosition + 20);

            // draw a line above the index for visual separation
            int xStart = i * width;
            int yLine = yPosition + 10;
            g.drawLine(xStart, yLine, xStart + width - 5, yLine);
        }

        // draw the output array
        yPosition = getHeight() * 2 / 3;
        if (outputArray == null) return;

        for (int i = 0; i < outputArray.length; i++) {
            g.setColor(Color.ORANGE);  // set color to orange for the output
            g.fillRect(i * width, yPosition - height / 2, width - 5, height);  // draw the output box

            g.setColor(Color.BLACK);  // set color to black for text
            // draw the output value inside the box
            g.drawString(String.valueOf(outputArray[i]), i * width + width / 4, yPosition);
            
            // draw the index of the output
            g.drawString(String.valueOf(i), i * width + width / 4, yPosition + 20);

            // draw a line above the index for visual separation
            int xStart = i * width;
            int yLine = yPosition + 10;
            g.drawLine(xStart, yLine, xStart + width - 5, yLine);
        }
    }
}
