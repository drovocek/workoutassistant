package ru.soft.common.data;

public interface HasOrder extends Comparable<HasOrder> {

    int getOrder();

    default int compareTo(HasOrder element) {
        return Integer.compare(this.getOrder(), element.getOrder());
    }
}
