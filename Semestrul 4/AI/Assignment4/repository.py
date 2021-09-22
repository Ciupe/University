# -*- coding: utf-8 -*-

import pickle
from domain import *


class Repository:
    def __init__(self):
        self.__populations = []
        self.__colonies = []
        self.map = Map()
        self.drone = [0, 0]

    def add_colony(self, number_of_ants, energy):
        self.__colonies.append(Colony(number_of_ants, energy))

    def get_current_colony(self):
        return self.__colonies[-1]

    def get_sensors(self):
        sensors = []
        for i in range(0, self.map.n):
            for j in range(0, self.map.m):
                if self.map.surface[i][j] == 2:
                    s = Sensor(i, j)
                    s.discover_squares(self.map)
                    sensors.append(s)
        return sensors

    def set_drone_positions(self, x, y):
        self.drone = [x, y]

    def random_map(self):
        self.map.randomMap()

    def save_map(self, file_name):
        self.map.saveMap(file_name)

    def load_map(self, file_name):
        self.map.loadMap(file_name)
