import torch


class Net(torch.nn.Module):
    # the class for the network

    def __init__(self, input_dimension, hidden_dimension, output_dimension):
        # we have two layers: a hidden one and an output one
        super(Net, self).__init__()
        """
            torch.nn.ReLU (Rectified Linear Unit)
        - given an input x, ReLU will take the maximal value between 0 and x.

            torch.nn.Linear
        - applies a linear transformation to the incoming data: y = x*A^T + b
        """
        self.hidden = torch.nn.Linear(input_dimension, hidden_dimension)
        self.output = torch.nn.Sequential(
            torch.nn.Linear(input_dimension, hidden_dimension), torch.nn.ReLU(),
            torch.nn.Linear(hidden_dimension, hidden_dimension), torch.nn.ReLU(),
            torch.nn.Linear(hidden_dimension, output_dimension)
        )

    def forward(self, x):
        # a function that implements the forward propagation of the signal
        # observe the refu function applied on the output of the hidden layer
        return self.output(x)
