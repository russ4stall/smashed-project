/**
 * Created by russellf on 2/26/2016.
 */
public class RussellViewModel {
    private String name;
    private int age;
    private String nickname;
    private String spouse;

    public RussellViewModel(String name, int age, String nickname, String spouse) {
        this.name = name;
        this.age = age;
        this.nickname = nickname;
        this.spouse = spouse;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
