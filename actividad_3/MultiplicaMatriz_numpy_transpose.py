import sys
import time
import numpy as np

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
    
    A_np = np.array(A)
    B_np = np.array(B)

    C_np = np.matmul(A_np, B_np.transpose())
    

def display_matrix(matrix):
    for row in matrix:
        for element in row:
            print(element, end=" ")
        print()    

if __name__ == "__main__":
    start_time = time.time()
    main()
    print("--- %s seconds ---" % (time.time() - start_time))
