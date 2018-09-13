

class Couch:
    width = 0
    length = 0
    color = ""
    brand = ""

    def __init__(self, width, length):
        self.width = width
        self.length = length


def selection_sort(couches):

    for i in range(len(couches)):

        max_idx = i

        for j in range(i+1, len(couches)):
            if couches[max_idx].width < couches[j].width:
                max_idx = j

        couches[max_idx], couches[i] = couches[i], couches[max_idx]


def merge_sort(couches):
    if len(couches) > 1:
        m = len(couches) // 2
        lefthalf = couches[:m]
        righthalf = couches[m:]

        merge_sort(lefthalf)
        merge_sort(righthalf)

        i = 0
        j = 0
        k = 0

        while i < len(lefthalf) and j < len(righthalf):
            if lefthalf[i].length < righthalf[j].length:
                couches[k] = lefthalf[i]
                i += 1
            else:
                couches[k] = righthalf[j]
                j += 1
            k += 1

        while i < len(lefthalf):
            couches[k] = lefthalf[i]
            i += 1
            k += 1

        while j < len(righthalf):
            couches[k] = righthalf[j]
            j += 1
            k += 1


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

    for couch in couches_list:
        print(couch.length)
    print('---------------------------------')

    merge_sort(couches_list)
    # selection_sort(couches_list)

    for couch in couches_list:
        print(couch.length) 