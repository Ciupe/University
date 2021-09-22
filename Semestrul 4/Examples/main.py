# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.

def format(s):
    final = []
    aux = ''
    for i in range (0, len(s)):
        if s[i] != ' ':
            aux += s[i]
        else:
            final.append(aux)
            aux = ''
    return final

def remove_duplicates(s):
    final = []
    for i in range (0, len(s)):
        found = False
        for j in range (0, len(s)):
            if s[i] == s[j] and i != j:
                found = True
        if found == False:
            final.append(s[i])
    print (final)

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print_hi('PyCharm')
    s = input("Give string: ")
    f = format(s)
    remove_duplicates(f)

    di = {1:'a', 2:'b', 3:'a', 4:'c'}
    values = di.values()
    keys = di.keys()
    xd = []
    for key in di:
        if (di[key] == 'a'):
            xd.append(key)
    print(xd)
# See PyCharm help at https://www.jetbrains.com/help/pycharm/
