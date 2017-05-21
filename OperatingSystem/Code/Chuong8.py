import numpy as np

max = 559
min = 0
current = 123
queue = [83, 47, 316, 124, 42, 96, 3, 149, 211, 43]

def FCFS():
    seek_distance = 0
    global current
    for item in queue:
        print ("|" + str(current) + " - " + str(item) + "|")
        seek_distance += abs(current - item)
        current = item
    print seek_distance

def SSTF():
    seek_distance = 0
    global current
    while len(queue) is not 0:
        resultMin = 100000
        for item in queue:
            if abs(current - item) < resultMin:
                resultMin = abs(current - item)
                itemMin = item

        print ("|" + str(current) + " - " + str(itemMin) + "|")
        seek_distance += resultMin
        current = itemMin
        queue.remove(current)

    print seek_distance

def SCAN():
    global max, min, current
    seek_distance = 0
    i = 0
    itemMin = max
    while(i < 2):
        i = i + 1
        print ("|" + str(current) + " - " + str(itemMin) + "|")
        seek_distance += abs(current - itemMin)
        current = itemMin
        itemMin = np.min(queue)
    print seek_distance

def CSCAN():
    global max, min, current
    seek_distance = 0
    i = 0
    itemMin = max

    myResult = 100000
    myvalue = 0
    for item in queue:
        if item < current:
            if current - item < myResult:
                myResult = current - item
                myvalue = item

    while(i < 3):
        i = i + 1
        print ("|" + str(current) + " - " + str(itemMin) + "|")
        seek_distance += abs(current - itemMin)
        current = itemMin
        if i is 1:
            itemMin = 0
        if i is 2:
            itemMin = myvalue

    print seek_distance

def LOOK():
    global current
    maxvalue = np.max(queue)
    minvalue = np.min(queue)
    a = abs(current - maxvalue) + abs(maxvalue - minvalue)
    print (str(current) + " - " + str(maxvalue))
    print (str(maxvalue) + " - " + str(minvalue))
    print a

def CLOOK():
    global current
    maxvalue = np.max(queue)
    minvalue = np.min(queue)

    myResult = 100000
    myvalue = 0
    for item in queue:
        if item < current:
            if current - item < myResult:
                myResult = current - item
                myvalue = item

    a = abs(current - maxvalue) + abs(maxvalue - minvalue) + abs(minvalue - myvalue)
    print (str(current) + " - " + str(maxvalue))
    print (str(maxvalue) + " - " + str(minvalue))
    print (str(minvalue) + " - " + str(myvalue))

    print a

CLOOK()