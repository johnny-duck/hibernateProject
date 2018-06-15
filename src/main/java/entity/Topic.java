package entity;

import javax.persistence.*;

@Entity
public class Thread {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String subject;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    private User author;
}
