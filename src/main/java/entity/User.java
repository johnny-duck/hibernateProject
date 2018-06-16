package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Post> myPosts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Topic> myTopics;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Thread> myThreads;
    @Column(nullable = false)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {


    }

    public void setNameToUpperCase() {
        this.username = this.username.toUpperCase();
    }
}
