package uz.internal_affairs.common.exception;

public class RecordAlreadyExist extends RuntimeException{
    public RecordAlreadyExist(String message) {
        super(message);
    }
}
