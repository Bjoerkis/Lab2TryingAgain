package studentinfo;

import javax.persistence.*;


@Entity
@Table(name = "students",schema = "student_info")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private int Grade;

    public Students(String name, int grade) {
        this.name = name;
        Grade = grade;
    }

    public Students() { }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    @Override
    public String toString() {
        return "Students" +
                "Id: " + Id +
                ", name: '" + name +
                ", Grade: " + Grade;
    }
}
