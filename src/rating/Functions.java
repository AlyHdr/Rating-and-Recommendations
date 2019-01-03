/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;

/**
 *
 * @author Ali Haidar
 */
public class Functions {
    public Functions()
    {
        
    }
    public int getManhattanDistance(User user1,User user2)
    {
        int totalDistance=0;
        for(int i=0;i<user1.movies.size();i++)
        {
            if(user1.movies.get(i).rating!=0 && user2.movies.get(i).rating!=0)
            {
                totalDistance+=Math.abs(user1.movies.get(i).rating - user2.movies.get(i).rating);
            }
        }
        return totalDistance;
    }
    public double getEuclidean(User user1,User user2)
    {
        double totalDistance=0;
        for(int i=0;i<user1.movies.size();i++)
        {
            if(user1.movies.get(i).rating!=0 && user2.movies.get(i).rating!=0)
            {
                totalDistance+=Math.pow(user1.movies.get(i).rating - user2.movies.get(i).rating,2);
            }
        }
        return Math.sqrt(totalDistance);
    }
    public double getPearson(User user1,User user2)
    {
        double Xbar=0;
        double Ybar=0;
        int count1=0;
        int count2=0;
        double numerator=0;
        double sumx=0;
        double sumy=0;
        for(int i=0;i<user1.movies.size();i++)
        {
            if(user1.movies.get(i).rating!=0)
            {
                count1++;
                Xbar+=user1.movies.get(i).rating;
            }
        }
        Xbar=Xbar/count1;
        for(int i=0;i<user2.movies.size();i++)
        {
            if(user2.movies.get(i).rating!=0)
            {
                count2++;
                Ybar+=user2.movies.get(i).rating;
            }
        }
        Ybar=Ybar/count2;
        for(int i=0;i<user1.movies.size();i++)
        {
            if(user1.movies.get(i).rating!=0 && user2.movies.get(i).rating!=0)
            {
                numerator+=(user1.movies.get(i).rating-Xbar)*(user2.movies.get(i).rating-Ybar);
                sumx+=Math.pow(user1.movies.get(i).rating-Xbar,2);
                sumy+=Math.pow(user2.movies.get(i).rating-Ybar,2);
            }
        }
        return numerator/(Math.sqrt(sumx)*Math.sqrt(sumy));
    }
    public double getCosineSimilarity(User user1,User user2)
    {
        int numerator=0;
        int sum1=0;
        int sum2=0;
        for(int i=0;i<user1.movies.size();i++)
        {
            numerator+=(user1.movies.get(i).rating)*(user2.movies.get(i).rating);
            sum1+=Math.pow(user1.movies.get(i).rating,2);
            sum2+=Math.pow(user2.movies.get(i).rating, 2);
        }
        return (numerator)/(Math.sqrt(sum1)*Math.sqrt(sum2));
    }
    public double getACS(Movie movie1,Movie movie2)
    {
        double Xbar=0;
        double Ybar=0;
        int count1=0;
        int count2=0;
        double numerator=0;
        double sumx=0;
        double sumy=0;
        for(int i=0;i<movie1.ratings.size();i++)
        {
            if(movie1.ratings.get(i)!=0)
            {
                count1++;
                Xbar+=movie1.ratings.get(i);
            }
        }
        Xbar=Xbar/count1;
        for(int i=0;i<movie2.ratings.size();i++)
        {
            if(movie2.ratings.get(i)!=0)
            {
                count2++;
                Ybar+=movie2.ratings.get(i);
            }
        }
        Ybar=Ybar/count2;
        for(int i=0;i<movie1.ratings.size();i++)
        {
            if(movie1.ratings.get(i)!=0 && movie2.ratings.get(i)!=0)
            {
                numerator+=(movie1.ratings.get(i)-Xbar)*(movie2.ratings.get(i)-Ybar);
                sumx+=Math.pow(movie1.ratings.get(i)-Xbar,2);
                sumy+=Math.pow(movie2.ratings.get(i)-Ybar,2);
            }
        }
        return numerator/(Math.sqrt(sumx)*Math.sqrt(sumy));
    }
    public double getWeightedSlopeOne(Movie movie1,Movie movie2)
    {
        double count=0;
        double sum=0;
        for(int i=0;i<movie1.ratings.size();i++)
        {
            if(movie1.ratings.get(i)!=0 && movie2.ratings.get(i)!=0)
            {
                sum+=(movie1.ratings.get(i)-movie2.ratings.get(i));
                count++;
            }
        }
        return sum/count;
    }
}
