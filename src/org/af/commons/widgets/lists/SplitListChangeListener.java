package org.af.commons.widgets.lists;

import java.util.List;

public interface SplitListChangeListener<T> {
    public void modelStateChanged(List<T> left, List<T> right);
}
