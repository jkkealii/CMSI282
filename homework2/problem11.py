def mod_mult(a, b, p):
    result = 1
    for i in xrange(b):
        result = (result * a) % p
    return result

def mult_final(a, b, c, p):
    result = pow(b, c)
    result = mod_mult(a, result, p)
    return result