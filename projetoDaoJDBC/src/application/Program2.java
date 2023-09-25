package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
  public static void main(String[] args) {
    DepartmentDao department = DaoFactory.createDepartmentDao();
    Department obj = new Department(6,"Carro");

    System.out.println("TEST 1 --- FindById");
    System.out.println();
    System.out.println(department.findById(2));
    System.out.println();

    System.out.println("TEST 2 --- FindByAll");
    System.out.println();
    System.out.println(department.findAll());
    System.out.println();

    System.out.println("TEST 3 --- Insert");
    System.out.println();
    department.insert(obj);
    System.out.println();

    System.out.println("TEST 4 --- Update");
    System.out.println();
    obj.setName("Cleitinh232323o");
    obj.setId(6);
    department.update(obj);
    System.out.println();

    System.out.println("TEST 5 --- Delete");
    System.out.println();
    department.deleteById(5);
    System.out.println();
  }
}
