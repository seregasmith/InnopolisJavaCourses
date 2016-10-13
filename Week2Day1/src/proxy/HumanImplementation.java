package proxy;

/**
 * Created by Smith on 10.10.2016.
 */
public class HumanImplementation implements Human {
    private int age;
    private String name;

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
