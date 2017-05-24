def contiguous_allocation():
    block_size = 2 * pow(2, 10)
    position = 15.25 * pow(2, 10)
    block = int(position / block_size)
    offset = position % block_size
    print (block, offset)

def extent_based():
    block_size = 2 * pow(2, 10)
    position = 15 * pow(2, 20)
    extent_block = 100

    extent = int(int(position/block_size)/extent_block)
    block = int(position / block_size) % extent_block
    offset = position % block_size
    print (extent, block, offset)

def linked_list_allocation():
    pointer_size = 4;
    block_size = 2 * pow(2, 10)
    data = block_size - pointer_size
    position = 15.5 * pow(2, 10)
    block = int(position / data)
    offset = position % data
    print (block, offset)

def indexed_allocation_onelv():
    block_size = 4 * pow(2, 10)
    pointer_size = 4
    position = 35 * pow(2, 10)
    data = block_size - pointer_size

    maximum_file_size = (block_size/pointer_size) * block_size
    block = int(position/block_size)
    offset = position % block_size

    print(block, offset)

def indexed_allocation_twolv():
    block_size = 4 * pow(2, 10)
    pointer_size = 4
    position = 15 * pow(2, 10)
    data = block_size - pointer_size
    other_data = block_size / pointer_size

    maximum_file_size = (block_size / pointer_size)**2 * block_size
    block = int(position / block_size)
    offset = position % block_size
    outer_index = int(block / other_data)
    inner_index = block % other_data

    print(outer_index, inner_index, offset)

def linked_index_allocation():
    block_size = 2 * pow(2, 10)
    pointer_size = 4
    position = 15.5 * pow(2, 20)
    data = block_size - pointer_size
    other_data = block_size / pointer_size - 1

    maximum_file_size = (block_size / pointer_size) ** 2 * block_size
    block = int(position / block_size)
    offset = position % block_size
    outer_index = int(block / other_data)
    inner_index = block % other_data

    print(block, outer_index, inner_index, offset)

def UNIX_system():
    block_size = 4 * pow(2, 10)
    pointer_size = 4
    data = block_size/pointer_size
    position = 21 * pow(2, 10)

    num_direct_pointer = 12
    num_single_indirect = 1
    num_double_indirect = 1
    num_triple_indirect = 1

    maximum_trip = block_size * (num_direct_pointer + num_single_indirect*pow(data, 1) + num_double_indirect*pow(data, 2) + num_triple_indirect*pow(data, 3))
    maximum_num_index = num_single_indirect + num_double_indirect*data + num_triple_indirect*pow(data, 2)

    max_dir = block_size * num_direct_pointer
    max_sing = block_size * (num_direct_pointer + num_single_indirect*pow(data, 1))
    max_dou = block_size * (num_direct_pointer + num_single_indirect*pow(data, 1) + num_double_indirect*pow(data, 2))

    if (position < max_dir):
        print ("direct")
        newPos = position
        block = newPos / block_size
        offset = newPos % block_size
        print(block + 1, offset)
    if (position > max_dir and position < max_sing):
        print ("single")
        newPos = position - max_dir
        block = newPos / block_size
        offset = newPos % block_size
        print(block, offset)
        #count from 1, not 0
    if (position > max_sing and position < max_dou):
        print ("double")
        newPos = position - max_sing
        out_block = newPos / block_size
        block_1 = out_block / data
        block_2 = out_block % data
        offset = position % block_size
        print (out_block, block_1, block_2, offset)
    if (position > max_dou and position < maximum_trip):
        print ("triple")
        newPos = position - max_dou
        out_block = newPos / block_size
        block_1 = out_block / pow(data, 2)
        index_1 = out_block % pow(data, 2)
        block_2 = index_1 / data
        block_3 = index_1 % data
        offset = position % block_size
        print (out_block, block_1, block_2, block_3, offset)



UNIX_system()
