/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetData {
      private Connection con;
    private Statement st;
    private ResultSet rs;
    public GetData()
    {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con= DriverManager.getConnection("jdbc:sqlserver://AliHaidar-PC:1433;databaseName=rating;integratedSecurity=true;");
            st=con.createStatement();
        }
        catch(Exception ex)
        {
            System.out.println("Error\t"+ex);
        }
    }
    public ArrayList<User> getUsers()
    {
        ArrayList<User> users=new ArrayList<>();
          try {
              rs=st.executeQuery("select * from users");
              while(rs.next())
              {
                  int userId=rs.getInt(1);
                  String userName=rs.getString(2);
                  users.add(new User(userName,userId));
              }
          } catch (SQLException ex) {
              Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
          }
          return users;
    }
    public void addMovies(User user)
    {
          try {
              rs=st.executeQuery("select movies.movie_name,ratings.rating from movies,ratings where ratings.movie_fk=movies.movie_id and ratings.user_fk="+user.userId);
              while(rs.next())
              {
                  String movieName=rs.getString(1);
                  int rating=rs.getInt(2);
                  user.addMovie(new Movie(movieName, rating));
              }
          } catch (SQLException ex) {
              Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
          }
                            
    }
    public void getRatings(Movie movie)
    {
          try {
              rs=st.executeQuery("select ratings.rating,movies.movie_name from ratings,movies where movies.movie_id=ratings.movie_fk");
              while(rs.next())
              {
                  String movieName=rs.getString(2);
                  if(movieName.equals(movie.movieName))
                  {
                      int rating=rs.getInt(1);
                      movie.ratings.add(rating);
                  }
              }
          } catch (SQLException ex) {
              Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    public ArrayList<Movie> getMovies()
    {
        ArrayList<Movie> movies=new ArrayList<>();
          try {
              rs=st.executeQuery("select Movies.movie_name from movies");
              while(rs.next())
              {
                  movies.add(new Movie(rs.getString(1), 0));
              }
          } catch (SQLException ex) {
              Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
          }
          return movies;
    }
}
