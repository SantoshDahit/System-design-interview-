//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();

        System.out.println("\n --- Adding voter details ----- ");
        votingSystem.addVoter("V1", "v1", "v1", "v1");
        votingSystem.addVoter("V2", "v2", "v2", "v2");
        votingSystem.addVoter("V3", "v3", "v3", "v3");

        System.out.println("\n --- Adding candidate details ----- ");
        votingSystem.addCandidate("c1", "Mohan", "kangres");
        votingSystem.addCandidate("C2",  "Balen", "RSp");
        votingSystem.addCandidate("C3", "KP", "yamale");

        System.out.println("\n --- start voting --- ");
        votingSystem.addVoting("R1", "V1", "C2");
        try {
            votingSystem.addVoting("R2", "V5", "C2");
        } catch (VoterNotValidException e) {
            System.out.println("Vote rejected: " + e.getMessage());   // program keeps running
        }
        
        votingSystem.addVoting("R3", "V2", "C2");
        votingSystem.addVoting("R3", "V3", "C4");

        votingSystem.getCandidateDetails("C2");

    }
}