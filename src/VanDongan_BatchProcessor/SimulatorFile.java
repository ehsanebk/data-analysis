package VanDongan_BatchProcessor;

import java.io.File;
import java.io.PrintStream;

public class SimulatorFile extends Binary {

	public SimulatorFile(File file) {
		super(file);
	}

	private Object[][] fields = { { "loadCount", "int", false }, { "version", "int", false },
			{ "hostFlags", "int", false }, { "igFlags", "int", false }, { "vehicleCount", "int", false },
			{ "exitCode", "int", false }, { "cabData", "int", false }, { "segNo", "int", false },
			{ "databaseType", "int", false }, { "gearSelect", "int", false }, { "ignition", "int", false },
			{ "tcdData", "skip", 20 * 4 }, { "vehFlags", "skip", 16 }, { "unknown", "skip", 12 * 4 },
			{ "elapsedTime", "float", true, 6 }, { "clutchPos", "float", false }, { "shifterXPos", "float", false },
			{ "shifterYPos", "float", false }, { "travelDist", "float", true, 6 }, { "averageMPG", "float", false },
			{ "currentMPG", "float", false }, { "gallonsFuel", "float", false }, { "motionPitch", "float", false },
			{ "motionRoll", "float", false }, { "simTime", "float", true, 6 }, { "accelPed", "float", true, 3 },
			{ "brakePed", "float", true, 3 }, { "steerWheel", "float", true, 8 }, { "engineRPM", "float", false },
			{ "lanePos", "float", true, 6 }, { "followDist", "float", true, 6 }, { "engineTorque", "float", false } };

	private void printHeader(PrintStream out) {
		out.print("frameCount\t");
		for (int i = 0; i < fields.length; i++) {
			Object[] row = fields[i];
			String type = (String) row[1];
			if (type.equals("int") || type.equals("float"))
				if ((boolean) row[2])
					out.print(((String) row[0]) + "\t");
		}
		out.println();
	}

	private void processStruct(int frameCount, PrintStream out) {
		out.print(frameCount + "\t");
		for (int i = 0; i < fields.length; i++) {
			Object[] row = fields[i];
			String type = (String) row[1];
			if (type.equals("int")) {
				int x = readInt();
				if ((boolean) row[2])
					out.print(x + "\t");
			} else if (type.equals("float")) {
				float x = readFloat();
				if ((boolean) row[2])
					out.print((String.format("%." + (int) row[3] + "f", x)) + "\t");
			} else if (type.equals("skip")) {
				skipBytes((int) row[2]);
			}
		}
		out.println();
	}

	public void process(PrintStream out) {
		try {
			printHeader(out);
			int n2 = 0;
			int n1 = 0;
			int n0 = 0;
			int lastFrame = 0;
			while (hasMore()) {
				n2 = n1;
				n1 = n0;
				n0 = readInt();
				if (n0 == 780 && n2 == lastFrame + 1) {
					processStruct(n2, out);
					lastFrame = n2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
