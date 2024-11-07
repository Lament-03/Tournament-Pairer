# Tournament-Pairer
A system that uses a database with information of players to match them for tournaments

Uses the SQLite Database Engine and Java for its base.
Lots of functions to perform maintenance on the Database
Represents the Players of the tournament as objects which will have all their necessary information as attributes
Database stores the names of the people participating, the games that have been played and the teams that the players are a part of. This will then be used to calculate a win ratio for everyone.
The tournament is always seeded so a win ratio is calculated for every player with their total wins over total games.
Once everyone is seeded a stack and circular queue is used to match up the seeds so they are paired in the intended way.
Half of the players are put in the stack and the other half in the queue, then the stack and queue will be popping out the players one at a time in sequence.

