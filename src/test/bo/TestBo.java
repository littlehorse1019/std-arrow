package test.bo;

import com.std.framework.annotation.Entity;
import com.std.framework.annotation.PrimaryKey;
import com.std.framework.model.orm.MapByClass;


@Entity(MapByClass.class)
public class TestBo {

    @PrimaryKey
    private Integer id;
    private String name;
    private Integer age;
    private String lolLevel;
    private String war3Level;
    private String javaLevel;

    @Override
    public String toString() {
        return "TestBo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", lolLevel='" + lolLevel + '\'' +
                ", war3Level='" + war3Level + '\'' +
                ", javaLevel='" + javaLevel + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLolLevel() {
        return lolLevel;
    }

    public void setLolLevel(String lolLevel) {
        this.lolLevel = lolLevel;
    }

    public String getWar3Level() {
        return war3Level;
    }

    public void setWar3Level(String war3Level) {
        this.war3Level = war3Level;
    }

    public String getJavaLevel() {
        return javaLevel;
    }

    public void setJavaLevel(String javaLevel) {
        this.javaLevel = javaLevel;
    }
}
