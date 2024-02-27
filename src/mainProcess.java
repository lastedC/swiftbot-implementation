SwiftBotAPI API = new SwiftBotAPI();

public class mainProcess {
    public static void main(String[] args) {

      BufferedImage img = API.GetQRImage();

      try{
          String decodedText = API.decodeQRImage(img);
          if (!decodedText.isEmpty()) {
              System.out.println(decodedText);
          }
      } catch(IllegalArgumentException e) {
          e.printStackTrace();
      }

      if (decodedText.isEmpty()) {
         System.err.println("No decoded text");
      }

      String inputString = decodedText;

            String[] parts = inputString.split(" ");
            int side1 = Integer.valueOf(parts[1]);
            int side2 = Integer.valueOf(parts[2]);
            int side3 = Integer.valueOf(parts[3]);

            String shape = parts[0];
            String square = "S";
            String triangle = "T";

            if (shape == square) {
               drawSquare(side1);
            } else if (shape == triangle) {
               drawTriangle(side1, side2, side3);
            } else {
               System.err.println("Invalid Shape Type: " + parts[0]);
            }
   }

   public static void drawSquare(int sideLength) {

        if (!checkShape.checkSideLength(sideLength)) {
            System.err.println("Please enter a valid side length");
            return;
        }

        int sidesDrawn = 0;
        int speed = 100;
        int time = sideLength / speed;

        while (sidesDrawn < 4) {
            API.move(speed, speed, time);
            API.move(speed, 0, 500);
            sidesDrawn += 1;
        }
   }

    public static void drawTriangle(int side1, int side2, int side3) {

        if (!checkShape.checkSideLength(side1, side2, side3)) {
            System.err.println("Please enter a valid side length.");
            return;
        }

        if (!checkShape.checkTriangle(side1, side2, side3)) {
            System.err.println("The side lenghts provided cannot create a triangle.");
            return;
        }

        int sidesDrawn = 0;
        double[] angles = checkShape.calculateAngles(side1, side2, side3);

        while(sidesDrawn < 3) {

            int currentSide = 0;

            // Find the time needed to move the SwiftBot for //
            if (sidesDrawn == 0) {
                currentSide = side1;
            } else if (sidesDrawn == 1) {
                currentSide = side2;
            } else if (sidesDrawn == 2) {
                currentSide = side3;
            }

            int leftSpeed = -100;
            int rightSpeed = 100;
            int wheelbase = 10;

            double rightWheelDistance, leftWheelDistance;
            double R = wheelbase * (rightSpeed - leftSpeed) / (2 * (rightSpeed + leftSpeed));

            rightWheelDistance = Math.PI * R * angles[sidesDrawn] / 180.0; // Convert angle to radians
            leftWheelDistance = Math.PI * (R + wheelbase) * angles[sidesDrawn] / 180.0; // Convert angle to radians

            // Find the time required by the slower-moving wheel
            double slowerSpeed = Math.min(Math.abs(rightSpeed), Math.abs(leftSpeed));
            double turnTime = Math.abs(slowerSpeed) != 0 ? Math.abs(leftWheelDistance) / Math.abs(slowerSpeed) : 0;

            moveRobot.moveForward(currentSide);
            // Turns the bot 90 degrees clockwise //
            //API.move(leftSpeed, rightSpeed, turnTime);
            sidesDrawn += 1;
        }
    }

    public static void endProcess() {

    }
}