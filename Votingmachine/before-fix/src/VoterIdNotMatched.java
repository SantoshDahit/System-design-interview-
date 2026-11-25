public class VoterIdNotMatched extends VoterNotValidException {
    public VoterIdNotMatched(String id) {
        super("VoterId miss match: " + id);
    }
}
