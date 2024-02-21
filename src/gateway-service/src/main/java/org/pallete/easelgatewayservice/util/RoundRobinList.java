package org.pallete.easelgatewayservice.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinList<E> {

    private final List<E> data;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public RoundRobinList(List<E> instanceInfos) {
        this.data = instanceInfos;
    }

    public E next() {
        if (data.isEmpty()) {
            return null;
        }

        int index = currentIndex.getAndIncrement() % data.size();
        return data.get(index);
    }

    public List<E> getData() {
        return data;
    }
}
