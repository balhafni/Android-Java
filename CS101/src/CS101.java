

import java.util.Scanner;

/**
 *
 * @author dstroud
 * @date 2-29-16 Instructor Subrina
 * 
 */

public class CS101 {
	public static void main(String[] args) {
		System.out.println("Input the following. If you don't have it, input a 0 in its place");

		Scanner input = new Scanner(System.in);

		System.out.print("Enter base: "); // getting base input from user
		double base = input.nextDouble();

		System.out.print("Enter height: "); // getting height input from user
		double height = input.nextDouble();

		System.out.print("Enter length: "); // getting length input from user
		double length = input.nextDouble();

		System.out.print("Enter width: "); // getting width input from user
		double width = input.nextDouble();

		System.out.print("Enter radius: "); // getting radius input form user
		double radius = input.nextDouble();

		System.out.print("Enter length a: "); // getting radius input form user
		double lena = input.nextDouble();

		System.out.print("Enter length b: "); // getting radius input form user
		double lenb = input.nextDouble();

		System.out.print("Enter degrees: "); // getting radius input form user
		double degrees = input.nextDouble();

		areaOfTriange(base, height); // Calculations of triangle
		areaOfSquare(length); // Calculations of square
		areaOfRectangle(width, height); // Calculations of rectangle
		areaOfParallelogram(base, height); // Calculation of parallellogram
		areaOfTrapezoid(lena, lenb, height); // length side a and b
		areaOfCircle(radius);
		areaOfEllipse(lena, lenb);
		areaOfSector(radius, degrees);
	}

	public static void areaOfTriange(double base, double vheight) { // print
																	// triangle
																	// area
		double area = (1.0 / 2.0) * base * vheight;
		System.out.println("\nArea of triangle = " + area);
	}

	public static void areaOfSquare(double length) { // print square area
		double area = Math.pow(length, 2);
		System.out.println("\nArea of square = " + area);
	}

	public static void areaOfRectangle(double width, double height) { // print
																		// rectangle
																		// area
		double area = width * height;
		System.out.println("\nArea of rectangle = " + area);
	}

	public static void areaOfParallelogram(double base, double height) {
		double area = base * height;
		System.out.println("\nArea of parallelogram = " + area);
	}

	public static void areaOfCircle(double radius) {
		double area = 2 * Math.PI * radius;
		System.out.println("\nArea of circle = " + area);
	}

	public static void areaOfTrapezoid(double lena, double lenb, double height) {
		double area = (1.0 / 2.0) * (lena + lenb) * height;
		System.out.println("\nArea of a trapezoid = " + area);
	}

	public static void areaOfEllipse(double lena, double lenb) {
		double area = Math.PI * lena * lenb;
		System.out.println("Area of an ellipse = " + area);
	}

	public static void areaOfSector(double radius, double degrees) {
		double area = (1.0 / 2.0) * Math.pow(radius, 2) * Math.toRadians(degrees);
		System.out.println("Area of an sector = " + area);

	}
}
