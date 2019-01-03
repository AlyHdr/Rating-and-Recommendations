
package rating;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
public class Interface {
    DecimalFormat df=new DecimalFormat("#.##");
    public ArrayList<User> users;
    public ArrayList<Movie> movies;
    public Functions functions;
    JFrame frame;
    JButton calculate;
    String[] names={"Manhattan Distance","Euclidean Distance","Pearson","Cosine Similarity","K-Collaborate","ACS","Weighted Slope One"};
    Color[] colors={Color.RED,Color.BLACK,Color.GREEN,Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.ORANGE};
    JButton[] btns=new JButton[7];
    
    ArrayList listOfnames;
    JComboBox listOfNames;
    JComboBox listofMovies;
    JPanel panel;
    
    public Interface()
    {
        functions=new Functions();
        display();
    }
    public void display()
    {
        fillUsers();
        fillMovies();
        frame=new JFrame("Rating");
        frame.setLayout(new BorderLayout(10,10));
        panel=new JPanel(new GridLayout(7,1,10,10));
        for(int i=0;i<7;i++)
        {
            init(i);
        }
        String[] usersNames=getUsersNames(users);
        
        listOfNames=new JComboBox(usersNames);
        
        frame.add(listOfNames,BorderLayout.NORTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    public void init(int i)
    {
        
            btns[i]=new JButton(names[i]);
            btns[i].setForeground(colors[i]);
            btns[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    switch(names[i])
                    {
                        case "Manhattan Distance":
                        {
                    String selectedName=listOfNames.getSelectedItem().toString();
                    SortedMap<Integer,Integer> map=getManhattanDistances(selectedName);
                    String str="";
                    String recomend=showRecomendation(users.get(listOfNames.getSelectedIndex()),users.get(map.get(map.firstKey())));
                    for(int i=0;i<3;i++)
                    {
                        int distance1=map.firstKey();
                        int pos1=map.get(distance1);
                        map.remove(distance1);
                        str+="User "+(i+1)+": "+users.get(pos1).userName+" with distance: "+distance1+"\n";
                    }
                    str+="\nRecomended movies:\n"+recomend;
                    JOptionPane.showMessageDialog(null, str);
                    break;
                        }
                        case "Euclidean Distance":
                        {
                            SortedMap<Double,Integer> map=getEuclideanDistances();
                            String str="";
                            String recomend=showRecomendation(users.get(listOfNames.getSelectedIndex()),users.get(map.get(map.firstKey())));
                            for(int i=0;i<3;i++)
                            {
                                double distance1=map.firstKey();
                                String dis=df.format(distance1);
                                int pos1=map.get(distance1);
                                map.remove(distance1);
                                 str+="User "+(i+1)+": "+users.get(pos1).userName+" with distance: "+dis+"\n";
                            }
                            str+="\nRecomended movies:\n"+recomend;
                            JOptionPane.showMessageDialog(null, str);
                            break;
                        }
                        case "Pearson":
                        {
                            SortedMap<Double,Integer> map=getPearson();
                            String str="";
                            String recomend=showRecomendation(users.get(listOfNames.getSelectedIndex()),users.get(map.get(map.lastKey())));
                            for(int i=0;i<3;i++)
                            {
                                double distance1=map.lastKey();
                                String dis=df.format(distance1);
                                int pos1=map.get(distance1);
                                map.remove(distance1);
                                 str+="User "+(i+1)+": "+users.get(pos1).userName+" with ratio: "+dis+"\n";
                            }
                            str+="\nRecomended movies:\n"+recomend;
                            JOptionPane.showMessageDialog(null, str);
                            break;
                        }
                        case "Cosine Similarity":
                        {
                            SortedMap<Double,Integer> map=getCosineSimilaraties();
                            String str="";
                            String recomend=showRecomendation(users.get(listOfNames.getSelectedIndex()),users.get(map.get(map.lastKey())));
                            for(int i=0;i<3;i++)
                            {
                                double distance1=map.lastKey();
                                String dis=df.format(distance1);
                                int pos1=map.get(distance1);
                                map.remove(distance1);
                                 str+="User "+(i+1)+": "+users.get(pos1).userName+" with ratio: "+dis+"\n";
                            }
                            str+="\nRecomended movies:\n"+recomend;
                            JOptionPane.showMessageDialog(null, str);
                            break;
                        }
                        case "K-Collaborate":
                        {
                            String k=JOptionPane.showInputDialog("enter K:");
                            User selectedUser=users.get(listOfNames.getSelectedIndex());
                            String str="";
                            String usersNames="";
                            SortedMap<Double,Integer> map=getPearson();
                            for(int i=0;i<Integer.parseInt(k);i++)
                            {
                                usersNames+="User "+(i+1)+" : "+users.get(map.get(map.lastKey())).userName+"\n";
                                map.remove(map.lastKey());
                            }
                            str+=usersNames+"\n";
                            
                            for(int i=0;i<selectedUser.movies.size();i++)
                            {
                                if(selectedUser.movies.get(i).rating==0)
                                {
                                    double res=getK(Integer.parseInt(k), i);
                                    String r=df.format(res);
                                    if(res!=0)
                                        str+="Movie Name: "+selectedUser.movies.get(i).movieName+" rate: "+r+"\n";
                                }
                            }
                            JOptionPane.showMessageDialog(null, str);
                            break;
                        }
                        case "ACS":
                        {
                            String str=showAccordingToMovies();
                            JOptionPane.showMessageDialog(null, str);
                           break;
                        }
                        case "Weighted Slope One":
                        {
                            String res=showSlop();
                            JOptionPane.showMessageDialog(null, res);
                        }
                    }
                }
            });
            panel.add(btns[i]);
                
    }
    public void fillUsers()
    {
        GetData get=new GetData();
        this.users=get.getUsers();
        for(int i=0;i<users.size();i++)
        {
            get.addMovies(users.get(i));
        }
    }
    public void fillMovies()
    {
        GetData get=new GetData();
        this.movies=get.getMovies();
        for(int i=0;i<movies.size();i++)
        {
            get.getRatings(movies.get(i));
        }
    }
    public String[] getUsersNames(ArrayList<User> users)
    {
        String[] usersNames=new String[users.size()];
        for(int i=0;i<users.size();i++)
        {
            usersNames[i]=users.get(i).userName;
        }
        return usersNames;
    }
    public String[] getMoviesNames(ArrayList<Movie> movies)
    {
        String[] moviesNames=new String[movies.size()];
        for(int i=0;i<movies.size();i++)
        {
            moviesNames[i]=movies.get(i).movieName;
        }
        return moviesNames;
    }
    public TreeMap<Integer,Integer> getManhattanDistances(String selectedName)
    {
        //ArrayList<Integer> distances=new ArrayList<>();
        SortedMap<Integer,Integer> distances=new TreeMap<>();
        User selectedUser=users.get(listOfNames.getSelectedIndex());
        for(int i=0;i<users.size();i++)
        {
            if(i!=listOfNames.getSelectedIndex())
                distances.put(functions.getManhattanDistance(selectedUser, users.get(i)),i);
        }
        return new TreeMap<>(distances);
    }
    public String showRecomendation(User user1,User user2)
    {
        String recomendation="";
        for(int i=0;i<users.size();i++)
        {
            if(user1.movies.get(i).rating==0 && user2.movies.get(i).rating!=0)
            {
                recomendation+=user2.movies.get(i).movieName+" : "+user2.movies.get(i).rating+"\n";
            }
        }
        return recomendation;
    }
    public TreeMap<Double,Integer> getEuclideanDistances()
    {
        SortedMap<Double,Integer> distances=new TreeMap<>();
        User selectedUser=users.get(listOfNames.getSelectedIndex());
        for(int i=0;i<users.size();i++)
        {
            if(i!=listOfNames.getSelectedIndex())
                distances.put(functions.getEuclidean(selectedUser, users.get(i)),i);
        }
        return new TreeMap<>(distances);
    }
    
