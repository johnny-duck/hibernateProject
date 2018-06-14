import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Student()
    {

    }

    public Student(String name)
    {
        this.name = name;
    }
    @Override
    public String toString()
    {
        return name;
    }
}