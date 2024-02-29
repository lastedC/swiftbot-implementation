import swiftbot.*;

public class draw {

    public static void drawSquare(SwiftBotAPI swiftBot, int sideLength) {

        if (!check.checkSideLength(sideLength)) {
            System.out.println("The length provided is invalid. Pleae make sure it is between 15 and 85.");
            return;
        };

        for (int sidesDrawn = 0; sidesDrawn < 4; sidesDrawn++) {
            // assume speed is 5m/s
            // distance needed in cm
            // time needed in milliseconds
            int time = (sideLength / 5) * 1000;

            try {
                swiftBot.move(100, 100, time);

                swiftBot.move(100, 0, 5000);
            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println("Error moving swiftbot.");
                System.exit(5);
            }
        };
   }

   public static void drawTriangle(SwiftBotAPI swiftBot, int side1, int side2, int side3) {

        if (!check.checkSideLength(side1, side2, side3)) {
            System.out.println("The length provided is invalid. Pleae make sure it is between 15 and 85.");
            return;
        };

        if (!check.checkTriangle(side1, side2, side3)) {
            System.out.println("The side lengths provided are unable to create a truangle.");
            return;
        };

        double[] angles = check.calculateAngles(side1, side2, side3);

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

            System.out.println("Current angle: " + currentAngle); // output for test purposes only
            int time = sideLength / 100;

            try {
                swiftBot.move(100, 100, time);

                // calculate turn time using currentAngle

                swiftBot.move(100, 0, 600);
            } catch (Exception exception) {
                System.out.println("Error moving swiftbot.");
            }

        }

    // while(sidesDrawn < 3) {

    //     int leftSpeed = -100;
    //     int rightSpeed = 100;
    //     int wheelbase = 10;

    //     double rightWheelDistance, leftWheelDistance;
    //     double R = wheelbase * (rightSpeed - leftSpeed) / (2 * (rightSpeed + leftSpeed));

    //     rightWheelDistance = Math.PI * R * angles[sidesDrawn] / 180.0; // Convert angle to radians
    //     leftWheelDistance = Math.PI * (R + wheelbase) * angles[sidesDrawn] / 180.0; // Convert angle to radians

    //     // Find the time required by the slower-moving wheel
    //     double slowerSpeed = Math.min(Math.abs(rightSpeed), Math.abs(leftSpeed));
    //     double turnTime = Math.abs(slowerSpeed) != 0 ? Math.abs(leftWheelDistance) / Math.abs(slowerSpeed) : 0;

    //     moveRobot.moveForward(currentSide);
    //     // Turns the bot 90 degrees clockwise //
    //     //API.move(leftSpeed, rightSpeed, turnTime);
    //     sidesDrawn += 1;
    // }
}
}
