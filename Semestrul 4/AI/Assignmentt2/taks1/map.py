import pickle
from random import random

import numpy as np
import pygame


class Map():
    def __init__(self, n, m):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))
        # Creating some colors
        self.__BLUE = (0, 0, 255)
        self.__GRAYBLUE = (50, 120, 120)
        self.__RED = (255, 0, 0)
        self.__GREEN = (0, 255, 0)
        self.__BLACK = (0, 0, 0)
        self.__WHITE = (255, 255, 255)
        self.values = {}
        for i in range(n):
            for j in range(m):
                self.values[(i, j)] = (-1, -1)
        self.loadMap()

    def getMapScale(self):
        return self.n * 20, self.m * 20

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill:
                    self.surface[i][j] = 1

    def isAvailable(self, x, y):
        return (x, y) in self.values.keys() and self.values[(x, y)] == (-1, -1) and self.surface[x][y] == 0

    def markVisited(self, x, y, lastX, lastY):
        self.values[(x, y)] = (lastX, lastY)
        self.image()

    def getCellValue(self, x, y):
        return self.values[(x, y)]

    def resetMap(self):
        self.loadMap()
        for key in self.values.keys():
            self.values[key] = (-1, -1)


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

    def loadMap(self, numfile="test.map"):
        with open(numfile, "rb") as f:
            dummy = pickle.load(f)
            self.n = dummy.n
            self.m = dummy.m
            self.surface = dummy.surface
            f.close()

    def image(self):
        imagine = pygame.Surface((20 * self.n, 20 * self.m))
        brick = pygame.Surface((self.n, self.m))
        brick.fill(self.__BLUE)
        imagine.fill(self.__WHITE)
        for i in range(self.n):
            for j in range(self.m):
                if self.surface[i][j] == 1:
                    imagine.blit(brick, (i * 20, j * 20))

        return imagine