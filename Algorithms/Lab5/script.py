BINARY_STRING = ''
NUMBER = 0
SOLVED = {}
BINARY_LIST = []


def to_bin(dec):
    return bin(dec)[2:]


def read_data(file_name):
    global BINARY_STRING, NUMBER
    with open(file_name) as f:
        BINARY_STRING, NUMBER = f.readline().split(' ')


def solve(width):
    if width <= 0:
        return 0
    global BINARY_STRING, NUMBER, SOLVED
    SOLVED[width-1] = solve(width-1)
    work_str = BINARY_STRING[-width:]
    string = work_str[0]

    if string == '0':
        return SOLVED[width-1]
    work_str = work_str[1:]

    str_iter = iter(work_str)

    condition = True
    found_list = []
    while condition:
        if SOLVED[width-1] == 0:
            string = string + work_str
            del str_iter
        if equals_binary(string):
            found_list.append(string)

        try:
            string = string + next(str_iter)
        except:
            break
        condition = is_present(string)

    if not found_list:
        return -1

    min_found = SOLVED[width-1] + 1
    for found in found_list:
        w = width - len(found)
        if SOLVED[w] + 1 < min_found:
            min_found = SOLVED[w] + 1
    return min_found


def equals_binary(string):
    return string in BINARY_LIST


def is_present(string):
    in_binary = False
    for bin_string in BINARY_LIST:
        if bin_string.startswith(string):
            in_binary = True
            break
    return in_binary


def get_binary():
    global BINARY_STRING, NUMBER
    dec = int(BINARY_STRING, 2)
    counter = 0
    res = []
    next_number = 0
    while next_number < dec:
        next_number = pow(int(NUMBER), counter)
        bin_number = to_bin(next_number)
        res.append(bin_number)
        counter += 1
    return res


if __name__ == '__main__':
    read_data('fantz.in')
    if int(NUMBER) % 2 == 1 and BINARY_STRING.endswith('0'):
        print('-1')
    else:
        BINARY_LIST.extend(get_binary())
        solve(len(BINARY_STRING)+1)
        print(SOLVED[len(BINARY_STRING)])
    print(BINARY_LIST)