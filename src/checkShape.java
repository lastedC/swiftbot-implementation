public class checkShape {
    public static boolean checkTriangle(double side1, double side2, double side3) {

        if (side1 + side2 > side3 && side1 + side3 > side2 && side2 + side3 > side1) {
            return true;
        }

        return false;

    }

    public static boolean checkSideLength(int... sides) {

        int side1 = sides[0];

        if (sides.length < 1) {
            if (side1 < 15 && side1 > 85) {
                System.err.println("Invalid side length");
                return false;
            }
        }

        if (sides.length > 1) {
            int side2 = sides[1];
            int side3 = sides[2];

            if (side1 < 15 && side1 > 85 &&
            side2 < 15 && side2 > 85 &&
            side3 < 15 && side3 > 85) {
            System.err.println("Invaluded side length");
            return false;
            }
        }

        return true;
        
    }
}
