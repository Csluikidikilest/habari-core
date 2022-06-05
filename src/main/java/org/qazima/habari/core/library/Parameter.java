package org.qazima.habari.core.library;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Parameter {
    private final List<Configuration> configurations = new ArrayList<>();
    public List<Configuration> configurations() { return configurations; }
}
