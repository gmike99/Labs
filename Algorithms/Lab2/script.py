from merge_sort import merge_sort as msort


if __name__=="__main__":

    with open('lngpok.in') as file_obj:
        arr = file_obj.readline().split()

   
    #converting to int
    arr = list(map(int, arr))

    #finding the number of zeroes
    zeroes = sum(num == 0 for num in arr)
    
    #removing duplicates
    arr = list(set(arr))

    #merge sorting array
    arr = msort(arr)
    
    try:
        arr.remove(0)
    except ValueError:
        pass

    print(arr)
    print(zeroes)

    max_combo = 0
    for i in range(len(arr)):
        last_no = 0
        current_combo = 0

        zero_idx = zeroes
        idx = 0

        while idx < len(arr):
            if arr[idx] == last_no+1:
                current_combo += 1
            elif idx == 0:
                current_combo += 1
            else:
                if zero_idx > 0:
                    last_no+=1
                    zero_idx-=1
                    current_combo += 1
                    continue
                else:
                    zero_idx = zeroes
                    current_combo = 1
            max_combo = max(max_combo, current_combo)
            last_no = arr[idx]  
            idx+=1
        current_combo+=zero_idx
        max_combo = max(max_combo, current_combo)
        arr.pop(0)

    print(max_combo)


    





 
    
                    
            