package kalambury.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał Stobierski on 2016-06-14.
 */

class MyPair {
    public List<String> hints = new ArrayList<>();
    int it;

    MyPair(List<String> hList) {
        for (String S : hList) {
            hints.add(S);
        }
        it = 0;
    }

    String get() {
        return hints.get((it++) % hints.size());
    }
}