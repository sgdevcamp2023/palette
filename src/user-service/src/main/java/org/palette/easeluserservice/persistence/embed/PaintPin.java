package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Embeddable
public class PaintPin extends Pin {

    @Column(name = "paint_pin", columnDefinition = "TEXT")
    private String value;

    public void main(String[] args) {
        PaintPin userPaintPin = new PaintPin();
        userPaintPin.loadAll(userPaintPin.value);
    }
}
