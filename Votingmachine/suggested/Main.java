public class Main {

    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();

        System.out.println("\n --- Adding voter details ----- ");
        votingSystem.addVoter("V1", "v1", "v1", "v1");
        votingSystem.addVoter("V2", "v2", "v2", "v2");
        votingSystem.addVoter("V3", "v3", "v3", "v3");

        System.out.println("\n --- Adding candidate details ----- ");
        votingSystem.addCandidate("C1", "Mohan", "kangres");
        votingSystem.addCandidate("C2",  "Balen", "RSp");
        votingSystem.addCandidate("C3", "KP", "yamale");

        System.out.println("\n --- start voting --- ");
        vote(votingSystem, "V1", "C2");
        vote(votingSystem, "V5", "C2");   // unknown voter -> rejected, program keeps going
        vote(votingSystem, "V2", "C2");
        vote(votingSystem, "V3", "C4");   // unknown candidate -> rejected

        System.out.println("\n --- results --- ");
        System.out.println("C1 Mohan: " + votingSystem.countVotes("C1"));
        System.out.println("C2 Balen: " + votingSystem.countVotes("C2"));
        System.out.println("C3 KP: " + votingSystem.countVotes("C3"));
    }

    // The service throws; the caller (here, Main) catches and decides what to show.
    private static void vote(VotingSystem votingSystem, String voterCode, String candidateCode) {
        try {
            VotingResult result = votingSystem.addVoting(voterCode, candidateCode);
            System.out.println("Vote saved: " + result.getCode() + " (" + voterCode + " -> " + candidateCode + ")");
        } catch (VoterNotValidException e) {
            System.out.println("Vote rejected: " + e.getMessage());
        }
    }
}
