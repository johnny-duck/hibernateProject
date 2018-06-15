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
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User author;

    public Topic() {
    }

    public Topic(String subject, User author) {
        this.subject = subject;
        this.author = author;
    }
}
