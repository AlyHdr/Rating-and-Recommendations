
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;

import java.util.ArrayList;

public class Movie {
    
public String movieName;
public int rating;
public ArrayList<Integer> ratings;
    public Movie(String movieName,int rating)
    {
        this.movieName=movieName;
        this.rating=rating;
        this.ratings=new ArrayList<>();
    }
}
