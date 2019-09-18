# IN4301_programming_assignment_1

## Ásta Lára Magnúsdóttir and Vasanth Subramanian

### The ‘auctions with budget constraints’ problem, as well as applicable algorithms. 
In this auction, k different items are sold to n different bidders. Each bidder i has a bid b<sub>ij</sub> for item j. Additionally, each bidder i has a budget limit d<sub>i</sub>. Due to this budget limit, given an allocation of the items among the bidders, the seller’s revenue for bidder i becomes min (d<sub>i</sub>, \sum_{j=1}^k x<sub>ij</sub> b<sub>ij<sub> ), where
  􏰝1 if item j is allocated to bidder i. x<sub>ij</sub> = 0 otherwise.
In other words, if a bidder is allocated items whose combined cost exceeds his budget limit, he pays his budget limit. The objective is to find an allocation of the items that maximizes the seller’s revenue.
