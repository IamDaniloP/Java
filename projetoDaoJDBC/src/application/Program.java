package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Program {
  public static void main(String[] args) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SellerDao sellerDao = DaoFactory.createSellerDao();

    System.out.println("|TESTE 1| - Seller findById: ");
    Seller seller = sellerDao.findById(1);
    System.out.println(seller);

    System.out.println();
    System.out.println("|TESTE 2| - Seller findByDepartment: ");
    Department dep = new Department(2, null);
    List<Seller> list = sellerDao.findByDepartment(dep);
    list.forEach(System.out::println);

    System.out.println();
    System.out.println("|TESTE 3| - Seller findAll: ");
    list = sellerDao.findAll();
    list.forEach(System.out::println);

    System.out.println();
    System.out.println("|TESTE 4| - Seller insertion: ");
    sellerDao.insert(new Seller(1, "Danilo", "danilo@gmail.com", new Date(sdf.parse("26/10/2004").getTime()), 3590.78, dep));
    list = sellerDao.findAll();
    list.forEach(System.out::println);


  }
}
