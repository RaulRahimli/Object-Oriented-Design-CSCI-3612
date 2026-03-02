class Writer {
    private Long wId;

    public void Current() {
        long cId = Thread.currentThread().getId();

        if (wId == null) {
            wId = cId;
            return;
        }

        if (!wId.equals(cId)) {
            throw new IllegalStateException(
                "One writer allowed. Current id = " + wId + ", thread " + cId + " tried to write."
            );
        }
    }

    public Long get_wId() {
        return wId;
    }
}