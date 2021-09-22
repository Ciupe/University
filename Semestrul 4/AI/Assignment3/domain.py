# -*- coding: utf-8 -*-
import pickle
from random import *
from utils import *
import numpy as np


# the class gene can be replaced with int or float, or other types
# depending on your problem's representation

class Gene:
    def __init__(self):
        # random initialise the gene according to the representation
        self.adjacent = randint(1, 4)  # N, E, S, V
        pass


class Drone:
    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y

    def setXY(self, x, y):
        self.x = x
        self.y = y


class Individual:
    def __init__(self, size=0):
        self.__size = size
        self.__x = [Gene() for i in range(self.__size)]
        self.__f = None

    def fitness(self, map, drone):
        # compute the fitness for the individual
        # and save it in self.__f

        path = self.computePath(drone)
        checked = []

        for p in path:
            if 0 <= p[0] < map.n and 0 <= p[1] < map.n and map.surface[p[0], [1]] != 1:
                if p not in checked:
                    checked.append(p)
                    self.__f += 1

                aux = p

                for direction in v:
                    while 0 <= aux[0] < map.n and 0 <= aux[1] < map.n and map.surface[aux[0], aux[1]] != 1:
                        aux[0] += direction[0]
                        aux[1] += direction[1]
                        if aux not in checked:
                            checked.append(aux)
                        self.__f += 1
                    aux = p

            else:
                break
        pass

    def computePath(self, drone):
        path = [[drone.x, drone.y]]
        for coord in self.__x:
            if coord == 1:
                path.append([path[-1][0], path[-1][1] - 1])
            if coord == 2:
                path.append([path[-1][0] + 1, path[-1][1]])
            if coord == 3:
                path.append([path[-1][0], path[-1][1] + 1])
            if coord == 4:
                path.append([path[-1][0] - 1, path[-1][1]])

        return path

    def mutate(self, mutateProbability=0.04):
        # perform a mutation with respect to the representation
        if random() < mutateProbability:
            self.__x[randint(0, self.__size - 1)] = Gene()

    def crossover(self, otherParent, crossoverProbability=0.8):
        # perform the crossover between the self and the otherParent
        offspring1, offspring2 = Individual(self.__size), Individual(self.__size)
        if random() < crossoverProbability:
            index = randint(0, self.__size - 1)
            for i in range(index + 1):  # 0-4
                offspring1.__x[i] = self.__x[i]
                offspring2.__x[i] = otherParent.__x[i]

            for i in range(self.__size - index):  # 0-15 + index
                offspring1.__x[index + i] = otherParent.__x[index + i]
                offspring2.__x[index + i] = self.__x[index + i]

        return offspring1, offspring2


class Population:
    def __init__(self, populationSize=0, individualSize=0):
        self.__populationSize = populationSize
        self.__individuals = [Individual(individualSize) for _ in range(populationSize)]

    def evaluate(self, map, drone):
        # evaluates the population
        for x in self.__individuals:
            x.fitness(map, drone)

    def selection(self, k=0):
        # perform a selection of k individuals from the population
        # and returns that selection
        sorted_population = sorted(self.__individuals, key=lambda individual: individual.fitness, reverse=True)

        selection = []
        for index in range(k):
            selection[index].append(sorted_population[index])

        return selection


class Map():
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))

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

    def loadMap(self, numfile="test.map"):
        with open(numfile, "rb") as f:
            dummy = pickle.load(f)
            self.n = dummy.n
            self.m = dummy.m
            self.surface = dummy.surface
            f.close()
