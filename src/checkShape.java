public class checkShape {
    public static boolean checkTriangle(double side1, double side2, double side3) {

        if (side1 + side2 > side3 && side1 + side3 > side2 && side2 + side3 > side1) {
            return true;
        }

        return false;

    }

    public static boolean checkSideLength(int... sides) {

        for (int i = 0; i < sides.length; i++) {
            if (sides[i] < 15 || sides[i] > 85) {
                System.out.println("\u001B[33m" + "Invalid side length.\nMust be between 15cm and 85cm." + "\u001B[0m");
                return false;
            }
        }

        return true;
        
    }

    public static double[] calculateAngles(double side1, double side2, double side3) {
        double[] angles = new double[3];

        angles[0] = Math.toDegrees(Math.acos((side2 * side2 + side3 * side3 - side1 * side1) / (2 * side2 * side3)));
        angles[1] = Math.toDegrees(Math.acos((side1 * side1 + side3 * side3 - side2 * side2) / (2 * side1 * side3)));
        angles[2] = Math.toDegrees(Math.acos((side1 * side1 + side2 * side2 - side3 * side3) / (2 * side1 * side2)));

        return angles;
    }
}
