import swiftbot.*;

public class drawShape {

    static SwiftBotAPI swiftBot;

    public static void drawSquare(int sideLength) {
        // if (!checkShape.checkSideLength(sideLength)) {
        //     System.err.println("Please enter a valid side length");
        //     return;
        // }

        // int sidesDrawn = 0;
        // int speed = 100;
        // int time = sideLength / speed;

        // while (sidesDrawn < 4) {
        //     API.move(speed, speed, time);
        //     API.move(speed, 0, 500);
        //     sidesDrawn += 1;
        // }
   }

   public static void drawTriangle(int side1, int side2, int side3) {

    // if (!checkShape.checkSideLength(side1, side2, side3)) {
    //     System.err.println("Please enter a valid side length.");
    //     return;
    // }

    // if (!checkShape.checkTriangle(side1, side2, side3)) {
    //     System.err.println("The side lenghts provided cannot create a triangle.");
    //     return;
    // }

    // int sidesDrawn = 0;
    // double[] angles = checkShape.calculateAngles(side1, side2, side3);

    // while(sidesDrawn < 3) {

    //     int currentSide = 0;

    //     // Find the time needed to move the SwiftBot for //
    //     if (sidesDrawn == 0) {
    //         currentSide = side1;
    //     } else if (sidesDrawn == 1) {
    //         currentSide = side2;
    //     } else if (sidesDrawn == 2) {
    //         currentSide = side3;
    //     }

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
