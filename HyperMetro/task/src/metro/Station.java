package metro;

import java.util.List;
import java.util.Objects;

public class Station {
    private int id;
    public String name;
    private String line;
    public List<Transfer> transfer;
    private Station connected;

    public Station(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station(String name) {
        this(0, name);
    }

    public Station(String name, List<Transfer> transfer) {
        this.name = name;
        this.transfer = transfer;
    }

    public void setTransferLine() {
        if (transfer != null && !transfer.isEmpty()) {
            setLine(transfer.get(0).getLine());
        }
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine() {
        return line == null ? "" : line;
    }

    @Override
    public String toString() {
        String connected = this.connected != null ? String.format(" - %s (%s)", getConnected().name, getLine()) : "";
        return name + connected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Station)) {
            return false;
        }

        Station other = (Station) obj;
        return Objects.equals(this.name, other.name);
    }

    public Station getConnected() {
        return connected;
    }

    public void setConnected(Station connected) {
        this.connected = connected;
    }

    private static class Transfer {
        private String line;
        private String station;

        public String getLine() {
            return line;
        }
    }
}
