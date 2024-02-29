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
                // System.out.println("Scanning for QR code");
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
                // System.out.println("No QR code was found.");
            } else {
                System.out.println("Found QR code.");

                int side1 = Integer.valueOf(parts[1]);

                String shape = parts[0];

                switch(shape) {
                    case "S" -> {
                        System.out.println("Drawing square. Side Length: " + side1);
                        draw.drawSquare(swiftBot, file, side1);
                        System.out.println("Scanning for QR code\n");
                    }

                    case "T" -> {
                        try {
                        System.out.println("Drawing triangle. Side lengths: " + parts[1] + " : " + parts[2] + " : " + parts[3]);
                        draw.drawTriangle(swiftBot, file, Integer.valueOf(side1), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
                        } catch (Exception exception) {
                            System.out.println("Error processing triangle sides.");
                        }
                        System.out.println("Scanning for QR code\n");
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

        System.out.println("\n---------------------------------------\n");

        int numberOfSquares = 0;
        int numberOfTriangles = 0;
        String mostFrequentShape = "";

        try {

            System.out.println("\nOutputing all shapes drawn:\n");

            Scanner scanner = new Scanner(file);

            String string = "B 0 0 0"; // B -> Blank
            String[] largestShape = string.split(" ");

            System.out.println("  Shape  |    Side Lengths    | Time To Draw\n------------------------------------------");

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
                        System.out.println(" Square  |        " + side1 + "cm        | " + averageTime + " seconds");

                        /*
                        * "  Shape  |    Side Lengths    | Time To Draw"
                        * "------------------------------------------"
                        * " Square  |        14cm        | 18 seconds"
                        * "Triangle | 12cm : 16cm : 15cm | 8 seconds"
                        */

                        if (Integer.valueOf(side1) > Integer.valueOf(largestShape[1])) {
                            largestShape[0] = "Square";
                            largestShape[1] = Integer.toString(side1);
                        }

                        numberOfSquares += 1;
                    }
                    case "T" -> {
                        System.out.println("Triangle | " + side1 + "cm : " + side2 + "cm : " + side3 + "cm | " + averageTime + " seconds");

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
            }

            System.out.println("\n Largest Shape:");

            if (largestShape[0] == "Square") {
                System.out.println("Shape: " + largestShape[0] + "| Side(s): " + largestShape[1] + "cm");
            } else if (largestShape[0] == "Triange") {
                System.out.println("Shape: " + largestShape[0] + "| Side(s): " + largestShape[1] + "cm : " + largestShape[2] + "cm : " + largestShape[3] + "cm");
            } else {
                System.out.println("Error finding largest shape.");
            }

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
                System.out.println("Shape: " + mostFrequentShape + " | Drawn " + numberofInsantces + " times.");
            }

            Thread.sleep(1000);

            scanner.close();
            file.delete();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("\u001B[31m" + "Error finding file." + "\u001B[0m");
        }

        System.out.println("\u001B[35m" + "\nSystem terminating in 2 seconds..." + "\u001B[0m");
        Thread.sleep(2000);
        System.exit(0);
    }
}