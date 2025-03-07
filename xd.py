import random

# Open the file in write mode
with open('random_vectors.txt', 'w') as file:
    # Generate 30 lines of 4 random numbers each (approximate ranges based on your dataset)
    for _ in range(30):
        # Generate 4 random numbers similar to your dataset
        # First three features between 4.0 and 7.9, last feature between 0.1 and 2.5
        line = f"{random.uniform(4.0, 7.9):.1f} {random.uniform(2.0, 4.5):.1f} {random.uniform(1.0, 6.9):.1f} {random.uniform(0.1, 2.5):.1f}"
        # Write the line to the file followed by a newline character
        file.write(line + '\n')

print("Random vectors saved to 'random_vectors'.")
