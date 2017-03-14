package test.bo;

import com.std.framework.annotation.Entity;
import com.std.framework.annotation.PrimaryKey;
import com.std.framework.model.ormap.MappingByClass;


@Entity(MappingByClass.class)
public class TestBo {

    @PrimaryKey
    private int id;
    private String name;
    private int age;
    private String lolLevel;
    private String war3Level;
    private String javaLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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
