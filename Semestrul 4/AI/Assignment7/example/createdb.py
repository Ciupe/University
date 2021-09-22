import torch
import numpy


def create_training_database():
    min = -10
    max = 10

    # create distribution of 1000 random pairs in range [-10, 10)
    pairs = (max - min) * torch.rand(1000, 2) + min

    l1: list[float] = [pair[0] for pair in pairs]
    l2: list[float] = [pair[1] for pair in pairs]

    l1: torch.Tensor = torch.tensor(l1)
    l2: torch.Tensor = torch.tensor(l2)

    function_results = torch.sin(l1 + (l2 / numpy.pi))

    # 3-dimensional tensor: (l1, l2, result)
    pairs_with_results = torch.column_stack((pairs, function_results))

    torch.save(pairs_with_results, "mydataset.dat")


create_training_database()
