import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {

		//// Hide / change / add to the testing code below, as needed.

		// Tests the reading and printing of an image:
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);

		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/**
	 * Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file.
	 */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array.
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt());
			}
		}

		return image;
	}

	// Prints the RGB values of a given color.
	private static void print(Color c) {
		System.out.print("(");
		System.out.printf("%3s,", c.getRed()); // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
		System.out.printf("%3s", c.getBlue()); // Prints the blue component
		System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting
	// image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array
		//// using the print(Color) function.
		for (int i = 0; i < image.length; i++) { // Loop through rows
			for (int j = 0; j < image[i].length; j++) { // Loop through columns
				print(image[i][j]); // Print the current pixel
			}
			// System.out.println();
		}
	}

	/**
	 * Returns an image which is the horizontally flipped version of the given
	 * image.
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] hFlip = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				hFlip[i][j] = image[i][image[0].length - j - 1];
			}

		}
		return hFlip;
	}

	/**
	 * Returns an image which is the vertically flipped version of the given image.
	 */
	public static Color[][] flippedVertically(Color[][] image) {
		Color[][] vFlip = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				vFlip[i][j] = image[image.length - i - 1][j];
			}

		}
		return vFlip;
	}

	// Computes the luminance of the RGB values of the given pixel, using the
	// formula
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object
	// consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int lum = (int) (pixel.getRed() * 0.299 + pixel.getGreen() * 0.587 + pixel.getBlue() * 0.114);
		Color lumColor = new Color(lum, lum, lum);
		return lumColor;
	}

	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] grayIMG = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				grayIMG[i][j] = luminance(image[i][j]);
			}

		}
		return grayIMG;
	}

	/**
	 * Returns an image which is the scaled version of the given image.
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scaIMG = new Color[height][width];

		double rowRatio = (double) image.length / height;
		double colRatio = (double) image[0].length / width;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				scaIMG[i][j] = image[(int) (i * rowRatio)][(int) (j * colRatio)];
			}
		}
		return scaIMG;
	}

	/**
	 * Computes and returns a blended color which is a linear combination of the two
	 * given
	 * colors. Each r, g, b, value v in the returned color is calculated using the
	 * formula
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r,
	 * g, b
	 * values in the two input color.
	 * 0. 25 · 100 + 0. 75 · 200 = 175
	 * 0. 25 · 40 + 0. 75 · 20 = 25
	 * 0. 25 · 100 + 0. 75 · 40 = 55
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		double beta = 1 - alpha;
		int newRed = (int) (alpha * c1.getRed() + beta * c2.getRed());
		int newGreen = (int) (alpha * c1.getGreen() + beta * c2.getGreen());
		int newBlue = (int) (alpha * c1.getBlue() + beta * c2.getBlue());

		Color blendedColor = new Color(newRed, newGreen, newBlue);

		return blendedColor;
	}

	/**
	 * Cosntructs and returns an image which is the blending of the two given
	 * images.
	 * The blended image is the linear combination of (alpha) part of the first
	 * image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blendIMG = new Color[image1.length][image1[0].length];
		for (int i = 0; i < image1.length; i++) {
			// System.out.println(image2.length + "-------" + image1.length);
			for (int j = 0; j < image1[0].length; j++) {
				if (i < image2.length) {
					blendIMG[i][j] = blend(image1[i][j], image2[i][j], alpha);
				} else {
					blendIMG[i][j] = blend(image1[i][j], image2[image2.length - 1][j], alpha);
				}
			}
		}
		return blendIMG;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 * Morphing: suppose we want to morph a source image into a target image
	 * gradually, in n steps. To do
	 * so, we stage a sequence of 0, 1, 2, ... , n steps, as follows. In each step i
	 * we blend the source image and
	 * the target image using α = (n − i)/n. For example, here is what happens when
	 * n = 3:
	 * step 0: Blend the two images using α = 3/3 (yielding the source image)
	 * step 1: Blend the two images using α = 2/3
	 * step 2: Blend the two images using α = 1/3
	 * step 3: Blend the two images using α = 0/3 (yielding the target image)
	 * The function void morph(Color[][] source, Color[][] target, int n) morphs the
	 * source
	 * image into the target image in n steps. If the images don't have the same
	 * dimensions, the function
	 * scales the target image to the dimensions of the source image, and then
	 * starts the morphing sequence.
	 * At the end of each blending step, the function should use the
	 * Runigram.display function to display
	 * the intermediate result, and the StdDraw.pause function to pause for about
	 * 500 milliseconds.
	 * Implement and test the morph function. Note: The canvas for this morphing
	 * operation is set by the
	 * caller of the morph function, as we now turn to discuss.
	 */

	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] scaledTarget = scaled(target, source.length, source[0].length);
		Color[][] morphedImage = new Color[source.length][source[0].length];
		Runigram.setCanvas(source);

		for (int i = n; i >= 0; i--) {
			double alpha = (double) i / n;
			// System.out.println(alpha);

			morphedImage = blend(source, scaledTarget, alpha);

			Runigram.display(morphedImage);
			StdDraw.pause(500);
		}

	}

	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		// Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor(image[i][j].getRed(),
						image[i][j].getGreen(),
						image[i][j].getBlue());
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}