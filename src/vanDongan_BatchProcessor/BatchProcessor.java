package vanDongan_BatchProcessor;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class BatchProcessor {
	private Path inBase;
	private Path outBase;

	public BatchProcessor(Path inBase, Path outBase) {
		this.inBase = inBase;
		this.outBase = outBase;
	}

	public void process(Path dir) {
		try {
			for (Path inPath : Files.newDirectoryStream(dir)) {
				if (Files.isDirectory(inPath)) {
					process(inPath);
				} else if (inPath.toString().toLowerCase().endsWith(".rec")) {
					Path relative = inBase.relativize(inPath);
					Path outPath = outBase.resolve(relative).resolveSibling(inPath.getFileName() + ".txt");
					if (!outPath.toFile().exists()) {
						System.out.println("Generating " + outPath);
						SimulatorFile in = new SimulatorFile(inPath.toFile());
						outPath.getParent().toFile().mkdirs();
						PrintStream out = new PrintStream(outPath.toFile());
						in.process(out);
					} else {
						System.out.println("Skipping " + outPath);
					}
				} else if (inPath.toString().toLowerCase().endsWith(".evt")) {
					Path relative = inBase.relativize(inPath);
					Path outPath = outBase.resolve(relative);
					if (!outPath.toFile().exists()) {
						System.out.println("Copying " + outPath);
						Files.copy(inPath, outPath, StandardCopyOption.REPLACE_EXISTING);
					} else {
						System.out.println("Skipping " + outPath);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void process() {
		process(inBase);
	}

	public static void main(String[] args) {
		String in = "/Volumes/Dongan 2(2)";
		String out = "/Users/dds26/Desktop/tmp";
		BatchProcessor bp = new BatchProcessor(Paths.get(in), Paths.get(out));
		bp.process();
	}
}
