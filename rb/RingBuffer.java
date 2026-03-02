import java.util.Arrays;
import java.util.List;

public class RingBuffer {
    private final int[] buffer;
    private final int capacity;

    private int next_w;
    private long total_w;

    private final Reader read;
    private final Writer write;

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new int[capacity];
        this.next_w = 0;
        this.total_w = 0L;

        this.read = new Reader();
        this.write = new Writer();
    }

    private long Oldest() {
        long old = total_w - capacity;
        return Math.max(0L, old);
    }

    private long Newest() {
        return total_w;
    }

    private int readSequence(long seq) {
        int slot = (int) (seq % capacity);
        return buffer[slot];
    }

    private void writeSeq(int v) {
        buffer[next_w] = v;

        next_w++;
        if (next_w == capacity) {
            next_w = 0;
        }

        total_w++;
    }

    private int[] SnapshotBuffer() {
        return Arrays.copyOf(buffer, buffer.length);
    }

    public synchronized void regR(String rID) {
        long old = Oldest();
        read.regR(rID, old);
    }

    public synchronized void unregR(String rID) {
        read.unregR(rID);
    }

    public synchronized boolean isReg(String rID) {
        return read.isReg(rID);
    }

    public synchronized void write(int v) {
        write.Current();
        writeSeq(v);
    }

    public synchronized Integer read(String rID) {
        long nextSeq = read.getNS(rID);

        long oldest = Oldest();
        long newest = Newest();

        if (nextSeq < oldest) {
            long missed = oldest - nextSeq;
            System.out.println(
                "Overwritten. " + missed + " items missed. " + 
                "Skipping from sequence " + nextSeq + " to sequence " + oldest + "."
            );
            nextSeq = oldest;
        }

        if (nextSeq >= newest) {
            System.out.println("No new message");
            read.setNS(rID, nextSeq);
            return null;
        }

        int value = readSequence(nextSeq);

        read.setNS(rID, nextSeq + 1);

        return value;
    }

    public synchronized void printState() {
        int[] raw = SnapshotBuffer();
        List<Reader.ReaderSnapshot> readers = read.snapshotReaders();

        System.out.println("Buffer: " + Arrays.toString(raw));
        System.out.println("capacity: " + capacity);
        System.out.println("write slot: " + next_w);
        System.out.println("totalWrites: " + total_w);
        System.out.println("writerId: " + write.get_wId());

        long oldest = Oldest();
        long newest = Newest();
        System.out.println("Range: [" + oldest + ", " + newest + ")");

        System.out.println("Reader positions:");
        if (readers.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Reader.ReaderSnapshot r : readers) {
                System.out.println("  " + r.get_rID() + " - " + r.getNS());
            }
        }
        System.out.println("-----------------------------------");
    }

    public int getCapacity() {
        return capacity;
    }

    public synchronized long get_wTotal() {
        return total_w;
    }

    public synchronized int get_wIndex() {
        return next_w;
    }

    public synchronized Long get_wId() {
        return write.get_wId();
    }

    public synchronized Long get_RPos(String readerId) {
        if (!read.isReg(readerId)) {
            return null;
        }
        return read.getNS(readerId);
    }
}