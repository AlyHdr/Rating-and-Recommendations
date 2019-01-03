
package rating;

import java.util.ArrayList;

public class User {
    public String userName;
    public int userId;
    public ArrayList<Movie> movies;
    public User(String Name,int userId)
    {
        userName=Name;
        this.userId=userId;
        movies=new ArrayList<>();
    }
    public void addMovie(Movie movie)
    {
        movies.add(movie);
    }
    public ArrayList<Movie> getMovies()
    {
        return this.movies;
    }
}
