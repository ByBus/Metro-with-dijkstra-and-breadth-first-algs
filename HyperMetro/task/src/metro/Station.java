package metro;

import java.util.Objects;

public class Station {
    private int id;
    private final String name;
    private String line;

    public Station(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Station(String name) {
        this(0, name);
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return name;
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
}
