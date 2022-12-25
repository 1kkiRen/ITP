# Taulor formula for sine

import math

def taylor_sine(x, n):
    """Taylor series for sine"""
    result = 0
    for i in range(n):
        result += ((-1)**i)*(x**(2*i+1))/math.factorial(2*i+1)
    return result

def main():
    """Main function"""
    x = float(input("Enter x: "))
    n = int(input("Enter n: "))
    print("sin({0}) = {1}".format(x, taylor_sine(x, n)))

if __name__ == "__main__":
    main()
