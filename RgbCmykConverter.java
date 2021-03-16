import java.util.Arrays;

public class RgbCmykConverter {
	public static void main(String[] args) {
		int[] cmyk = rgbToCmyk(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

		System.out.println(Arrays.toString(cmyk));
	}

	public static int[] rgbToCmyk(int r, int g, int b) {
		// converts RGB values to percentages
		double percentageR = r / 255.0 * 100;
		double percentageG = g / 255.0 * 100;
		double percentageB = b / 255.0 * 100;

		// creates special key value in CMYK
		double k = 100 - Math.max(Math.max(percentageR, percentageG), percentageB);
		if (k == 100) {
			return new int[] { 0, 0, 0, 100 };
		}

		// translates RGB to CMY
		int c = (int) ((100 - percentageR - k) / (100 - k) * 100);
		int m = (int) ((100 - percentageG - k) / (100 - k) * 100);
		int y = (int) ((100 - percentageB - k) / (100 - k) * 100);

		// returns CMYK in an int array
		return new int[] { c, m, y, (int) k };
	}
}

//Works Cited:
//Tan, Martín. “Java RGB to CMYK Converter.” Martín Tan, MRTAN.ME, 29 Oct. 2019, mrtan.me/post/java-rgb-to-cmyk-converter.html. 