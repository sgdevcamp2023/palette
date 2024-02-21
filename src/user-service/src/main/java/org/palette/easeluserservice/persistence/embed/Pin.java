package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public record Pin(
        @Column(name = "paint_pin", nullable = false, length = Integer.MAX_VALUE)
        String paintPin,

        @Column(name = "dm_pin", nullable = false, length = Integer.MAX_VALUE)
        String dmPin
) {
    public String insert(Long paintId) {
        List<String> values = new ArrayList<>(Arrays.asList(paintPin.split(",")));
        values.add(String.valueOf(paintId));

        return String.join(",", values);
    }

    public String delete(Long paintId) {
        return Arrays.stream(paintPin.split(","))
                .filter(s -> !s.equals(paintId.toString()))
                .collect(Collectors.joining(","));
    }

}
