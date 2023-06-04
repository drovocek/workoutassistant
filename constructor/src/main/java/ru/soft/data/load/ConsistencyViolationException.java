package ru.soft.data.load;

public class ConsistencyViolationException extends RuntimeException {

    public ConsistencyViolationException(String message) {
        super(message);
    }
}
