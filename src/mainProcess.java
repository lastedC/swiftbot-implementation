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

        System.out.println("---------------------------------------\nDRAW SHAPE PROGRAM     By: Shihab Marey\n---------------------------------------");
        Thread.sleep(1000);
        System.out.println("\nLoading API...");

        try {
            swiftBot = new SwiftBotAPI();
            System.out.println("API loaded successfully.");
        } catch (Exception exception) {
            System.out.println("Error loading API" + exception.getMessage());
            System.exit(5);
        };

        File file = new File("shapes.txt");

        System.out.println("Creating file...");
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

        System.out.println("\nStarting program...");
        Thread.sleep(1000);
        System.out.println("\n---------------------------------------\n");

        String decodedMessage = "";
        // Continously scanning for a QR code using the camera.
        System.out.println("Please scan QR code.\n");
        while (run.get()) {

            try {
                BufferedImage img = swiftBot.getQRImage();
                decodedMessage = swiftBot.decodeQRImage(img);
            } catch (Exception exception) {
                System.out.println("Error finding QR code.");
                exception.printStackTrace();
                System.exit(5);
            }

            String[] parts = decodedMessage.split(" ");

            if (decodedMessage.isEmpty()) {
                // do nothing and return to scanning
            } else {
                System.out.println("Found QR code.");

                if (parts.length < 2) {
                    System.out.println("\u001B[33m" + "Please provide a valid input." + "\u001B[0m");
                    continue; // continue scanning
                }

                if (parts[0].length() > 1) {
                    System.out.println("\u001B[33m" + "Please provide a valid input." + "\u001B[0m");
                    continue; // continue scanning
                }
                

                int side1 = Integer.valueOf(parts[1]);

                String shape = parts[0];

                switch(shape) {
                    case "S" -> {
                        System.out.println("Drawing square. Side Length: " + parts[1]);
                        drawShape.drawSquare(swiftBot, file, side1);
                        System.out.println("Scanning for QR code\n");
                    }

                    case "T" -> {
                        try {
                        System.out.println("Drawing triangle. Side lengths: " + parts[1] + " : " + parts[2] + " : " + parts[3]);
                        drawShape.drawTriangle(swiftBot, file, Integer.valueOf(side1), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
                        } catch (Exception exception) {
                            System.out.println("Error processing triangle sides.");
                        }
                        System.out.println("Scanning for QR code\n");
                    }

                    default -> {
                        System.out.println("\u001B[31m" + "Invalid Shape Type." + parts[0] + "\u001B[0m");
                    }
                }
            }
        }
    }

    public static void end(File file) throws InterruptedException {

        swiftBot.disableButton(Button.X);

        if (file.length() == 0) {
            file.delete();
            System.out.println("No shapes drawn.");
            terminate();
        }

        // FILE FORMAT: "shape time side1 side2 side3"

        /* Output Template:
         * Shape : SideLengths : AverageTime
         */

        System.out.println("\n---------------------------------------\n");

        Thread.sleep(500);

        int numberOfSquares = 0;
        int numberOfTriangles = 0;
        String mostFrequentShape = "";

        try {

            System.out.println("\n All shapes:\n");

            Scanner scanner = new Scanner(file);

            String string = "B 0"; // B -> Blank | 0 -> Largest Side
            String[] largestShape = string.split(" ");

            String largestShapeName = largestShape[0];
            int largestShapeSize = Integer.valueOf(largestShape[1]);

            System.out.println("  Shape  |    Side Lengths    | Time To Draw\n--------------------------------------------");

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] splitData = data.split(" ");
                if (splitData.length < 5) {
                    System.out.println("Invalid data format: " + data);
                    continue;
                }

                if (splitData.length < 5) {
                    System.out.println("Incomplete data for shape: " + data);
                    continue;
                }

                String shape = splitData[0];
                int averageTime = Integer.valueOf(splitData[1]);

                int side1 = Integer.valueOf(splitData[2]);
                int side2 = Integer.valueOf(splitData[3]);
                int side3 = Integer.valueOf(splitData[4]);

                switch (shape) {
                    case "S" -> {
                        System.out.println(" Square  |        " + side1 + "cm        | " + averageTime + " seconds");

                        /*
                        * "  Shape  |    Side Lengths    | Time To Draw"
                        * "------------------------------------------"
                        * " Square  |        14cm        | 18 seconds"
                        * "Triangle | 12cm : 16cm : 15cm | 8 seconds"
                        */

                        if (Integer.valueOf(side1) > largestShapeSize) {
                            largestShapeName = "Square";
                            largestShapeSize = side1;
                        }
                        numberOfSquares += 1;
                        break;
                    }
                    case "T" -> {
                        System.out.println("Triangle | " + side1 + "cm : " + side2 + "cm : " + side3 + "cm | " + averageTime + " seconds");

                        for (int i = 1; i <= 3; i++) {
                            if (Integer.valueOf(splitData[i]) > largestShapeSize) {
                                largestShapeName = "Triangle";
                                largestShapeSize = Integer.valueOf(splitData[i]);
                            }
                        }
                        numberOfTriangles += 1;
                        break;
                    }
                    default -> {
                        shape = "\u001B[31m" + "U" + "\u001B[0m";
                        side1 = side2 = side3 = 0;
                    }
                }
            }

            Thread.sleep(1500);

            System.out.println("\n Largest Shape:");

            if (largestShapeName != "B") {
                System.out.println("Shape: " + largestShapeName + " | Largest side: " + largestShapeSize + "cm");
            } else {
                System.out.println("\u001B[31m" + "Error finding largest shape." + "\u001B[0m");
            }

            Thread.sleep(1500);

            System.out.println("\n Most Frequent Shape:");

            int numberofInsantces = 0;

            if (numberOfSquares > numberOfTriangles) {
                mostFrequentShape = "Square";
                numberofInsantces = numberOfSquares;
            } else if (numberOfSquares < numberOfTriangles) {
                mostFrequentShape = "Triangle";
                numberofInsantces = numberOfTriangles;
            } else if (numberOfSquares == numberOfTriangles) {
                mostFrequentShape = "Square & Triangle";
                numberofInsantces = numberOfSquares;
            } else {
                mostFrequentShape = " ";
            }
            
            if (mostFrequentShape != " ") {
                System.out.println("Shape: " + mostFrequentShape + " | Drawn " + numberofInsantces + " time(s).");
            }

            Thread.sleep(1000);

            scanner.close();
            file.delete();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("\u001B[31m" + "Error finding file." + "\u001B[0m");
        }

        terminate();
    }

    public static void terminate() throws InterruptedException {
        int[] red = {255, 0, 0};
        try {
            swiftBot.fillUnderlights(red);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        System.out.println("\u001B[35m" + "\nSystem terminating in 5 seconds..." + "\u001B[0m");
        Thread.sleep(5000);
        try {
            swiftBot.disableUnderlights();
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}