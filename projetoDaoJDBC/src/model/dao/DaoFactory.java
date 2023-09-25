package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

//servirá para instanciar a implementação
//dessa maneira, ao instânciar o SellerDaoJDBC não precisará expor a implementação e sim apenas a factory.
public class DaoFactory {
  public static SellerDao createSellerDao() {
    return new SellerDaoJDBC(DB.getConnection());
  }
  public static DepartmentDao createDepartmentDao() {
    return new DepartmentDaoJDBC(DB.getConnection());
  }
}