    public TreeMap<Double,Integer> getPearson()
    {
        SortedMap<Double,Integer> distances=new TreeMap<>();
        User selectedUser=users.get(listOfNames.getSelectedIndex());
        for(int i=0;i<users.size();i++)
        {
            if(i!=listOfNames.getSelectedIndex())
                distances.put(functions.getPearson(selectedUser, users.get(i)),i);
        }
        return new TreeMap<>(distances);
    }
    public TreeMap<Double,Integer> getCosineSimilaraties()
    {
        SortedMap<Double,Integer> distances=new TreeMap<>();
        User selectedUser=users.get(listOfNames.getSelectedIndex());
        for(int i=0;i<users.size();i++)
        {
            if(i!=listOfNames.getSelectedIndex())
                distances.put(functions.getCosineSimilarity(selectedUser, users.get(i)),i);
        }
        return new TreeMap<>(distances);
    }
    public double getK(int k,int movieIndex)
    {
        SortedMap<Double,Integer> map=getPearson();
        double total=0;
        double[] pearsons=new double[k];
        int[] ratings=new int[k];
        double result=0;
        for(int i=0;i<k;i++)
        {
            total+=map.lastKey();
            pearsons[i]=map.lastKey();
            ratings[i]=users.get(map.get(map.lastKey())).movies.get(movieIndex).rating;
            map.remove(map.lastKey());
        }
        for(int i=0;i<pearsons.length;i++)
        {
            result+=ratings[i]*((pearsons[i]/total));
        }
        return result;
    }
    public TreeMap<Double,Integer> getACS(Movie selectedMovie,int index)
    {
        SortedMap<Double,Integer> distances=new TreeMap<>();
        for(int i=0;i<movies.size();i++)
        {
            if(i!=index)
                distances.put(functions.getACS(selectedMovie, movies.get(i)),i);
        }
        return new TreeMap<>(distances);
    }
    public TreeMap<Double,Integer> getWeightedSlope(Movie selectedMovie,int index)
    {
        SortedMap<Double,Integer> distances=new TreeMap<>();
        for(int i=0;i<movies.size();i++)
        {
            if(i!=index)
                distances.put(functions.getWeightedSlopeOne(selectedMovie, movies.get(i)),i);
        }
        return new TreeMap<>(distances);
    }
    
