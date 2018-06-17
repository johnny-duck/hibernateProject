package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
@NamedQueries({
        @NamedQuery(
                name = "get_user_by_username",
                query = "from User where username = :user_name"
        )
})
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

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return password.equals(user.password) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "[ #" + id + ", " + username + " ]";
    }
}
