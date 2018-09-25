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
    append_count = 0

    for left_idx in range(len(couches)):

        max_idx = left_idx

        for right_idx in range(left_idx+1, len(couches)):
            if couches[max_idx].width < couches[right_idx].width:
                comparison_count += 1
                max_idx = right_idx
        
        couches[max_idx], couches[left_idx] = couches[left_idx], couches[max_idx]
        if max_idx != left_idx:
            append_count += 1


    print('Selection sort')
    print('Comparison count: {}\n Swap count: {}\n'.format(comparison_count, append_count))
    for couch in couches:
        print(couch.width, end=" ")
        
        
def merge_sort(couches):

    res = []
    
    if len(couches) > 1:
        # comparison_count += 1
        middle_idx = len(couches) // 2

        lefthalf = []
        righthalf = []

        lefthalf = couches[:middle_idx]
        righthalf = couches[middle_idx:]

        sorted_left = merge_sort(lefthalf)
        sorted_right = merge_sort(righthalf)

        res = merge(sorted_left, sorted_right)
    else:
        res = couches

    return res


def merge(lefthalf, righthalf):

    res = []
    left_idx = 0
    right_idx = 0   

    while left_idx < len(lefthalf) and right_idx < len(righthalf):
        if lefthalf[left_idx].length < righthalf[right_idx].length:
            res.append(lefthalf[left_idx])
            left_idx += 1
            
        else:
            res.append(righthalf[right_idx])
            right_idx +=  1

    res.extend(lefthalf[left_idx:])

    res.extend(righthalf[right_idx:])

    return res




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


    sorted = merge_sort(couches_list)
    for i in range(len(sorted)):
        print(sorted[i].length)
    
