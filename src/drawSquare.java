public class drawSquare {
    public static void main(int sideLength) {

        if(!checkShape.checkSideLength(sideLength)) {
            System.err.println("Please enter a valid side length");
            return;
        }

        int sidesDrawn = 0;

        while(sidesDrawn < 4) {
            moveRobot.moveForward(sideLength);
            moveRobot.turn90();
            sidesDrawn += 1;
        }
    }
}
