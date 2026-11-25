public class Candidate {

    private String code;
    private String name;
    private String party;

    public Candidate(String code, String name, String party){
        this.code = code;
        this.name = name;
        this.party = party;
    }

    public String getCode (){
        return code;
    }

    public String getName(){
        return  name;
    }

   public String getParty(){
        return  party;
   }

    @Override
    public String toString() {
        return "code: " + code + " name: " + name  + " party: " + party;
    }
}
