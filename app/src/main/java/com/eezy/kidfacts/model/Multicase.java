package com.eezy.kidfacts.model;

import java.util.ArrayList;
import java.util.List;

public class Multicase {
    public int result;
    public List<Integer> causes;

    public Multicase(int result, List<Integer> causes) {
        this.result = result;
        this.causes = causes;
    }
    public List<Integer> split() {
        List<Integer> l = new ArrayList<>();
        l.add(this.result);
        l.addAll(this.causes);
        return l;
    }
}
