package uz.internal_affairs.common.exception;

public class RecordNotFountException extends RuntimeException{
    public RecordNotFountException(String name){
        super(name);
    }
}
