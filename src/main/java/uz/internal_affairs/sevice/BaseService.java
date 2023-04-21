package uz.internal_affairs.sevice;

import java.text.ParseException;

public interface BaseService<T,R> {
    R add(T t) throws ParseException;
    boolean delete(Long id);
    R update(Long id,T t);
}
