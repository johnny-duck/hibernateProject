package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Thread {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Topic.class)
    private Topic topic;
    @Column(nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User creator;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Topic.class, mappedBy = "thread")
    private List<Topic> topics;


    public Thread(Topic topic, String title, User creator) {
        this.topic = topic;
        this.title = title;
        this.creator = creator;
    }

    public Thread() {

    }
}
