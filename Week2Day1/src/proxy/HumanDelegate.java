package proxy;

/**
 * Created by Smith on 10.10.2016.
 */
public class HumanDelegate implements  Human {
    private Human delegate = new HumanImplementation();
    @Override
    public int getAge() {
        return delegate.getAge();
    }

    @Override
    public void setAge(int age) {
        delegate.setAge(age);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public void setName(String name) {
        delegate.setName(name);
    }
}
