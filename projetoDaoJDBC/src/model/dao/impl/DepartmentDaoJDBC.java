package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
  private Connection conn;
  public DepartmentDaoJDBC(Connection conn) {
    this.conn = conn;
  }

  @Override
  public void insert(Department obj) {
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conn.prepareStatement(
              "INSERT INTO department "
              + "(Name) "
              + "VALUES"
              + "(?)", Statement.RETURN_GENERATED_KEYS
      );

      st.setString(1, obj.getName());
      int rowsAffected = st.executeUpdate(); // pega o número de linhas afetadas

      if (rowsAffected > 0) { // caso seja maior que 0
        rs = st.getGeneratedKeys(); // armazena o número da key gerada
        if (rs.next()) {
          obj.setId(rs.getInt(1)); // coloca o valor da key no objeto.
        }
      }
      else {
        throw new DbException("Unexpected error! No rows affected.");
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
  public void update(Department obj) {
    PreparedStatement st = null;

    try {
      st = conn.prepareStatement(
              "UPDATE department "
                      +"SET Name = ?"
                      + "WHERE Id = ?"
      );

      st.setString(1, obj.getName());
      st.setInt(2, obj.getId());

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
      st = conn.prepareStatement(
              "DELETE FROM department WHERE Id = ?"
      );

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
  public Department findById(Integer id) {
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conn.prepareStatement(
              "SELECT * FROM department WHERE Id = ?"
      );

      st.setInt(1, id);
      rs = st.executeQuery();

      if (rs.next()) {
        Department obj = new Department();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        return obj;
      }

      return null;
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
  public List<Department> findAll() {
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conn.prepareStatement(
              "SELECT * FROM department"
      );

      rs = st.executeQuery();
      List<Department> list = new ArrayList<>();

      while (rs.next()) {
        Department obj = new Department();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
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
}
