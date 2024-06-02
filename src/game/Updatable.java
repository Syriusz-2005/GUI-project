package game;

import java.io.IOException;

public interface Updatable {
    void step(float timeDelta) throws IOException;
}
