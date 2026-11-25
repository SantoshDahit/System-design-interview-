import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VotingSystem {

    private HashMap<String, Voter> newVoter = new HashMap<>();

    private HashMap<String, Candidate> newCandidate = new HashMap<>();
    public HashMap<String, VotingResult> newVotingResult = new HashMap<>();

    // Fix #2: the system creates the vote ID itself, so two votes can never share an ID.
    private int nextVoteNumber = 1;

    public Voter addVoter(String code, String userName, String password, String name){
        Voter voter = new Voter(code, userName, password, name);
        newVoter.put(code, voter);
        System.out.println("Voter details: " + voter.toString());
        return voter;
    }

    public Voter getVoterDetails(String code){
        return newVoter.get(code);
    }

    public Candidate addCandidate(String code,String name, String party){
        Candidate candidate = new Candidate(code, name, party);
        newCandidate.put(code, candidate);
        System.out.println("Candidate details: " + candidate.toString());
        return candidate;
    }

    public Candidate getCandidateDetails(String code){
        return newCandidate.get(code);
    }

    // Fix #2: no vote code from the caller any more.
    public VotingResult addVoting(String voterCode, String candidateCode){
        Voter voter = getVoterDetails(voterCode);

        if (!newVoter.containsValue(voter)){
            throw new VoterIdNotMatched(voterCode);
        }

        Candidate candidate = getCandidateDetails(candidateCode);
        if (!newCandidate.containsValue(candidate)){
            throw new  CandidateIdNotMatched(candidateCode);
        }

        String code = "R" + nextVoteNumber++;

        VotingResult votingResult = new VotingResult(code, voter, candidate);
        newVotingResult.put(code, votingResult);
        return votingResult;
    }

    // Fix #1: loop over the saved votes (before, it looped over the new empty list).
    public List<VotingResult> getVotingListForCandidate(String candidateCode){
        Candidate existingCandidate = getCandidateDetails(candidateCode);
        if (existingCandidate == null) {
            throw new CandidateIdNotMatched(candidateCode);
        }

        List<VotingResult> votingResultList = new ArrayList<>();
        for (VotingResult votingResult : newVotingResult.values()){
            if (existingCandidate.equals(votingResult.getCandidate())){
                votingResultList.add(votingResult);
            }
        }
        return votingResultList;
    }

    public int countVotes(String candidateCode){
        return getVotingListForCandidate(candidateCode).size();
    }
}
