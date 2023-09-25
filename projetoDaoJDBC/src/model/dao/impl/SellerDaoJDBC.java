package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
  private Connection conn;

  public SellerDaoJDBC(Connection conn) {
    this.conn = conn;
  }
  @Override
  public void insert(Seller obj) {
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conn.prepareStatement(
              "INSERT INTO seller "
                     + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                     + "VALUES "
                     + "(?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS // indica ao banco que irá retornar essa chave em um futuro.
      );

      st.setString(1, obj.getName());
      st.setString(2, obj.getEmail());
      st.setDate(3, new Date(obj.getBirthDate().getTime()));
      st.setDouble(4, obj.getBaseSalary());
      st.setInt(5, obj.getDepartment().getId());

      int rowsAffected = st.executeUpdate();

      if (rowsAffected > 0) { // caso seja maior que zero, confirma que foi inserido um vendedor,
        rs = st.getGeneratedKeys(); // armazenei  chave no rs
        if (rs.next()) {
          int id = rs.getInt(1); // id = chave do objeto que foi inserido(o valor refere-se a primeira coluna da tabela).
          obj.setId(id); // insere a chave no vendedor.
        }
      }
      else {
        throw new DbException("Unexpected error! No rows affected");
      }
    }
    catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
      DB.closeResultSet(rs);
    }
  }

  @Override
  public void update(Seller obj) {
    PreparedStatement st = null;
    try {
      st = conn.prepareStatement(
              "UPDATE seller "
                      +"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                      + "WHERE Id = ?"
      );

      st.setString(1, obj.getName());
      st.setString(2, obj.getEmail());
      st.setDate(3, new Date(obj.getBirthDate().getTime()));
      st.setDouble(4, obj.getBaseSalary());
      st.setInt(5, obj.getDepartment().getId());
      st.setInt(6, obj.getId());

      st.executeUpdate();
    }
    catch (SQLException e) {
      throw new DbException(e.getMessage());
    }

    finally {
      DB.closeStatement(st);
    }
  }

  @Override
  public void deleteById(Integer id) {
    PreparedStatement st = null;
    try {
      st = conn.prepareStatement("DELETE FROM seller  WHERE Id = ?");

      st.setInt(1, id);

      st.executeUpdate();
    }
    catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
    }
  }

  @Override
  public Seller findById(Integer id) {
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
        st = conn.prepareStatement(
                    "select seller.*, department.Name as DepName "
                      + "from seller inner join department "
                      + "on seller.departmentId = department.Id "
                      + "where seller.Id = ?"
      );

      st.setInt(1, id);
      rs = st.executeQuery();

      if (rs.next()) {
        Department dep = instantiateDepartment(rs);
        Seller obj = instantiateSeller(rs, dep);
        return obj;
      }

      return null; //caso não possua um id correspondente o seller retornará null
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
      DB.closeResultSet(rs);
    }
  }

  public Department instantiateDepartment(ResultSet rs) throws SQLException {
    Department dep = new Department();
    dep.setId(rs.getInt("DepartmentId"));
    dep.setName(rs.getString("DepName"));
    return dep;
  }

  public Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
    Seller obj = new Seller();
    obj.setId(rs.getInt("Id"));
    obj.setName(rs.getString("Name"));
    obj.setEmail(rs.getString("Email"));
    obj.setBirthDate(rs.getDate("BirthDate"));
    obj.setBaseSalary(rs.getDouble("BaseSalary"));
    obj.setDepartment(dep); // dessa maneira é feita a associação das classes.
    return obj;
  }
  // por os métodos acima tratarem- se de métodos auxiliares, é melhor propagar a exceção, já que o método principal já está tratando o erro.

  @Override
  public List<Seller> findAll() {
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conn.prepareStatement(
              "SELECT seller.*,department.Name as DepName "
                     + "FROM seller INNER JOIN department "
                     + "ON seller.DepartmentId = department.Id "
                     + "ORDER BY Name"
      );

      rs = st.executeQuery();

      List<Seller> list = new ArrayList<>();
      Map<Integer, Department> map = new HashMap<>();

      while(rs.next()) {
        Department dep = map.get(rs.getInt("DepartmentId"));

        if (dep == null) {
          dep = this.instantiateDepartment(rs);
          map.put(rs.getInt("DepartmentId"), dep);
        }

        Seller obj = this.instantiateSeller(rs, dep);
        list.add(obj);
      }

      return list;
    }
    catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
      DB.closeResultSet(rs);
    }
  }

  @Override
  public List<Seller> findByDepartment(Department department) {
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conn.prepareStatement(
              "SELECT seller.*,department.Name as DepName "
                      + "FROM seller INNER JOIN department "
                      + "ON seller.DepartmentId = department.Id "
                      + "WHERE DepartmentId = ? "
                      + "ORDER BY Name"
      );
      st.setInt(1, department.getId());

      rs = st.executeQuery();

      List<Seller> list = new ArrayList<>();
      Map<Integer, Department> map = new HashMap<>(); // Servirá para armazenar os departamentos já cadastrados.

      while (rs.next()) {
        // a função abaixo irá consultar a hashMap map.
        Department dep = map.get(rs.getInt("DepartmentId")); // irá retornar o objeto encontrado com aquele id.

        if (dep == null) { // se o dep for null, ele irá inserir um novo departament na HashMap map.
          dep = this.instantiateDepartment(rs);
          map.put(rs.getInt("DepartmentId"), dep); // irá inserir o objeto na chave com o valor do id do departamento
        }

        Seller obj = this.instantiateSeller(rs, dep);
        list.add(obj);
        // Dessa forma, o código acima, caso os objetos possuam o mesmo departamento, irá apontar para a mesma locação de memória.
      }

      return list; // essa lista terá todos os objetos.
    }
    catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
    finally {
      DB.closeStatement(st);
      DB.closeResultSet(rs);
    }
  }
}
