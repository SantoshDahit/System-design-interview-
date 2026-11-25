import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class VotingSystem {

    private HashMap<String, Voter> newVoter = new HashMap<>();

    private HashMap<String, Candidate> newCandidate = new HashMap<>();
    public HashMap<String, VotingResult> newVotingResult = new HashMap<>();

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

    public VotingResult addVoting(String code, String voterCode, String candidateCode){
        Voter voter = getVoterDetails(voterCode);

        if (!newVoter.containsValue(voter)){
            throw new  VoterNotValidException(voterCode);
        }

        Candidate candidate = getCandidateDetails(candidateCode);
        if (!newCandidate.containsValue(candidate)){
            throw new  CandidateIdNotMatched(candidateCode);
        }

        VotingResult votingResult = new VotingResult(code, voter, candidate);
       newVotingResult.put(code, votingResult);
       return votingResult;

    }

    public List<VotingResult> getVotingListForCandidate(String candidateCode){
        List<VotingResult> votingResultList = new ArrayList<>();
        Candidate existingCandidate = getCandidateDetails(candidateCode);
        for (VotingResult votingResult : votingResultList){
            if (existingCandidate.equals(votingResult.getCandidate())){
                votingResultList.add(votingResult);
            }

        }
        return votingResultList;
    }
}
