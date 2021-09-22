# -*- coding: utf-8 -*-
import copy
import pickle
from random import *

import pygame

from utils import *
import numpy as np


# the class gene can be replaced with int or float, or other types
# depending on your problem's representation

class Ant:
    def __init__(self, energy):
        self.energy = energy
        self.path = []
        self.visited_sensors = [0]
        self.current_sensor = 0
        self.squares_visited_by_sensors = {}

    def add_path(self, path):
        for p in path:
            self.path.append(p)

    def fitness(self):
        return sum(self.squares_visited_by_sensors.values())


class Colony:
    def __init__(self, number_of_ants, energy):
        self.number_of_ants = number_of_ants
        self.ants = [Ant(energy) for _ in range(number_of_ants)]

    def reset_ants(self, energy):
        self.ants = [Ant(energy) for _ in range(self.number_of_ants)]

    def get_ants(self):
        return self.ants


class Sensor:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.squares_discovered = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 0}
        self.optimal_energy = 0

    def get_optimal_energy(self):
        maxim = -1
        for key, value in self.squares_discovered.items():
            if value > maxim:
                self.optimal_energy = key
                maxim = value
        return self.optimal_energy

    def discover_squares(self, currentMap):
        v = [[-1, 0], [0, 1], [1, 0], [0, -1]]
        for energy in range(1, 6):
            for direction in v:
                aux = energy
                x = self.x + direction[0]
                y = self.y + direction[1]
                while True:
                    if aux == 0 or x < 0 or x >= currentMap.n or y < 0 or y >= currentMap.m:
                        break
                    aux -= 1
                    self.squares_discovered[energy] += 1
                    x = x + direction[0]
                    y = y + direction[1]


class Map:
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))

    def randomMap(self, fill=0.2, total=5):
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

    def loadMap(self, numFile):
        with open(numFile, "rb") as f:
            dummy = pickle.load(f)
            self.n = dummy.n
            self.m = dummy.m
            self.surface = dummy.surface
            f.close()

    def image(self, colour=BLUE, background=WHITE):
        imagine = pygame.Surface((400, 400))
        brick = pygame.Surface((20, 20))
        sensor = pygame.Surface((20, 20))

        brick.fill(BLUE)
        imagine.fill(WHITE)
        sensor.fill(RED)

        for i in range(self.n):
            for j in range(self.m):
                if self.surface[i][j] == 1:
                    imagine.blit(brick, (j * 20, i * 20))
                if self.surface[i][j] == 2:
                    imagine.blit(sensor, (j * 20, i * 20))

        return imagine