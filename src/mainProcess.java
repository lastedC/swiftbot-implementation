public class mainProcess {
    public static void main(String[] args) {

      // BufferedImage img = API.GetQRImage();

      String decodedText = "T 16 17 16";
      if (decodedText.isEmpty()) {
         System.err.println("No decoded text");
      }

      String inputShape = decodedText;

            String[] parts = inputShape.split(" ");
            int side1 = Integer.valueOf(parts[1]);
            int side2 = Integer.valueOf(parts[2]);
            int side3 = Integer.valueOf(parts[3]);

            String shape = parts[0];
            String square = "S";
            String triangle = "T";

            if (shape == square) {
               drawSquare.main(side1); 
            } else if (shape == triangle) {
               drawTriangle.main(side1, side2, side3);
            } else {
               System.err.println("Invalid Shape Type: " + parts[0]);
            }
   }
}