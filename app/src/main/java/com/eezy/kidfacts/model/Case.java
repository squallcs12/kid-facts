package com.eezy.kidfacts.model;

import java.util.ArrayList;
import java.util.List;

public class Case {
    public int cause1;
    public int cause2;
    public int result;

    public Case(int cause1, int cause2, int result) {
        this.cause1 = cause1;
        this.cause2 = cause2;
        this.result = result;
    }
    public List<Integer> split() {
        List<Integer> l = new ArrayList<>();
        l.add(cause1);
        l.add(cause2);
        l.add(result);
        return l;
    }
}
