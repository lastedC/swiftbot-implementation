import java.awt.image.BufferedImage;

import swiftbot.*;

public class main {

    static SwiftBotAPI swiftBot;

    public static void mainProcess(String[] args) {

        try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception exception) {
            System.out.println("Error loading API" + exception.getMessage());
        };

        String decodedMessage = "";

        // Continously scanning for a QR code using the camera.
        while (true) {
            try {
                System.out.println("Scanning for QR code in 5 seconds...");
                Thread.sleep(5000);

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
                        drawShape.drawSquare(side1);
                    }

                    case "T" -> {
                        try {
                        System.out.println("Drawing Square with side lengths of " + parts[1] + parts[2] + parts[3]);
                        drawShape.drawTriangle(Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
                        } catch (Exception exception) {
                            System.out.println("Error processing triangle sides.");
                        }
                    }

                    default -> {
                        System.out.println("Invalid shape type: " + parts[0]);
                    }
                }

                // if (parts[0] == "S") {
                //     System.out.println("Drawing Square with side length of " + side1);
                //     drawSquare(side1);
                // } else if (parts[0] == "T") {
                //     System.out.println("Drawing Square with side lengths of " + side1 + side2 + side3);
                //     drawTriangle(side1, side2, side3);
                // } else {
                //     System.out.println("Invalid shape type: " + parts[0]);
                // }
            }
        }
    }

    public static void endProcess() {

    }
}