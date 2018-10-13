from merge_sort import merge_sort as msort

def get_zero_num(mlist):
    num_zeroes = 0
    for index in range(len(mlist)):
        if mlist[index] == 0:
            num_zeroes += 1
        else:
            break
    return num_zeroes

def remove_duplicates(numbers):
    newlist = []
    for number in numbers:
        if number == 0:
            newlist.append(0)
        if number not in newlist:
           newlist.append(number)
    return newlist

if __name__=="__main__":

    with open('lngpok.in') as file_obj:
        num_list = file_obj.readline().split()

    #converting to int
    num_list = list(map(int, num_list))

    num_list = msort(num_list)

    num_zeroes = get_zero_num(num_list)
    num_list = remove_duplicates(num_list)

    

    print(num_list)
    max_combo = 0

    current_list = []

    





 
    
                    
            