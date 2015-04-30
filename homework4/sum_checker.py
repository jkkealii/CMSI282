from __future__ import print_function

def sum_checker(num_set, value):
    result = False
    set_length = len(num_set)

    if set_length == 0:
        raise ValueError('No numeric list given')
    for i in num_set:
        if not number_check(i):
            raise ValueError('Elements in list must be a number')
    if not number_check(value):
        raise ValueError('Target value must be a number')

    # Check to see if target value is greater than or equal to the MAX sum
    MAX = sum(num_set)
    if value > MAX:
        return False
    elif value == MAX:
        return True
    else:
        num_set.sort()
        # Initialize 2D table, all false ([rows][columns])
        subset = [[False]*(set_length + 1) for x in range(value + 1)]

        # sobset = [['0']*(set_length + 1) for x in range(value + 1)]]

        # If sum is 0, answer is true
        for i in range(set_length + 1):
            subset[0][i] = True
            # sobset[0][i] = 0

        # If sum isn't 0 and set is empty, answer is false
        for i in range(1, value + 1):
            subset[i][0] = False
            # sobset[i][0] = 1

        # Fill in table from bottom-up
        for i in range(1, value + 1):
            for j in range(1, set_length + 1):
                subset[i][j] = subset[i][j-1]
                if i >= num_set[j-1]:
                    subset[i][j] = subset[i][j] or subset[i - num_set[j-1]][j-1]
        
        # Print table
        s = [[str(e) for e in row] for row in subset]
        lens = [max(map(len, col)) for col in zip(*s)]
        fmt = '\t'.join('{{:{}}}'.format(x) for x in lens)
        table = [fmt.format(*row) for row in s]
        print ('\n'.join(table))
    
    return subset[value][set_length]

def number_check(_input):
    try:
        float(_input)
        return True
    except ValueError:
        return False

test_set = [2, 3, 4, 5, 12, 34]
value = 46
if (sum_checker(test_set, value)):
    print ("found subset with given sum")
else:
    print ("no subset found with given sum")