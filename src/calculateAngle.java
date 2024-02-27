public class calculateAngle {
    public static double[] calculateAngles(double side1, double side2, double side3) {
        double[] angles = new double[3];

        // Using law of cosines to calculate each angle
        angles[0] = Math.toDegrees(Math.acos((side2 * side2 + side3 * side3 - side1 * side1) / (2 * side2 * side3)));
        angles[1] = Math.toDegrees(Math.acos((side1 * side1 + side3 * side3 - side2 * side2) / (2 * side1 * side3)));
        angles[2] = Math.toDegrees(Math.acos((side1 * side1 + side2 * side2 - side3 * side3) / (2 * side1 * side2)));

        return angles;
    }
}
