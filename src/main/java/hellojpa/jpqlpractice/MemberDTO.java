package hellojpa.jpqlpractice;

public class MemberDTO {

    private String name;
    private int age;

    public MemberDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
