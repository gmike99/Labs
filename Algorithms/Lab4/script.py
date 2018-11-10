from math import sqrt
from collections import namedtuple, deque
import copy
from collections import OrderedDict

Edge = namedtuple('Edge', 'start, end, weight')

def get_weights_from_file(filename):
    with open(filename) as file_obj:
        weights = file_obj.readline().split(',')
    weights = list(map(int, weights))
    return weights

def generate_matrix(weights):
    matrix = []
    rank = int(sqrt(len(weights)))
    counter = iter(weights)
    for i in range(rank):
        matrix.append([])
        for j in range(rank):
            matrix[i].append(next(counter))

    return matrix

def get_edgelist(matrix):
    edgelist = []
    for current_element in range(len(matrix)):
        edgelist.extend([Edge(current_element, idx, num) 
                        for idx, num in enumerate(matrix[current_element]) if num != 0])
    return edgelist

def solve():
    weight_list = get_weights_from_file('profes.in')
    rank = sqrt(len(weight_list))
    matrix = generate_matrix(weight_list)
    edge_list = get_edgelist(matrix)
    min_weight = min(edge_list, key=lambda x: x.weight).weight
    max_weight = max(edge_list, key=lambda x: x.weight).weight

    best_diff_list = []
    max_diff = max_weight - min_weight
    start_edges = [edge for edge in edge_list if edge.start == 0]
    visited = []
    streak_weights = []

    visited_vertices = set()
    candidate_dict = OrderedDict()
    for start_edge in start_edges:
        print('a')        
        for accepted_diff in range(0, max_diff+1):
            current_vertex = start_edge.end
            candidate_dict.clear()
            visited.clear()
            visited_vertices.clear()
            visited.append(start_edge)
            diff_points = copy.copy(accepted_diff)
            cur_min, cur_max = start_edge.weight - diff_points, start_edge.weight + diff_points
            if cur_min < 0: cur_min = 0
            streak_weights.clear()
            streak_weights.append(start_edge.weight)

            done = False
            while not done:
                done = len(visited_vertices) == rank
                accepted_neighbors = [edge for edge in edge_list 
                                        if cur_min <= edge.weight <= cur_max and edge.start == current_vertex
                                        and edge not in visited]

                # assign cur_min and cur_max to current vertex 
                if len(accepted_neighbors) > 1: candidate_dict[current_vertex] = (cur_min, cur_max)
                
                try:
                    candidate_edge = accepted_neighbors.pop()   
                        
                    visited.append(candidate_edge) 
                    visited_vertices.add(current_vertex)
                    current_vertex = candidate_edge.end
                    visited_vertices.add(current_vertex)

                    streak_weights.append(candidate_edge.weight)
                    
                    streak_max = max(streak_weights)
                    streak_min = min(streak_weights)

                    streak_len = streak_max - streak_min

                    buffer = accepted_diff - streak_len

                    cur_min = streak_min - buffer
                    cur_max = streak_max + buffer
                    # print(cur_min)
                    # print(cur_max)
                    continue
                except IndexError:
                    try:
                        current_vertex = list(candidate_dict.keys())[-1]
                        cur_min = candidate_dict[current_vertex][0]
                        cur_max = candidate_dict[current_vertex][1]
                        candidate_dict.popitem(last=True)
                        continue
                    except IndexError:
                        break
            else:
                # if accepted_diff == 24: print(start_edge)
                best_diff_list.append(accepted_diff)
                break

            candidate_dict.clear()

    return best_diff_list           

    
if __name__ == '__main__':
    print(solve())
