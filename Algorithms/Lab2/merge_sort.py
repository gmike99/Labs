def merge_sort(arg_list):

    res = []
    
    if len(arg_list) > 1:
        middle_idx = len(arg_list) // 2

        lefthalf = []
        righthalf = []

        lefthalf = arg_list[:middle_idx]
        righthalf = arg_list[middle_idx:]

        sorted_left = merge_sort(lefthalf)
        sorted_right = merge_sort(righthalf)

        res = merge(sorted_left, sorted_right)
    else:
        res = arg_list

    return res


def merge(lefthalf, righthalf):

    res = []
    left_idx = 0
    right_idx = 0   

    while left_idx < len(lefthalf) and right_idx < len(righthalf):
        if lefthalf[left_idx] < righthalf[right_idx]:
            res.append(lefthalf[left_idx])
            left_idx += 1
            
        else:
            res.append(righthalf[right_idx])
            right_idx +=  1

    res.extend(lefthalf[left_idx:])

    res.extend(righthalf[right_idx:])

    return res