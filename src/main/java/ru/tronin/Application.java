package ru.tronin;

import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.tronin.dao.Idao;
import ru.tronin.dao.ProductDao;
import ru.tronin.models.Product;
import ru.tronin.services.Products;
import ru.tronin.services.DDLHandler;

import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {

        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();

        try(Idao<Product, Long, String> productDao =  new ProductDao(sessionFactory)){

            System.out.println("\n=========================PREPARING===========================");
            prepareDB(productDao, sessionFactory);
            showAllEntities(productDao);

            System.out.println("\n=========================READ BY NAME========================");
            System.out.println(productDao.readByName("Tomato"));

            System.out.println("\n=========================READ BY ID==========================");
            System.out.println(productDao.readById(4L));

            System.out.println("\n=========================ADD ENTITY==========================");
            productDao.create(new Product("Thistle", 16.92f));
            showAllEntities(productDao);

            System.out.println("\n========================UPDATE BY NAME=======================");
            updateEntityByName(productDao, "Apple", 123555.01f);

            System.out.println("\n========================UPDATE BY ID=========================");
            updateEntityById(productDao,2L, "Potato", 2.74f);

            System.out.println("\n========================DELETE BY NAME=======================");
            deleteEntityByName(productDao, "Apple");

            System.out.println("\n========================DELETE BY ID=========================");
            deleteById(productDao, 3L);

            System.out.println("\n========================SAVE OR UPDATE=======================");
            saveOrUpdate(productDao, "Apple", "Grapes");
        }
    }

    private static void saveOrUpdate(Idao<Product, Long, String> productDao, String creatingName, String updatingName) {
        //Save object
        productDao.saveOrUpdate(new Product(creatingName, 13.42f));
        Product product = productDao.readByName(creatingName);
        System.out.println(product);
        //Update project
        productDao.saveOrUpdate(new Product(product.getId(), updatingName, 99.99f));
        System.out.println(productDao.readByName(updatingName));
    }

    private static void deleteById(Idao<Product, Long, String> productDao, Long id) {
        showAllEntities(productDao);
        System.out.println();
        productDao.deleteById(id);
        showAllEntities(productDao);
    }

    private static void deleteEntityByName(Idao<Product, Long, String> productDao, String name) {
        showAllEntities(productDao);
        System.out.println();
        productDao.deleteByName(name);
        showAllEntities(productDao);
    }

    public static void prepareDB(Idao<Product, Long, String> productDao, SessionFactory sessionFactory){
        new DDLHandler(sessionFactory).createProductsTable();
        Products products = new Products();
        productDao.createFromList(products.getProductList());
    }

    public static void updateEntityByName(Idao<Product, Long, String> productDao, String name, Float cost){
        System.out.println(productDao.readByName(name));
        Product product = productDao.readByName(name);
        product.setCost(cost);
        productDao.update(product);
        System.out.println(productDao.readByName(name));
    }

    public static void showAllEntities(Idao<Product, Long, String> productDao){
        List<Product> products = productDao.readAllProducts();
        products.forEach(System.out::println);
    }

    public static void updateEntityById(Idao<Product, Long, String> productDao, Long id, String title, Float cost){
        System.out.println(productDao.readById(id));
        Product product = productDao.readById(id);
        product.setCost(cost);
        product.setTitle(title);
        productDao.update(product);
        System.out.println(productDao.readById(id));
    }

}
