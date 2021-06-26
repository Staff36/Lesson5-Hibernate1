package ru.tronin.services;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@AllArgsConstructor
public class DDLHandler {

    private SessionFactory factory;

    public void createProductsTable(){
        try(Session session = factory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery("drop table if exists testhibernate.products").executeUpdate();
            session.createSQLQuery("drop schema if exists testhibernate").executeUpdate();
            session.createSQLQuery("create schema testhibernate").executeUpdate();
            session.createSQLQuery("create table testhibernate.Products(id serial primary key ,title varchar, cost float)").executeUpdate();
            session.getTransaction().commit();
        }
    }

}
