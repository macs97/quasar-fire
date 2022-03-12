package co.com.quasarfire.domain.enums;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum Satellites {

    KENOBI(1,"KENOBI", List.of(-500f, -200f)),
    SKYWALKER(2, "SKYWALKER", List.of(100f, -100f)),
    SATO(3, "SATO", List.of(500f, 100f));

    private final Integer order;
    private final String name;
    private final List<Float> positions;

    Satellites(Integer order, String name, List<Float> positions) {
        this.order = order;
        this.name = name;
        this.positions =  positions;
    }

    public static Satellites getByName(String name) {
        return Arrays.stream(Satellites.values()).filter(satellites -> satellites.getName().equals(name.toUpperCase())).findFirst().orElse(null);
    }
}
