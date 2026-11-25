public class CandidateIdNotMatched extends VoterNotValidException {
    public CandidateIdNotMatched(String id) {
        super("Candidate miss match: " + id);
    }
}
