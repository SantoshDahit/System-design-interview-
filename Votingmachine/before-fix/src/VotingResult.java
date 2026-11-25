import javax.swing.plaf.synth.ColorType;

public class VotingResult {

    private String code;
    private Voter voter;
    private Candidate candidate;

    public VotingResult (String code, Voter voter, Candidate candidate){
        this.code = code;
        this.voter = voter;
        this.candidate = candidate;
    }

    public String getCode(){
        return code;
    }

    public Voter getVoter(){
        return voter;
    }

    public Candidate getCandidate(){
        return candidate;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
