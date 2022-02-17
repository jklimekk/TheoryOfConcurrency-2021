from graphviz import Digraph

# funkcja wyznaczająca relację zależności na podstawie alfabetu oraz zestawu transakcji
def dependent_relations(alphabet, T):
    D = set()
    for a in sorted(alphabet):
        for b in sorted(alphabet):
            if a == b and (a, b) not in D:
                D.add((a, b))
            else:
                a_transaction = T.get(a)
                a_transaction_splited = a_transaction.split("=")
                a_transaction_left, a_transaction_right = a_transaction_splited[0], a_transaction_splited[1]

                b_transaction = T.get(b)
                b_transaction_splited = b_transaction.split("=")
                b_transaction_left, b_transaction_right = b_transaction_splited[0], b_transaction_splited[1]

                if a_transaction_left in b_transaction_right or b_transaction_left in a_transaction_right and (a, b) not in D:
                    D.add((a, b))
    return D

# funkcja wyznaczająca relację niezależności na podstawie alfabetu oraz relacji zależności
def independent_relations(alphabet, D):
    I = set()
    for a in sorted(alphabet):
        for b in sorted(alphabet):
            if (a, b) not in D and (a, b) not in I:
                I.add((a, b))
    return I

# funkcja zwracająca ślad słowa 'w' w postaci stringa
def trace(word):
    result = '<'

    for i in range(len(word) - 1):
        result += word[i]
        result += ', '

    result += word[len(word)-1]
    result += '>'
    return result

# funckja tworząca stosy dla każdej litery alfabetu
# algorytm dodaje na stos literę oraz '*' na stosach liter, które są zależne od badanej litery
def create_stacks(alphabet, D, word):
    stacks = {sign: [] for sign in alphabet}
    index = len(word)

    for sign in word[::-1]:
        for letter in alphabet:
            if letter == sign:
                stacks[sign].append((sign, str(index)))
                index -= 1
            elif (sign, letter) in D:
                stacks[letter].append('*')

    return stacks

# funkcja zwracająca litery ze stosów, wykorzystywana do tworzenia klas foaty
def pop_letters_from_stack(stacks, alphabet):
    poped_letters = []
    for letter in sorted(alphabet):

        if stacks[letter]:
            poped_letter = stacks[letter].pop(len(stacks[letter]) - 1)

            if poped_letter != '*':
                poped_letters.append(poped_letter)

    poped_letters.sort()
    return poped_letters

# funkcja zamieniająca postać normalną foaty na string
def FNF_to_string(letter_mapping):
    fnf = ''
    for foata_class in letter_mapping:
        if len(foata_class) > 0:
            fnf += '('
            for sign in foata_class:
                fnf += sign[0]
            fnf += ')'
    return fnf

# funkcja korygująca wynik algorytmu
#   jeżeli którakolwiek z liter nowej potencjalnej klasy fnf (current_class) jest zależna od
#   elementów klasy poprzednio dodanej do fnf, to nie możemy połączyć tych klas
# jeżeli jednak wszystkie elementy są niezależne to łączymy te klasy
def correction(fnf, current_class, D):
    index = len(fnf) - 1

    for current_letter in current_class:
        for letter in fnf[index]:
            if (current_letter[0], letter[0]) in D:
                fnf.append(current_class)
                return fnf

    for letter in current_class:
        fnf[index].append(letter)
    fnf[index].sort()

# funkcja zwracająca postać normalną foaty po podstawie algorytmu ze stosami liter
def FNF(alphabet, D, word):
    stacks = create_stacks(alphabet, D, word)
    max_length = max([len(value) for value in stacks.values()])
    fnf = []

    for i in range(max_length):
        letters = pop_letters_from_stack(stacks, alphabet)

        if letters:
            if len(fnf) > 0:
                correction(fnf, letters, D)
            else:
                fnf.append(letters)
    return fnf

# funkcja rysująca graf na podstawie postaci normalnej foaty, relacji zależności i słowa w
# do grafu dodajemy tylko krawędzie łączące elementy zależne, należące do różnych klas foaty
# oraz nie ma jeszcze między nimi połączenia
def graph(fnf, D, word):
    dot_graph = Digraph()
    multi_edges = []  # bezpośrednie i pośrednie połączenia pomiędzy wierzchołkami
    edges = []  # bezpośrednie połączenia pomiędzy wierzchołkami

    for i in range(len(fnf) - 2, -1, -1):
        for u in fnf[i]:
            for j in range(i + 1, len(fnf)):
                for v in fnf[j]:
                    if (u[0], v[0]) in D and (u, v) not in multi_edges:

                        edges.append((u, v))
                        dot_graph.edge(u[1], v[1])

                        multi_edges.append((u, v))
                        for e in multi_edges:
                            if e[0] == v:
                                multi_edges.append((u, e[1]))

    for i, letter in enumerate(word):
        dot_graph.node(str(i+1), letter)
    print(dot_graph.source)


# funkcja uruchamiająca analizę danego przykładu
def run(T, alphabet, word):
    D = dependent_relations(alphabet, T)
    I = independent_relations(alphabet, D)
    t = trace(word)
    letter_mapping = FNF(alphabet, D, word)
    fnf_string = FNF_to_string(letter_mapping)

    print("\n============================================================================\n")
    print("Tranzakcje: ", T)
    print("Alfabet A =", sorted(alphabet))
    print("Słowo w =", word)
    print("Relacja zależności D =", sorted(D, key=lambda x: x[0]))
    print("Relacja niezależności I =", sorted(I, key=lambda x: x[0]))
    print("Ślad słowa [w] =", t)
    print("Postać normalna Foaty FNF([w]) =", fnf_string)
    print("Graf zależności:")
    graph(letter_mapping, D, word)


# Przyklad 1
transactions = {
    "a": "x=x+y",
    "b": "y=y+2z",
    "c": "x=3x+z",
    "d": "z=y-z"
}
A = {'a', 'b', 'c', 'd'}
w = 'baadcb'
run(transactions, A, w)


# Przyklad 2
transactions = {
    "a": "x=x+1",
    "b": "y=y+2z",
    "c": "x=3x+z",
    "d": "w=w+v",
    "e": "z=y-z",
    "f": "v=x+v"
}
A = {'a', 'b', 'c', 'd', 'e', 'f'}
w = 'acdcfbbe'
run(transactions, A, w)
