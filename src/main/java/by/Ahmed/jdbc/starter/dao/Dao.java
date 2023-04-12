package by.Ahmed.jdbc.starter.dao;

import by.Ahmed.jdbc.starter.dto.TicketFilter;
import by.Ahmed.jdbc.starter.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    public boolean update(E e);

    public Optional<E> findById(K id);

    public List<E> findAll();

    public boolean delete(K id);

    public E save(E e);
}
