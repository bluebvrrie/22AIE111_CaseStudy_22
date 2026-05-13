import java.util.Scanner;

class VotingException extends Exception {
    public VotingException(String message) {
        super(message);
    }
}

class Person {
    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    // Encapsulation
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    // Polymorphism
    public void displayRole() {
        System.out.println("Person");
    }
}

class Voter extends Person {
    private String password;
    private boolean hasVoted;
    public Voter(int id, String name, String password) {
        super(id, name);
        this.password = password;
        hasVoted = false;
    }
    public String getPassword() {
        return password;
    }
    public boolean getHasVoted() {
        return hasVoted;
    }
    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
    @Override
    public void displayRole() {
        System.out.println(getName() + " is a Voter");
    }
}

class Admin extends Person {
    private String adminPassword;
    public Admin(int id, String name, String adminPassword) {
        super(id, name);
        this.adminPassword = adminPassword;
    }
    public boolean login(String password) {
        return adminPassword.equals(password);
    }
    @Override
    public void displayRole() {
        System.out.println(getName() + " is an Admin");
    }
    public void viewResults(Candidate[] candidates, int totalCandidates) {
        System.out.println("\n\tFINAL RESULTS");
        int maxVotes = -1;
        Candidate winner = null;
        for (int i = 0; i < totalCandidates; i++) {
            System.out.println(
                    candidates[i].getCandidateId() + ". "+ candidates[i].getCandidateName()+ " : "+ candidates[i].getVoteCount()+ " votes");
            if (candidates[i].getVoteCount() > maxVotes) {
                maxVotes = candidates[i].getVoteCount();
                winner = candidates[i];
            }
        }
        if (winner != null) {
            System.out.println(
                    "\nWinner is: "+ winner.getCandidateName()+ " with "+ winner.getVoteCount()+ " votes");
        }
    }
}

class Candidate {
    private int candidateId;
    private String candidateName;
    private int voteCount;
    public Candidate(int candidateId, String candidateName) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        voteCount = 0;
    }
    public int getCandidateId() {
        return candidateId;
    }
    public String getCandidateName() {
        return candidateName;
    }
    public int getVoteCount() {
        return voteCount;
    }
    public void incrementVote() {
        voteCount++;
    }
}

