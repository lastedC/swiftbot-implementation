import java.awt.image.BufferedImage;

import swiftbot.*;

public class main {

    static SwiftBotAPI swiftBot;

    public static void mainProcess(String[] args) {

        try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception e) {
            System.out.println("Error loading API" + e.getMessage());
        };

        // Continously scanning for a QR code using the camera.
        while (true) {
            try {
                System.out.println("Scanning for QR code in 5 seconds...");
                Thread.sleep(5000);

                BufferedImage img = swiftBot.getQRImage();
                String decodedMessage = swiftBot.decodeQRImage(img);

                if (decodedMessage.isEmpty()) {
                    System.out.println("No QR code was found.");
                } else {
                    System.out.println("Found QR code.");

                    String[] parts = decodedMessage.split(" ");

                    int side1 = Integer.valueOf(parts[1]);
                    int side2 = Integer.valueOf(parts[2]);
                    int side3 = Integer.valueOf(parts[3]);

                    switch(parts[0]) {
                        case "S" -> {
                            System.out.println("Drawing Square with side length of " + side1);
                            drawShape.drawSquare(side1);
                        }

                        case "T" -> {
                            System.out.println("Drawing Square with side lengths of " + side1 + side2 + side3);
                            drawShape.drawTriangle(side1, side2, side3);
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
            } catch (Exception e) {
                System.out.println("Error finding QR code.");
                e.printStackTrace();
                System.exit(5);
            }
        }
    }

    public static void endProcess() {

    }
}