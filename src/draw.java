import swiftbot.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class draw {

    public static void drawSquare(SwiftBotAPI swiftBot, File file, int sideLength) {

        int totalTime = 0;

        if (!check.checkSideLength(sideLength)) {
            System.out.println("\u001B[33m" + "Invalid side length.\nMust be between 15cm and 85cm." + "\u001B[0m");
            return;
        };

        for (int sidesDrawn = 0; sidesDrawn < 4; sidesDrawn++) {
            // assume speed is 5m/s
            // distance needed in cm
            // time needed in milliseconds
            int time = (sideLength / 5) * 1000;

            totalTime += time + 2500;

            try {
                swiftBot.move(25, 25, time);

                swiftBot.move(25, 0, 2500);
            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println("\u001B[31m" + "Error moving swiftbot." + "\u001B[0m");
                System.exit(5);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("S " + totalTime/1000 + " " + sideLength + " 0 0 \n");
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("\u001B[31m" + "Error writing to file: " + file.getName() + "\u001B[0m");
        }

        System.out.println("\u001B[32m" + "Square drawn successfully.\n" + "\u001B[0m");
    };

   public static void drawTriangle(SwiftBotAPI swiftBot, File file, int side1, int side2, int side3) {

        if (!check.checkSideLength(side1, side2, side3)) {
            System.out.println("\u001B[33m" + "Invalid side length.\nMust be between 15cm and 85cm." + "\u001B[0m");
            return;
        };

        if (!check.checkTriangle(side1, side2, side3)) {
            System.out.println("\u001B[33m" + "Lengths provided are unable to create a triangle." + "\u001B[0m");
            return;
        };

        double[] angles = check.calculateAngles(side1, side2, side3);

        int totalTime = 0;

        for (int sidesDrawn = 0; sidesDrawn < 3; sidesDrawn++) {

            int sideLength = 0;
            double currentAngle = 0;

            switch (sidesDrawn) {

                case 0 -> {
                    sideLength = side1;
                    currentAngle = angles[0];
                }

                case 1 -> {
                    sideLength = side2;
                    currentAngle = angles[1];
                }

                case 2 -> {
                    sideLength = side3;
                    currentAngle = angles[2];
                }
            }

            int time = (sideLength / 5) * 1000;
            int turnTime = 0;

            try {
                swiftBot.move(25, 25, time);

                // calculate turn time using currentAngle

                turnTime = (int) ((Math.toRadians(currentAngle) / 5) * 1000);

                totalTime += time + turnTime;

                swiftBot.move(25, 0, turnTime);
            } catch (Exception exception) {
                System.out.println("\u001B[31m" + "Error moving swiftbot." + "\u001B[0m");
            }

        }

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("T " + totalTime/1000 + " " + side1 + " " + side2 + " " + side3 + "\n");
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("\u001B[31m" + "Error writing to file: " + file.getName() + "\u001B[0m");
        }

        System.out.println("\u001B[32m" + "Triangle drawn successfully.\n" + "\u001B[0m");
    }
}
