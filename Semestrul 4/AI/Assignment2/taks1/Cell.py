class Cell:
    def __init__ (self, x, y, finalX, finalY, cost = 0):
        self.x = x
        self.y = y
        self.cost = cost
        self.__computeDistance(finalX, finalY)

    def __computeDistance(self, finalX, finalY):
        self.distance = abs(self.x - finalX) + abs(self.y - finalY)

    def __lt__(self, cell2):
        if self.cost + self.distance == cell2.cost + cell2.distance:
            return self.cost < cell2.cost
        return self.cost + self.distance < cell2.cost + cell2.distance

