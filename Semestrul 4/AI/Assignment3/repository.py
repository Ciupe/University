# -*- coding: utf-8 -*-

import pickle
from domain import *


class Repository:
    def __init__(self):
         
        self.__populations = []
        self.map = Map()
        self.drone = Drone()
        self.initializeDrone()

    def initializeDrone(self):
        x = randint(0, self.map.n - 1)
        y = randint(0, self.map.m - 1)

        while self.map.surface[x, y] == 1:
            x = randint(0, self.map.n - 1)
            y = randint(0, self.map.m - 1)

        self.drone.x = x
        self.drone.y = y

    def save_map(self, file):
        self.map.saveMap(file)

    def load_map(self, file_name):
        self.map.loadMap(file_name)

    def createPopulation(self, args):
        # args = [populationSize, individualSize] -- you can add more args
        return Population(args[0], args[1])



    # TO DO : add the other components for the repository: 
    #    load and save from file, etc
            