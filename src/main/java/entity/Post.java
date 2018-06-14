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
    User author;

    public Post(String content, User author) {
        this.content = content;
        this.author = author;
    }

    public Post() {

    }
}
