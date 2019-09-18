public interface Solver {
    AuctionProblemInstance.Solution solve(AuctionProblemInstance a);

    String getName();
}
