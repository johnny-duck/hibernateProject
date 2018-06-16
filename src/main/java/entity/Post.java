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
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Topic.class)
    private Topic topic;

    public Post(String content, User author, Topic topic) {
        this.content = content;
        this.topic = topic;
        this.author = author;
    }

    public Post() {

    }
}
