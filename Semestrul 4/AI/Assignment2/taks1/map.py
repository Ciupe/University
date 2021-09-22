import pickle,pygame,time
from random import random, randint
import numpy as np

class Map:
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))
        self.__values = {}
        self.__resetMap()

        self.__BLUE = (0, 0, 255)
        self.__GRAYBLUE = (50, 120, 120)
        self.__RED = (255, 0, 0)
        self.__GREEN = (0, 255, 0)
        self.__BLACK = (0, 0, 0)
        self.__WHITE = (255, 255, 255)

    def __resetMap(self):
        self.loadMap()
        for key in self.__values.keys():
            self.__values[key] = (-1, -1)

    def isEmpty(self, x, y):
        return (x, y) in self.__values.keys() and self.__values[(x, y)] == (-1, -1) and self.surface[x][y] == 0

    def markVisited(self, x, y, lastX, lastY):
        self.__values[(x, y)] = (lastX, lastY)
        self.image()

    def getValue(self, x, y):
        return self.__values[(x, y)]

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill:
                    self.surface[i][j] = 1

    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string

    def saveMap(self, numFile="test.map"):
        with open(numFile, 'wb') as f:
            pickle.dump(self, f)
            f.close()

    def markVisited(self, x, y, lastX, lastY):
        self.__last[(x, y)] = (lastX, lastY)
        self.image()

    def loadMap(self, numFile="test.map"):
        with open(numFile, "rb") as f:
            dummy = pickle.load(f)
            self.n = dummy.n
            self.m = dummy.m
            self.surface = dummy.surface
            f.close()

    def image(self):
        imagine = pygame.Surface((400, 400))
        brick = pygame.Surface((20, 20))
        brick.fill(self.__BLUE)
        imagine.fill(self.__WHITE)
        for i in range(self.n):
            for j in range(self.m):
                if self.surface[i][j] == 1:
                    imagine.blit(brick, (j * 20, i * 20))

        return imagine


