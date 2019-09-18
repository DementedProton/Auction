# IN4301_programming_assignment_1

## Ásta Lára Magnúsdóttir and Vasanth Subramanian

### The ‘auctions with budget constraints’ problem, as well as applicable algorithms. 
In this auction, k different items are sold to n different bidders. Each bidder i has a bid bij for item j. Additionally, each bidder i has a budget limit di. Due to this budget limit, given an allocation of the items among the bidders, the seller’s revenue for bidder i becomes min (d_i, \sum_{j=1}^k xij bij ), where
􏰝1 if item j is allocated to bidder i. xij = 0 otherwise.
In other words, if a bidder is allocated items whose combined cost exceeds his budget limit, he pays his budget limit. The objective is to find an allocation of the items that maximizes the seller’s revenue.
