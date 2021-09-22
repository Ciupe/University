class Cell():
    def __init__(self, x, y, finalX, finalY, cost=0):
        self.x = x
        self.y = y
        self.cost = cost
        self.computeDistance(finalX, finalY)

    def computeDistance(self, finalX, finalY):
        self.distance = abs(finalX - self.x) + abs(finalY - self.y)

    def __lt__(self, cell2):
        if self.cost + self.distance == cell2.cost + cell2.distance:
            return self.cost < cell2.cost
        return self.cost + self.distance < cell2.cost + cell2.distance