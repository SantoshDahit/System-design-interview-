import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VotingSystemTest {

    private VotingSystem votingSystem;

    @BeforeEach
    void setUp() {
        votingSystem = new VotingSystem();                // fresh system for every test
        votingSystem.addVoter("V1", "v1", "pw1", "Ram");
        votingSystem.addVoter("V2", "v2", "pw2", "Sita");
        votingSystem.addCandidate("C1", "Mohan", "A");
        votingSystem.addCandidate("C2", "Balen", "B");
    }

    @Test
    void validVotesAreCountedForTheRightCandidate() {
        votingSystem.addVoting("V1", "C2");
        votingSystem.addVoting("V2", "C2");

        assertEquals(2, votingSystem.countVotes("C2"));
        assertEquals(0, votingSystem.countVotes("C1"));
    }

    @Test
    void everyVoteGetsItsOwnId() {
        VotingResult first = votingSystem.addVoting("V1", "C1");
        VotingResult second = votingSystem.addVoting("V2", "C1");

        assertNotEquals(first.getCode(), second.getCode());
        assertEquals(2, votingSystem.countVotes("C1"));   // nothing was overwritten
    }

    @Test
    void unknownVoterIsRejected() {
        assertThrows(VoterNotValidException.class, () -> votingSystem.addVoting("V9", "C1"));
        assertEquals(0, votingSystem.countVotes("C1"));
    }

    @Test
    void unknownCandidateIsRejected() {
        assertThrows(CandidateIdNotMatched.class, () -> votingSystem.addVoting("V1", "C9"));
    }
}