    public String showAccordingToMovies()
    {
        User selectedUser=users.get(listOfNames.getSelectedIndex());
        String str="";
        str+="Recommended movies to watch for "+selectedUser.userName+" :\n\n";
        for(int i=0;i<selectedUser.movies.size();i++)
        {
            if(selectedUser.movies.get(i).rating==0)
            {
                
                SortedMap<Double,Integer> map=getACS(movies.get(i),i);
                for(int j=0;j<map.size();j++)
                {
                    if(selectedUser.movies.get(map.get(map.lastKey())).rating!=0)
                    {
                        String res=df.format(map.lastKey());
                        str+="ACS="+res+" , "+selectedUser.movies.get(map.get(map.lastKey())).movieName+"\nMovie : "+selectedUser.movies.get(i).movieName+ " suggested rating : "+selectedUser.movies.get(map.get(map.lastKey())).rating+"\n\n";
                        break;
                    }
                    else
                        map.remove(map.lastKey());
                }
            }
        }
        return str;
    }
    public String showSlop()
    {
        User selectedUser=users.get(listOfNames.getSelectedIndex());
        String str="";
        str+="Recommended movies to watch for "+selectedUser.userName+" :\n\n";
        for(int i=0;i<selectedUser.movies.size();i++)
        {
            if(selectedUser.movies.get(i).rating==0)
            {
                
                SortedMap<Double,Integer> map=getWeightedSlope(movies.get(i),i);
                for(int j=0;j<map.size();j++)
                {
                    if(selectedUser.movies.get(map.get(map.lastKey())).rating!=0)
                    {
                        String res=df.format(map.lastKey());
                        str+="slope="+res+" , "+selectedUser.movies.get(map.get(map.lastKey())).movieName+"\nMovie : "+selectedUser.movies.get(i).movieName+ " suggested rating : "+selectedUser.movies.get(map.get(map.lastKey())).rating+"\n\n";
                        break;
                    }
                    else
                        map.remove(map.lastKey());
                }
            }
        }
        return str;
    }
}
