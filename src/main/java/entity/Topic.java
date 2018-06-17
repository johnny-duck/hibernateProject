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

    public Topic(String subject, String description, User author, Thread thread) {
        this.subject = subject;
        this.author = author;
        this.thread = thread;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[ #" + id + ", " + subject + ", " + description
                + ", " + author + thread + " ]";
    }

    public String toStringPosts() {
        return post.toString();
    }
}
