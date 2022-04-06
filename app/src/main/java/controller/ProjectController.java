/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author User
 */
public class ProjectController {
    
    public void save( Project project){
        String sql = "INSERT INTO projects(name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3,new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date( project.getUpdatedAt().getTime()));
            statement.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível salvar" +e.getMessage());
        } finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public void update(Project project){
        String sql = "UPDATE projects SET name = ?,"+ "description = ?,"+ "createdAt = ?,"+ "updatedAt = ? WHERE id=?";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3,new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date( project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
            
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível atualizar o projeto."+ e.getMessage());
        } finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void removeById(int projectID) {
        String sql = "DELETE FROM projects WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectID);
            statement.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possivel deletar o projeto" + e.getMessage());
            
        } finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
   
  public List <Project> getAll(){
      String sql = "SELECT * FROM projects";
      List<Project> projects = new ArrayList<>();
      
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;
      
      
      try {
          connection = ConnectionFactory.getConnection();
          statement = connection.prepareStatement(sql);
          
          resultSet = statement.executeQuery();
          
          while(resultSet.next()){
              Project project = new Project();
              
               project.setId(resultSet.getInt("id"));
               project.setName(resultSet.getString("name"));
               project.setDescription(resultSet.getString("description"));
               project.setCreatedAt(resultSet.getDate("createdAt"));
               project.setUpdatedAt(resultSet.getDate("updatedAt"));
               
               projects.add(project);
              
          }
      } catch (SQLException e) {
          throw new RuntimeException("Erro ao buscar os projetos" + e.getMessage());
      }finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
            }
      
      return projects;
  }
            
            
            
}
