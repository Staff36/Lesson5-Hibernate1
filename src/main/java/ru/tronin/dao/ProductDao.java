package ru.tronin.dao;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.tronin.models.Product;

import javax.persistence.Query;
import java.util.List;

@AllArgsConstructor
public class ProductDao implements Idao<Product, Long, String> {

    private SessionFactory factory;


    @Override
    public void close()  {
            factory.close();
    }

    @Override
    public void create(Product product) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }
    }

    @Override
    public Product readById(Long id) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            Product productFromDB = session.get(Product.class ,id);
            session.getTransaction().commit();
            return productFromDB;
        }
    }

    @Override
    public Product readByName(String name) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("from Product where title =:name ");
            query.setParameter("name", name);
            Product productFromDB = (Product) query.getSingleResult();
            session.getTransaction().commit();
            return productFromDB;
        }
    }

    @Override
    public void update(Product product) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            session.update(product);
            session.getTransaction().commit();
        }
    }


    @Override
    public void delete(Product product) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(Long id) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("delete from Product where id =:id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        }
//        Можно написать так, но так получается 2 обращения к базе данных, мне кажется- это не очень хорошо
//        Product product = readById(id);
//        delete(product);
    }

    @Override
    public void deleteByName(String title) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("delete from Product where title =:title");
            query.setParameter("title", title);
            query.executeUpdate();
            session.getTransaction().commit();
        }
//        Можно написать так, но так получается 2 обращения к базе данных, мне кажется- это не очень хорошо
//        Product product = readByName(title);
//        delete(product);
    }

    @Override
    public void createFromList(List<Product> productList){
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            productList.forEach(session::saveOrUpdate);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Product> readAllProducts(){
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            List<Product> products= session.createQuery("from Product").list();
            session.getTransaction().commit();
            return products;
        }
    }

    @Override
    public void clearTable(){
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("delete from Product").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveOrUpdate(Product product) {
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }
    }
}
