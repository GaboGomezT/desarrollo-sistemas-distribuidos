import sys
import time

def main():
    N = int(sys.argv[1])
    
    A = []
    B = []
    C = []

    # Inicializacion de matrices
    for i in range(N):
        A.append([])
        B.append([])
        C.append([])
        for j in range(N):
            A[i].append(2 * i - j)
            B[i].append(i + 2 * j)
            C[i].append(0)
    
    # Transpone la matriz B
    for i in range(N):
        for j in range(N):
            x = B[i][j]
            B[i][j] = B[j][i]
            B[j][i] = x

    for i in range(N):
        for j in range(N):
            for k in range(N):
                C[i][j] += A[i][k] * B[k][j]
    

def display_matrix(matrix):
    for row in matrix:
        for element in row:
            print(element, end=" ")
        print()    

if __name__ == "__main__":
    start_time = time.time()
    main()
    print("--- %s seconds ---" % (time.time() - start_time))