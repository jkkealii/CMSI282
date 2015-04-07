def encrypt(plaintext, keyword):
    plaintext = plaintext.upper()
    plaintext = plaintext.replace(' ', '')
    keyword = keyword.upper()
    if len(keyword) < 1:
        raise ValueError('Key cannot be the empty string')
    ciphertext = ""
    keyword = keyword + plaintext
    i = 0
    while i < len(plaintext):
        ciphertext += shifted(plaintext[i], keyword[i])
        i += 1
    return ciphertext
 
def decrypt(ciphertext, keyword):
    ciphertext = ciphertext.upper()
    keyword = keyword.upper()
    if len(keyword) < 1:
        raise ValueError('Key cannot be the empty string')
    plaintext = ""
    i = 0
    while i < len(ciphertext):
        plaintext += shifted(ciphertext[i], keyword[i % len(keyword)], True)
        keyword += plaintext[-1]
        i += 1
    return plaintext
 
def rotated(letter, number):
    return chr((((ord(letter) - ord('A')) + number) % 26) + ord('A'))
 
def shifted(mark, grapheme, inverse=False):
    degree = ord(grapheme) - ord('A')
    if inverse:
        degree = -degree
    return rotated(mark, degree)

message = "Implement the Autokey Vigenere cipher"

key1 = "toal"
test1 = encrypt(message, key1)
print encrypt(message, key1)
print decrypt(test1, key1)

key2 = "=+*@"
test2 = encrypt(message, key2)
print encrypt(message, key2)
print decrypt(test2, key2)

key3 = ""
test3 = encrypt(message, key3)
print encrypt(message, key3)
print decrypt(test3, key3)