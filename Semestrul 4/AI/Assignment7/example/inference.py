import numpy
import torch

import myModel

# we load the model

filepath = "myNetwork.pt"
ann = myModel.Net(2, 10, 1)

ann.load_state_dict(torch.load(filepath))
ann.eval()

# visualise the parameters for the ann (aka weights and biases)
# for name, param in ann.named_parameters():
#     if param.requires_grad:
#         print (name, param.data)


x1 = float(input("x1 = "))
x2 = float(input("x2 = "))
x = torch.tensor([x1, x2])
print("trained network result: " + str(ann(x).tolist()[0]))
print("correct result: " + str(numpy.sin(x1 + (x2 / numpy.pi))))
