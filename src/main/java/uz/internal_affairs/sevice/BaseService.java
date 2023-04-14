package uz.internal_affairs.sevice;

public interface BaseService<T,R> {
    R add(T t);
    boolean delete(Long id);
    R update(Long id,T t);
}
