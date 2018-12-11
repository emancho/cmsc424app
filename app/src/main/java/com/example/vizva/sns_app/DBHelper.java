package com.example.vizva.sns_app;

import java.sql.*;
import java.util.ArrayList;

public class DBHelper {

    //This opens a connection to the DB that we have on the Google Cloud
    public Connection connectToDB() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://35.243.132.4","root","KjluNxFdxFxhAEed");
            //here is the database IP, Username, and Password
            return con;
        }catch(Exception e){ System.out.println(e);}
        return null;
    }

    public void closeConnection(Connection con) {
        try{
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //This prints something out, but doesn't return any usable data
    public void displayPosts(Connection con) {
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM project.posts");
            while(rs.next())  {
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6)+"  "+rs.getString(7)+"  "+rs.getString(8));
            }
        }catch(Exception e){ System.out.println(e);}
    }

    //I want to be able to add readers
    public void addReader(Connection con, String email, String name, String password, String lat, String longi) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO project.readers " +
                    "(email, Latitude, Longitude, Name, Password, verified) " +
                    "VALUES " +
                    "(\""+email+"\", \""+lat+"\", \""+longi+"\", \""+name+"\", \""+password+"\", 0);");
        }catch(Exception e){ System.out.println(e);}
    }

    //I want to be able to add publishers
    public void addPublisher(Connection con, String email, String name, String password, String address) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO project.publishers " +
                    "(email, Name, Password, Address, verified) " +
                    "VALUES " +
                    "(\""+email+"\", \""+name+"\", \""+password+"\", \""+address+"\", 0);");
        }catch(Exception e){ System.out.println(e);}
    }

    //I want to be able to delete readers
    public void deleteReader(Connection con, String email) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("DELETE FROM project.readers " +
                    "WHERE email = \""+email+"\";");
        }catch(Exception e){ System.out.println(e);}
    }

    //I want to be able to delete publishers
    public void deletePublisher(Connection con, String email) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("DELETE FROM project.publishers " +
                    "WHERE email = \""+email+"\";");
        }catch(Exception e){ System.out.println(e);}
    }

    //I want to be able to add tags to readers
    //This can add 1 tag to a reader at a time
    public void addReaderTag(Connection con, String email, String tag) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO project.reader_tags " +
                    "(reader, tag) " +
                    "VALUES " +
                    "(\""+email+"\", \""+tag+"\");");
        }catch(Exception e){ System.out.println(e);}
    }

    //I want to be able to delete tags from readers
    //This can delete 1 tag from a reader at a time
    public void deleteReaderTag(Connection con, String email, String tag) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("DELETE FROM project.reader_tags " +
                    "WHERE reader = \""+email+"\" AND tag = \""+tag+"\"");
        }catch(Exception e){ System.out.println(e);}
    }

    //I want to be able to add tags to posts
    //This can add 1 tag to a post at a time
    public void addPostTag(Connection con, String postID, String tag) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO project.post_tags " +
                    "(post, tag) " +
                    "VALUES " +
                    "(\""+postID+"\", \""+tag+"\");");
        }catch(Exception e){ System.out.println(e);}
    }

    //Create a new post
    public void addPost(Connection con, String lat, String longi, String extend, String start, String end, String publisherEmail, String text, String[] tags) {
        try{
            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO project.posts " +
                    "(Latitude, Longitude, extend, start_time, end_time, publisher_email, post_text) " +
                    "VALUES " +
                    "(\""+lat+"\", \""+longi+"\", \""+Double.toString(Double.parseDouble(extend)/69)+"\", \""+start+"\", \""+end+"\", \""+publisherEmail+"\", \""+text+"\");");
            Statement stmt2=con.createStatement();
            ResultSet rs=stmt2.executeQuery("SELECT MAX(post_id) FROM project.posts");
            rs.next();
            int id = rs.getInt(1);
            for(int i=0; i<tags.length; i++){
                addPostTag(con, Integer.toString(id), tags[i]);
            }
        }catch(Exception e){ System.out.println(e);}
    }

    //Publisher can Search posts
    //variables are Connector, Latitude, Longitude, time, and reader email
	/*SELECT post_id, post_text, publisher_email FROM posts
	INNER JOIN post_tags p_t ON post_id = post
	WHERE publisher_email = @currentPublisher AND SQRT(POW(ABS(Latitude - @lat), 2) + POW(ABS(Longitude - @long), 2)) < extend AND start_time < @time AND end_time > @time AND tag = @searchTag
	GROUP BY post_id;
	 */
    public ArrayList<String[]> publisherSearch(Connection con, String lat, String longi, String time, String email, String tag) {
        ArrayList<String[]> posts = new ArrayList<String[]>();
        String query = "SELECT post_id, post_text, publisher_email FROM project.posts" +
                " INNER JOIN project.post_tags p_t ON post_id = post" +
                " WHERE publisher_email = \""+email+"\"";
        if(!lat.isEmpty() || !longi.isEmpty()) {
            query = query + " AND SQRT(POW(ABS(Latitude - "+lat+"), 2) + POW(ABS(Longitude - "+longi+"), 2)) < extend";
        }
        if(!time.isEmpty()) {
            query = query + " AND start_time < "+time+" AND end_time > "+ time;
        }
        if(!tag.isEmpty()) {
            query = query + " AND tag = \""+tag+"\"";
        }
        query = query + " GROUP BY post_id;";
        //System.out.println(query);
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next()) {
                posts.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
            }

        }catch(Exception e){ System.out.println(e);}

        return posts;
    }

    //Get Reader Stories
    //variables are Connector, Latitude, Longitude, time, and reader email
    public ArrayList<String[]> getStories(Connection con, String lat, String longi, String time, String email) {
        ArrayList<String[]> posts = new ArrayList<String[]>();
        if(time.isEmpty()) {
            time = "NOW()";
        }
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT post_id, post_text, publisher_email FROM project.posts " +
                    " INNER JOIN project.post_tags p_t ON post = post_id" +
                    " INNER JOIN project.reader_tags r_t ON p_t.tag = r_t.tag" +
                    " WHERE SQRT(POW(ABS(Latitude - "+lat+"), 2) + POW(ABS(Longitude - "+longi+"), 2)) < extend AND start_time < "+time+" AND end_time > "+time+" AND reader = \""+email+"\"" +
                    " GROUP BY post_id;");
            while(rs.next()) {
                posts.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
            }

        }catch(Exception e){ System.out.println(e);}

        return posts;
    }

    //Get all unique tags
    //variables are just the Connector
    public ArrayList<String> getTags(Connection con) {
        ArrayList<String> posts = new ArrayList<String>();
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT DISTINCT tag FROM project.post_tags;");
            while(rs.next()) {
                posts.add(new String(rs.getString(1)));
            }

        }catch(Exception e){ System.out.println(e);}

        return posts;
    }

}