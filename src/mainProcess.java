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
                endProcess(file);
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

            if (decodedMessage.isEmpty()) {
                System.out.println("No QR code was found.");
            } else {
                System.out.println("Found QR code.");

                String[] parts = decodedMessage.split(" ");

                int side1 = Integer.valueOf(parts[1]);

                String shape = parts[0];

                switch(shape) {
                    case "S" -> {
                        System.out.println("Drawing Square with side length of " + side1);
                        drawShape.drawSquare(swiftBot, side1);
//                        drawShape.drawSquare(swiftBot, side1);
                    }

                    case "T" -> {
                        try {
                        System.out.println("Drawing Square with side lengths of " + parts[1] + " " + parts[2] + " " + parts[3]);
//                        drawShape.drawTriangle(swiftBot, Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
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

    public static void endProcess(File file) throws InterruptedException {

        try {
            Scanner scanner = new Scanner(file);

            String shape;

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();

                // Printing shapes in order
                String[] splitData = data.split(" ");

                switch (splitData[0]) {
                    case "S" -> {
                        shape = "Square";
                    }
                    case "T" -> {
                        shape = "Triangle";
                    }
                    default -> {
                        shape = "Not found";
                    }
                }

                System.out.print(shape + " : ");
            }

            scanner.close();
            file.delete();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("Error finding file.");
        }

        System.out.println("System terminating in 2 seconds...");
        Thread.sleep(2000);
        System.exit(0);
        // print shapes // use text file maybe?

        // output largest shape

        // output most frequent drawn shape

        // output average time it took to draw all shapes
    }
}