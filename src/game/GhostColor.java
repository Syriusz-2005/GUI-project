package game;

public enum GhostColor {
    ORANGE() {
        @Override
        public String getColor() {
            return "orange";
        }
    },
    RED() {
        @Override
        public String getColor() {
            return "red";
        }
    },
    WHITE() {
        @Override
        public String getColor() {
            return "white";
        }
    };

    GhostColor() {}

    public abstract String getColor();
}