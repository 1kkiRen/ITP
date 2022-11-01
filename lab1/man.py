from tqdm import tqdm
n0 = -31

for i in tqdm(range(100000)):
    n0 += i

    if(n0 % 22 == 2 and n0 % 21 == 2):
        print(n0)
        break