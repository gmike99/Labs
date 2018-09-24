import timeit

class Couch:
    width = 0
    length = 0
    color = ""
    brand = ""

    def __init__(self, width, length):
        self.width = width
        self.length = length



def wrapper(func, *args, **kwargs):
    def wrapped():
        return func(*args, **kwargs)
    return wrapped



def selection_sort(couches):
    comparison_count = 0
    swap_count = 0

    for i in range(len(couches)):

        max_idx = i

        for j in range(i+1, len(couches)):
            if couches[max_idx].width < couches[j].width:
                comparison_count += 1
                max_idx = j
        
        couches[max_idx], couches[i] = couches[i], couches[max_idx]
        if max_idx != i:
            swap_count += 1


    print('Selection sort')
    print('Comparison count: {}\n Swap count: {}\n'.format(comparison_count, swap_count))
    for couch in couches:
        print(couch.width, end=" ")
        
        


def merge_sort(couches):

    comparison_count = 0
    swap_count = 0

    if len(couches) > 1:
        comparison_count += 1
        m = len(couches) // 2
        lefthalf = couches[:m]
        righthalf = couches[m:]

        merge_sort(lefthalf)
        merge_sort(righthalf)

        i = 0
        j = 0
        k = 0

        while i < len(lefthalf) and j < len(righthalf):
            comparison_count +=2
            if lefthalf[i].length < righthalf[j].length:
                comparison_count += 1
                couches[k] = lefthalf[i]
                if k != i:
                    swap_count +=1
                i += 1
            else:
                couches[k] = righthalf[j]
                if k != j:
                    swap_count += 1
                j += 1
            k += 1

        while i < len(lefthalf):
            comparison_count += 1
            couches[k] = lefthalf[i]
            if k != i:
                swap_count += 1
            i += 1
            k += 1

        while j < len(righthalf):
            comparison_count += 1
            couches[k] = righthalf[j]
            if k != j:
                swap_count += 1
            j += 1
            k += 1

    print('Merge sort')
    print('Comparison count: {}\n Swap count: {}\n'.format(comparison_count, swap_count))
    for couch in couches:
        print(couch.length, end=" ")




if __name__ == "__main__":
    couches_list = []

    couch1 = Couch(12, 35)
    couch2 = Couch(64, 65)
    couch3 = Couch(98, 47)
    couch4 = Couch(123, 36)
    couch5 = Couch(98, 72)
    couch6 = Couch(15, 86)
    couch7 = Couch(21, 45)
    couch8 = Couch(65, 26)
    couch9 = Couch(18, 74)

    couches_list.append(couch1)
    couches_list.append(couch2)
    couches_list.append(couch3)
    couches_list.append(couch4)
    couches_list.append(couch5)
    couches_list.append(couch6)
    couches_list.append(couch7)
    couches_list.append(couch8)
    couches_list.append(couch9)

    # wrapped = wrapper(selection_sort, couches_list)
    # timeit.timeit(wrapped, number=1)
    #wfejbveio;

    wrapped = wrapper(merge_sort, couches_list)
    timeit.timeit(wrapped, number=1)

