import java.util.ArrayList;
import java.util.List;


class Reader {
    private static class ReaderState {
        private final String rId;
        private long nxt;

        ReaderState(String rId, long nxt) {
            this.rId = rId;
            this.nxt = nxt;
        }
    }

    static class ReaderSnapshot {
        private final String rId;
        private final long nxt;

        ReaderSnapshot(String rId, long nxt) {
            this.rId = rId;
            this.nxt = nxt;
        }

        public String get_rID() {
            return rId;
        }

        public long getNS() {
            return nxt;
        }
    }

    private final List<ReaderState> readers = new ArrayList<>();

    public void regR(String rId, long start) {

        if (RIndex(rId) != -1) {
            return;
        }

        readers.add(new ReaderState(rId, start));
    }

    public void unregR(String rId) {
        int i = RIndex(rId);
        if (i != -1) {
            readers.remove(i);
        }
    }

    public boolean isReg(String rId) {
        return RIndex(rId) != -1;
    }

    public long getNS(String rId) {
        int i = RIndex(rId);
        return readers.get(i).nxt;
    }

    public void setNS(String rId, long nxt) {
        int i = RIndex(rId);
        readers.get(i).nxt = nxt;
    }

    public List<ReaderSnapshot> snapshotReaders() {
        List<ReaderSnapshot> snap = new ArrayList<>();
        for (ReaderState s : readers) {
            snap.add(new ReaderSnapshot(s.rId, s.nxt));
        }
        return snap;
    }

    private int RIndex(String rId) {
        for (int i = 0; i < readers.size(); i++) {
            if (readers.get(i).rId.equals(rId)) {
                return i;
            }
        }
        return -1;
    }
}