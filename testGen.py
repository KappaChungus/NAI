import random

def load_training_data(filename):
    training_data = []
    with open(filename, 'r') as file:
        for line in file:
            parts = line.strip().split(',')
            vector = list(map(float, parts[:-1]))
            group = parts[-1]
            training_data.append((vector, group))
    return training_data

def generate_vector_near_group(training_data):
    vector, group = random.choice(training_data)
    noisy_vector = [
        round(max(v + random.uniform(-0.3, 0.3), 0.1), 1)  # szum od -0.3 do 0.3, ale wartość nie mniejsza niż 0.1
        for v in vector
    ]
    return noisy_vector

training_data = load_training_data('train_data.txt')

with open('random_vectors.txt', 'w') as file:
    for _ in range(30):
        vector = generate_vector_near_group(training_data)
        line = f"{vector[0]},{vector[1]},{vector[2]},{vector[3]}"
        file.write(line + '\n')

print("Random vectors saved to 'random_vectors.txt'.")
