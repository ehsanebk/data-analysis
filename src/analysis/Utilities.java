package analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Vector;

public class Utilities {
	public static DecimalFormat df0 = new DecimalFormat("0");
	public static DecimalFormat df1 = new DecimalFormat("#.0");
	public static DecimalFormat df2 = new DecimalFormat("#.00");
	public static DecimalFormat df3 = new DecimalFormat("#.000");
	public static DecimalFormat df4 = new DecimalFormat("#.0000");
	public static DecimalFormat df5 = new DecimalFormat("#.00000");
	public static DecimalFormat df8 = new DecimalFormat("#.00000000");

	public static int sign(double x) {
		return (x >= 0) ? 1 : -1;
	}

	public static double sqr(double x) {
		return (x * x);
	}

	public static double rotationAngle(double hx, double hz) {
		return (-180 * (Math.atan2(hz, hx)) / Math.PI);
	}

	public static double deg2rad(double x) {
		return x * (Math.PI / 180.0);
	}

	public static double rad2deg(double x) {
		return x * (180.0 / Math.PI);
	}

	public static double mps2mph(double x) {
		return x * 2.237;
	}

	public static double mph2mps(double x) {
		return x / 2.237;
	}

	public static double in2cm(double x) {
		return x * 2.54;
	}

	static double cm2in(double x) {
		return x / 2.54;
	}

	public static double minsigned(double x, double m) {
		return (x < 0) ? Math.max(x, -m) : Math.min(x, m);
	}

	public static double maxsigned(double x, double m) {
		return (x < 0) ? Math.min(x, -m) : Math.max(x, m);
	}

	public static String repeatString(String s, int n) {
		String res = "";
		for (int i = 0; i < n; i++)
			res += s;
		return res;
	}

	public static int count(char c, String s) {
		int res = 0;
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == c)
				res++;
		return res;
	}

	public static PrintStream uniqueOutputFile(String name) {
		int num = 1;
		File file;
		String filename;
		do {
			filename = name + num + ".txt";
			file = new File(filename);
			num++;
		} while (file.exists());
		PrintStream stream = null;
		try {
			stream = new PrintStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stream;
	}
	
	public static boolean arrayContains (int [] array, int element){
		for ( int a: array){
			if ( a == element)
				return true;
		}
		return false;
	}
	
	public static boolean arrayContains (String [] array, String element){
		return Arrays.asList(array).contains(element);
	}

	public static double toDouble(String s){
		try {
			return Double.valueOf(s).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
	public static int toInt(String s){
		try {
			return Integer.valueOf(s).intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	public static long toLong(String s){
		try {
			return Long.valueOf(s).longValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	// values:    an array of numbers that will be modified in place
	// smoothing: the strength of the smoothing filter; 1=no change, larger values smoothes more
	// since the invalid values are -100 there are if statements which check how the values are before and after
	public static Values smoothArray( Values values, int smoothing ){
	  
		Values output = new Values();
		double  value = values.get(0); // start with the first input
		output.add(value);
		for (int i =1; i < values.size(); ++i){
			if (!(values.get(i-1) == -100) && !(values.get(i) == -100) ){
				double currentValue = values.get(i);
				value += (currentValue - value) / smoothing;
				output.add(value);
			}
			else if ((values.get(i) == -100) ){
				output.add(-100.0);
			}
			else if ((values.get(i-1) == -100) && !(values.get(i) == -100) ){
				value = values.get(i); // start again
				output.add(value);
			}
		}
		return output;
	}

	// Return RC low-pass filter output samples, given input samples,
	// time interval dt, and time constant RC
	public static Values lowpass( Values values,double CUTOFF, double SAMPLE_RATE){
		  
		Values output = new Values();
		double  value = values.get(0); // start with the first input
		output.add(value);
		
		double RC = 1.0/(CUTOFF*2*3.14); 
	    double dt = 1.0/SAMPLE_RATE; 
	    double alpha = dt/(RC+dt);
		
		for (int i =1; i < values.size(); ++i){
			if (!(values.get(i-1) == -100) && !(values.get(i) == -100) ){
				value = output.get(i-1) + (alpha*(values.get(i) - output.get(i-1)));
				output.add(value);
			}
			else if ((values.get(i) == -100) ){
				output.add(-100.0);
			}
			else if ((values.get(i-1) == -100) && !(values.get(i) == -100) ){
				value = values.get(i); // start again
				output.add(value);
			}
		}
		return output;
	}
	// Return RC low-pass filter output samples, given input samples,
	// value of alpha is between 0 and 1. the curve will be smoother if alpha is closer to 0 
	public static Values lowpass( Values values,double alpha){

		Values output = new Values();
		double  value = values.get(0); // start with the first input
		output.add(value);

		for (int i =1; i < values.size(); ++i){
			if (!(values.get(i-1) == -100) && !(values.get(i) == -100) ){
				value = output.get(i-1) + (alpha*(values.get(i) - output.get(i-1)));
				output.add(value);
			}
			else if ((values.get(i) == -100) ){
				output.add(-100.0);
			}
			else if ((values.get(i-1) == -100) && !(values.get(i) == -100) ){
				value = values.get(i); // start again
				output.add(value);
			}
		}
		return output;
	}


}
