# Genetic_TSP
Completed in 2017, this is a project where the purpose was to implement a genetic algorithm system for the Traveling Salesman Problem. Program will work on the two provided .txt files (berlin52.txt and dj38.txt)

A few details on the Genetic Algorithm:
- Initial Population initializer: creates a population of size Pop_Size of randomized individuals.
- Reproduction: uses Tournament Selection
- Crossover: given two individuals, this creates two offspring using (1) Uniform order crossover with bitmask and (2) Order crossover
- Mutator: given an individual, creates a mutated individual (avoids convergence).
- Fitness evaluation function: Total distance traveled (the shorter the distance, the more fit an individual is)
- Genetic algorithm system: This is the implementation of the GA system
- User Parameters: population size, maximum generation span, probability of (crossover, mutation, etc.)
