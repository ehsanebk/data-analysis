package VanDongan_BatchProcessor;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Binary {
	private RandomAccessFile raf;
	private FileChannel channel;
	private ByteBuffer bb;

	public Binary(File file) {
		try {
			raf = new RandomAccessFile(file, "r");
			channel = raf.getChannel();
			bb = ByteBuffer.allocate((int) channel.size());
			bb.order(ByteOrder.LITTLE_ENDIAN);
			channel.read(bb);
			bb.flip();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasMore() {
		return bb.hasRemaining();
	}

	public int readChar() {
		return bb.get() & 0xff;
	}

	public int readShort() {
		return bb.getShort();
	}

	public int readUnsignedShort() {
		return readShort() & 0xffff;
	}

	public int readInt() {
		return bb.getInt();
	}

	public long readUnsignedInt() {
		return readInt() & 0xffffffffL;
	}

	public float readFloat() {
		return bb.getFloat();
	}

	public String skipBytes(int n) {
		for (int i = 0; i < n; i++)
			bb.get();
		return "[" + n + " bytes]";
	}
}
