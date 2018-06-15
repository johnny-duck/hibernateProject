package entity;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User author;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Thread.class)
    private Thread thread;

    public Post(String content, User author, Thread thread) {
        this.content = content;
        this.thread = thread;
        this.author = author;
    }

    public Post() {

    }
}
