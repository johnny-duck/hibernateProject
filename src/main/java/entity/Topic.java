package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Topic {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User author;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Thread.class)
    private Thread thread;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Post.class, mappedBy = "topic")
    private List<Post> post;

    public Topic() {
    }

    public Topic(String subject, User author, Thread thread) {
        this.subject = subject;
        this.author = author;
        this.thread = thread;
    }
}
