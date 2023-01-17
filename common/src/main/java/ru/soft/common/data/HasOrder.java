package ru.soft.common.data;

public interface HasOrder extends Comparable<HasOrder> {

    int order();

    default int compareTo(HasOrder element) {
        return Integer.compare(this.order(), element.order());
    }
}
