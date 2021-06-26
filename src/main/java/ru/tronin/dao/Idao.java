package ru.tronin.dao;

import ru.tronin.models.Product;

import java.util.List;

public interface Idao <E,I,S> extends AutoCloseable{

   void create(E e);
   E readById(I i);
   E readByName(S s);
   void update (E e);
   void delete(E e);
   void deleteById(I i);
   void deleteByName(S s);
   void clearTable();
   List<E> readAllProducts();
   void createFromList(List<E> eList);
   void saveOrUpdate(E e);
}
