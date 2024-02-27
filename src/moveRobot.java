public class moveRobot {
    public static void turn90() {
        API.move(2, 0, 5);
    }

    public static void turnTriangle() {

    }

    public static void moveForward(int sideLength) {

        int speed = 5;
        int time = sideLength / speed;

        API.move(speed, speed, time);
    }
}
