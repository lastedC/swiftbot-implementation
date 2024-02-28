import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import swiftbot.*;

public class mainProcess {

    static SwiftBotAPI swiftBot;

    public static void main(String[] args) throws InterruptedException {

        try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception exception) {
            System.out.println("Error loading API" + exception.getMessage());
        };

        AtomicBoolean run = new AtomicBoolean(true);

        swiftBot.enableButton(Button.X, () -> {
            run.set(false);

            endProcess();
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
                    }

                    case "T" -> {
                        try {
                        System.out.println("Drawing Square with side lengths of " + parts[1] + parts[2] + parts[3]);
                        drawShape.drawTriangle(swiftBot, Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
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

    public static void endProcess() {
        // print shapes // use text file maybe?

        // output largest shape

        // output most frequent drawn shape

        // output average time it took to draw all shapes
    }
}