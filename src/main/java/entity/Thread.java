package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Thread {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User creator;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Topic.class, mappedBy = "thread")
    private List<Topic> topics;


    public Thread(String title, User creator) {
        this.title = title;
        this.creator = creator;
    }

    public Thread() {

    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[ #" + id + ", " + title + ", " + creator +" ]";
    }

    public String toStringTopics() {
        return topics.toString();
    }

}
