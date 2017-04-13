import math
import numpy


array = [7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1]
frames = 3
#note neu co cac gia tri giong nhau trong luc khoi tao store => tu xu ly
def FIFO():
    store = array[0:frames]
    result = [False] * frames
    index = 0
    PF = frames
    R = 0
    print store
    for i in range(frames, len(array)):
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

def Optimal(): #deo hieu, luoi meo code nua
    store = array[0:frames]
    stack = store
    result = [False] * frames
    index = 0
    PF = frames
    R = 0
    print store
    for i in range(frames, len(array)):
        if not array[i] in store:
            result.append(False)
            new_array = array[i + 1:len(array)]
            store[index] = array[i]
            PF = PF + 1
        else:
            result.append(True)
            R = R + 1
        print store

    print result
    print ("Page Fault: ", PF)
    print ("R: ", R)

def LRU():
    store = array[0:frames]
    stack =[]
    for i in range(0, frames):
        stack.insert(0, array[i])
    result = [False] * frames
    index = 0
    PF = frames
    R = 0
    print store
    for i in range(frames, len(array)):
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

FIFO()