class Election {
    private boolean electionStarted;
    private boolean electionEnded;
    public Election() {
        electionStarted = false;
        electionEnded = false;
    }
    public boolean isElectionStarted() {
        return electionStarted;
    }
    public boolean isElectionEnded() {
        return electionEnded;
    }
    public void startElection() {
        electionStarted = true;
        electionEnded = false;
        System.out.println("Election Started Successfully!");
    }
    public void endElection() {
        electionStarted = false;
        electionEnded = true;
        System.out.println("Election Ended Successfully!");
    }
    public void castVote(
            Voter voter,
            Candidate[] candidates,
            int totalCandidates,
            int candidateId)
            throws VotingException {
        if (electionEnded) {
            throw new VotingException("Election has ended!");
        }

        if (!electionStarted) {
            throw new VotingException("Election has not started!");
        }

        if (voter.getHasVoted()) {
            throw new VotingException("You have already voted!");
        }
        boolean found = false;

        for (int i = 0; i < totalCandidates; i++) {
            if (candidates[i].getCandidateId() == candidateId) {
                candidates[i].incrementVote();
                voter.setHasVoted(true);
                System.out.println("Vote Cast Successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            throw new VotingException("Invalid Candidate ID!");
        }
    }
}

public class OnlineVotingSystem {
    // Find voter by ID
    public static Voter findVoter(
            Voter[] voters,
            int totalVoters,
            int id) {
        for (int i = 0; i < totalVoters; i++) {
            if (voters[i].getId() == id) {
                return voters[i];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Election election = new Election();
        Admin admin =new Admin(1,"Election Officer","admin123");

        Candidate[] candidates = new Candidate[100];
        Voter[] voters = new Voter[100];
        int totalCandidates = 0;
        int totalVoters = 0;
        int mainChoice;

        do {
            System.out.println("\n\tONLINE VOTING SYSTEM");
            System.out.println("1. Admin");
            System.out.println("2. Voter");
            System.out.println("3. Exit");
            System.out.print("\nEnter Choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid Input!");
                sc.next();
                System.out.print("Enter Again: ");
            }
            mainChoice = sc.nextInt();
            switch (mainChoice) {
                case 1:
                    sc.nextLine();
                    System.out.print("Enter Admin Password: ");
                    String pass = sc.nextLine();
                    if (!admin.login(pass)) {
                        System.out.println("Invalid Password!");
                        break;
                    }
                    int adminChoice;

                    do {
                        System.out.println("\n\tADMIN MENU");
                        System.out.println("1. Add Candidates");
                        System.out.println("2. Add Voters");
                        System.out.println("3. Start Election");
                        System.out.println("4. End Election");
                        System.out.println("5. View Results");
                        System.out.println("6. Display Role");
                        System.out.println("7. Back");
                        System.out.print("Enter Choice: ");
                        adminChoice = sc.nextInt();
                        sc.nextLine();

                        switch (adminChoice) {
                            case 1:
                                System.out.print("Enter number of candidates: ");
                                int n = sc.nextInt();
                                sc.nextLine();
                                for (int i = 0; i < n; i++) {
                                    System.out.print("Enter Candidate Name: ");
                                    String cname = sc.nextLine();
                                    candidates[totalCandidates] = new Candidate(totalCandidates + 1,cname);
                                    totalCandidates++;
                                }
                                System.out.println("Candidates Added Successfully!");
                                break;

                            case 2:
                                System.out.print("Enter number of voters: ");
                                int v = sc.nextInt();
                                sc.nextLine();
                                for (int i = 0; i < v; i++) {
                                    System.out.print( "Enter Voter ID: ");
                                    int vid = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Enter Voter Name: ");
                                    String vname = sc.nextLine();
                                    System.out.print("Set Password: ");
                                    String vpass = sc.nextLine();
                                    voters[totalVoters] =new Voter(vid,vname,vpass);
                                    totalVoters++;
                                }
                                System.out.println("Voters Added Successfully!");
                                break;

                            case 3:

                                if(totalCandidates == 0) {

                                    System.out.println("Add candidates first!");
                                }

                                else if(totalVoters == 0) {

                                    System.out.println("Add voters first!");
                                }

                                else {

                                    election.startElection();
                                }

                                break;

                            case 4:
                                election.endElection();
                                break;
                            
                            case 5:
                                if (!election.isElectionEnded()) {
                                    System.out.println("Results can be viewed only after election ends!");
                                }
                                else {
                                    admin.viewResults(candidates,totalCandidates);
                                }
                                break;

                            case 6:
                                admin.displayRole();
                                break;
                            
                            case 7:
                                System.out.println("Returning...");
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }
                    } while (adminChoice != 7);
                    break;

                case 2:
                    try {
                        if (totalVoters == 0) {
                            throw new VotingException("No voters registered!");
                        }
                        if (totalCandidates == 0) {
                            throw new VotingException("No candidates available!");
                        }if (!election.isElectionStarted()) {
                            throw new VotingException("Election has not started!");
                        }

                        System.out.print("Enter Voter ID: ");
                        int voterId = sc.nextInt();
                        sc.nextLine();
                        Voter currentVoter =findVoter(voters,totalVoters,voterId);
                        if (currentVoter == null) {
                            throw new VotingException("Voter not found!");
                        }
                        System.out.print("Enter Password: ");
                        String voterPass = sc.nextLine();
                        if (!currentVoter.getPassword().equals(voterPass)) {
                            throw new VotingException("Incorrect Password!");
                        }
                        System.out.println("\n\tCANDIDATES");

                        for (int i = 0; i < totalCandidates; i++) {
                            System.out.println(candidates[i].getCandidateId() + ". "+ candidates[i].getCandidateName());
                        }
                        System.out.print( "Enter Candidate ID: ");
                        int cid = sc.nextInt();
                        election.castVote(currentVoter,candidates,totalCandidates,cid);
                    }

                    catch (VotingException e) {
                        System.out.println("Exception: "+ e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Exiting System...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }
        } while (mainChoice != 3);
        sc.close();
    }
}
