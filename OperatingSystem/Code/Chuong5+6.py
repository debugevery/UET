import math
import numpy


array = [1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5]
frames = 3

def FIFO():
    store = []
    result = []
    index = 0
    PF = 0
    R = 0
    for i in range(len(array)):
        if len(store) < frames:
            if not array[i] in store:
                result.append(False)
                store.append(array[i])
                index = (index + 1) % frames
                PF +=1
            else:
                result.append(True)
                index = store.index(array[i])
                R +=1
        else:
            if not array[i] in store:
                result.append(False)
                store[index] = array[i]
                index = (index + 1) % frames
                PF = PF + 1
            else:
                result.append(True)
                R = R + 1
        print store

    print result
    print ("Page Fault: ", PF)
    print ("R: ", R)


def LRU():
    store = []
    stack =[]
    result = []
    index = 0
    PF = 0
    R = 0
    for i in range(len(array)):
        if (len(store) < frames):
            if not array[i] in store:
                result.append(False)
                store.append(array[i])
                stack.insert(0, array[i])
                PF += 1
            else:
                result.append(True)
                stack.remove(array[i])
                stack.insert(0, array[i])
                R = R + 1
        else:
            if not array[i] in store:
                result.append(False)
                index = store.index(stack.pop())
                stack.insert(0, array[i])
                store[index] = array[i]
                PF = PF + 1
            else:
                result.append(True)
                stack.remove(array[i])
                stack.insert(0, array[i])
                R = R + 1
        print store


    print result
    print ("Page Fault: ", PF)
    print ("R: ", R)

def second_chance():
    store = []
    result = []
    refer_bit = [0] * frames
    index = 0
    PF = 0
    R = 0

    for i in range(len(array)):
        if (len(store) < frames):
            if (array[i] not in store):
                store.append(array[i])
                refer_bit[index] = 1
                index = (index + 1) % frames
                ++PF
                result.append(False)
            else:
                index = store.index(array[i])
                refer_bit[index] = 1
                ++R
                result.append(True)
        else:
            if (array[i] not in store):
                while (True):
                    if refer_bit[index] is 1:
                        refer_bit[index] = 0
                        index = (index + 1) % frames
                    elif refer_bit[index] is 0:
                        store[index] = array[i]
                        refer_bit[index] = 1
                        index = (index + 1) % frames
                        PF += 1
                        result.append(False)
                        break
            else:
                new_index = store.index(array[i])
                refer_bit[new_index] = 1
                R += 1
                result.append(True)

        print (store, refer_bit)
    print PF, R


FIFO()



