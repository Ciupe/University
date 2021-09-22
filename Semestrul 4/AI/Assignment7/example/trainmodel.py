import matplotlib.pyplot as plot
import torch

from example import myModel


def train_model():
    data = torch.load("mydataset.dat")

    pairs = torch.empty(0, 2)
    results = torch.empty(0, 1)
    for record in data:
        pair = torch.tensor([record[0], record[1]])
        pairs = torch.vstack((pairs, pair))
        results = torch.vstack((results, record[2]))

    # we set up the lossFunction as the mean square error
    lossFunction = torch.nn.MSELoss()

    # we create the ANN
    ann = myModel.Net(input_dimension=2, hidden_dimension=100, output_dimension=1)

    print(ann)
    # we use an optimizer that implements stochastic gradient descent
    batch_optimizer = torch.optim.SGD(ann.parameters(), lr=0.02)

    # we memorize the losses for some graphics
    loss_list = []

    # we set up the environment for training in batches
    batch_size = 256
    n_batches = int(len(pairs) / batch_size)
    print(n_batches)

    for epoch in range(10000):
        for batch in range(n_batches):
            # we prepare the current batch  -- please observe the slicing for tensors
            pair_batch = pairs[batch * batch_size:(batch + 1) * batch_size]
            result_batch = results[batch * batch_size:(batch + 1) * batch_size]

            # we compute the output for this batch
            prediction = ann(pair_batch)

            # we compute the loss for this batch
            loss = lossFunction(prediction, result_batch)

            # we save it for graphics
            loss_list.append(loss.item())

            # we set up the gradients for the weights to zero (important in pytorch)
            batch_optimizer.zero_grad()

            # we compute automatically the variation for each weight (and bias) of the network
            loss.backward()

            # we compute the new values for the weights
            batch_optimizer.step()

        if epoch % 500 == 0:
            y_pred = ann(pairs)
            loss = lossFunction(y_pred, results)
            print('\repoch: {}\tLoss =  {:.5f}'.format(epoch, loss.item()))

        # Specify a path
    filepath = "myNetwork.pt"

    # save the model to file
    torch.save(ann.state_dict(), filepath)

    # visualise the parameters for the ann (aka weights and biases)
    for name, param in ann.named_parameters():
        if param.requires_grad:
            print(name, param.data)

    plot.plot(loss_list)
    plot.xlabel('Iteration')
    plot.ylabel('Loss')
    plot.show()


train_model()
