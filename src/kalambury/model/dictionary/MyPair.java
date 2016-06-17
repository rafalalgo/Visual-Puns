package kalambury.model.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafalbyczek on 31.05.16.
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