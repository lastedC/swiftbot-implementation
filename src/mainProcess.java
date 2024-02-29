import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import swiftbot.*;

public class mainProcess {

    static SwiftBotAPI swiftBot;

    public static void main(String[] args) throws InterruptedException, IOException {

        try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception exception) {
            System.out.println("Error loading API" + exception.getMessage());
        };

        File file = new File("shapes.txt");

        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        };

        AtomicBoolean run = new AtomicBoolean(true);

        swiftBot.enableButton(Button.X, () -> {
            run.set(false);

            try {
                end(file);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                System.out.println("Error running termination process.");
            }
        });

        String decodedMessage = "";
        // Continously scanning for a QR code using the camera.
        while (run.get()) {

            try {
                System.out.println("Scanning for QR code...");
                Thread.sleep(2000);

                BufferedImage img = swiftBot.getQRImage();
                decodedMessage = swiftBot.decodeQRImage(img);
            } catch (Exception exception) {
                System.out.println("Error finding QR code.");
                exception.printStackTrace();
                System.exit(5);
            }

            String[] parts = decodedMessage.split(" ");

            if (decodedMessage.isEmpty()) {
                System.out.println("No QR code was found.");
            } else {
                System.out.println("Found QR code.");

                int side1 = Integer.valueOf(parts[1]);

                String shape = parts[0];

                switch(shape) {
                    case "S" -> {
                        System.out.println("Drawing Square with side length of " + side1);
                        draw.drawSquare(swiftBot, file, side1);
                    }

                    case "T" -> {
                        try {
                        System.out.println("Drawing Square with side lengths of " + parts[1] + " " + parts[2] + " " + parts[3]);
                        draw.drawTriangle(swiftBot, file, Integer.valueOf(side1), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
                        } catch (Exception exception) {
                            System.out.println("Error processing triangle sides.");
                        }
                    }

                    default -> {
                        System.out.println("Invalid shape type: " + parts[0]);
                    }
                }
            }
        }
    }

    public static void end(File file) throws InterruptedException {

        // FILE FORMAT: "shape time side1 side2 side3"

        /* Output Template:
         * Shape : SideLengths : AverageTime
         */

        int numberOfSquares = 0;
        int numberOfTriangles = 0;
        String mostFrequentShape = "";

        try {
            Scanner scanner = new Scanner(file);

            String string = "B 0 0 0";
            String[] largestShape = string.split(" ");

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();

                // Printing shapes in order
                String[] splitData = data.split(" ");

                String shape = splitData[0];
                int averageTime = Integer.valueOf(splitData[1]);

                int side1 = Integer.valueOf(splitData[2]);
                int side2 = Integer.valueOf(splitData[3]);
                int side3 = Integer.valueOf(splitData[4]);

                switch (shape) {
                    case "S" -> {
                        System.out.println("Square : " + side1 + "cm : " + averageTime + " seconds");

                        if (Integer.valueOf(side1) > Integer.valueOf(largestShape[1])) {
                            largestShape[0] = "Square";
                            largestShape[1] = Integer.toString(side1);
                        }

                        numberOfSquares += 1;
                    }
                    case "T" -> {
                        System.out.println("Triangle : " + side1 + "cm " + side2 + "cm " + side3 + "cm : " + averageTime + " seconds");

                        if (Integer.valueOf(side1) > Integer.valueOf(largestShape[1]) || Integer.valueOf(side2) > Integer.valueOf(largestShape[2]) || (Integer.valueOf(side3) > Integer.valueOf(largestShape[3]))) {
                            largestShape[0] = "Triangle";
                            for (int v = 1; v <= 3; v++) {
                                largestShape[v] = splitData[v+1];
                            }
                        }

                        numberOfTriangles += 1;
                    }
                    default -> {
                        shape = "Shape data unavailable";
                    }
                }

                if (largestShape[0] == "Square") {
                    System.out.println("The largest shape was a " + largestShape[0] + " with side(s) of : " + largestShape[1] + "cm");
                } else if (largestShape[0] == "Triange") {
                    System.out.println("The largest shape was a " + largestShape[0] + " with side(s) of : " + largestShape[1] + "cm | " + largestShape[2] + "cm | " + largestShape[3] + "cm");
                } else {
                    System.out.println("Error finding largest shape.");
                }

                

                if (numberOfSquares > numberOfTriangles) {
                    mostFrequentShape = "Square";
                } else if (numberOfSquares < numberOfTriangles) {
                    mostFrequentShape = "Triangle";
                } else {
                    mostFrequentShape = "Square & Triangle";
                }
                
                System.out.println("The most frequently drawn shape was : " + mostFrequentShape);
            }

            Thread.sleep(1000);

            scanner.close();
            file.delete();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("Error finding file.");
        }

        System.out.println("System terminating in 2 seconds...");
        Thread.sleep(2000);
        System.exit(0);
        // print shapes // use text file maybe? // DONE

        // output largest shape // DONE

        // output most frequent drawn shape // DONE

        // output average time it took to draw all shapes DONE
    }
}