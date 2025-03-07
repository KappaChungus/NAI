# Projekt: KNN

## Opis
Aplikacja pozwala na analizę danych, która opiera się na przekazaniu trzech argumentów:
1. `k` (int) - Liczba najbliższych sąsiadów.
2. Ścieżka do pliku `training_data.txt` (String) - Plik zawierający dane treningowe.
3. Ścieżka do pliku `newData.txt` (String) - Plik zawierający nowe dane do analizy.

## Usage
Opcjonalnie można wygenerować test skryptem testGen.py.

Aby uruchomić aplikację, należy wywołać metodę `main` z trzema argumentami:

```bash
java Main <k> <ścieżka_do_training_data.txt> <ścieżka_do_newData.txt>
