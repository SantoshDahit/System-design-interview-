
public class Voter {

    private String code;
    private String username;
    private String password;

    private String name;

    public Voter (String code, String username, String password, String name){
        this.code = code;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    private String getCode(){
        return code;
    }

    private String getUsername(){
        return username;
    }
    private String getPassword(){
        return password;
    }
    private String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "code: " + code + " username: " + username + " password: " + password + " name: " + name ;
    }
}
