import heapq
import pickle, pygame, time
from map import *
from heapq import *
from math import sqrt
from Cell import *
from Repository import *
from map import *


class Pathfinding:

    def __init__(self, repo):
        # define directions
        self.repository = repo
        self.UP = 0
        self.DOWN = 2
        self.LEFT = 1
        self.RIGHT = 3

        # define indexes variations
        self.v = [[-1, 0], [1, 0], [0, 1], [0, -1]]

    def getPath(self, initialX, initialY, finalX, finalY, pq):
        if len(pq) == 0:
            return "Destination unreachable..."

        # reconstructing path
        path = [(finalX, finalY)]
        while finalX != initialX or finalY != initialY:
            (finalX, finalY) = self.repository.getMap().getValue(finalX, finalY)
            path.append((finalX, finalY))

        return reversed(path)

    def searchAStar(self, initialX, initialY, finalX, finalY):
        heap = []
        cell = Cell(initialX, initialY, finalX, finalY)
        heapq.heappush(heap, cell)
        while len(heap) > 0 and (heap[0].x != finalX or heap[0].y != finalY):
            cell = heapq.heappop(heap)
            for dir in self.v:
                x = cell.x + dir[0]
                y = cell.y + dir[1]
                if self.repository.getMap().isEmpty(x, y):
                    new_cell = Cell(x, y, finalX, finalY, cell.cost + 1)
                    heapq.heappush(heap, new_cell)
                    self.repository.getMap().markVisited(x, y, cell.x, cell.y)

        return self.getPath(initialX, initialY, finalX, finalY, heap)

    def searchGreedy(self, mapM, droneD, initialX, initialY, finalX, finalY):
        pq = []
        cell = Cell(initialX, initialY, finalX, finalY)
        heapq.heappush(pq, cell)
        while len(pq) > 0 and (pq[0].x != finalX or pq[0].y != finalY):
            cell = heapq.heappop(pq)
            for dir in self.v:
                x = cell.x + dir[0]
                y = cell.y + dir[1]
                if self.repository.getMap().isEmpty(x, y):
                    new_cell = Cell(x, y, finalX, finalY)
                    heapq.heappush(pq, new_cell)
                    self.repository.getMap().markVisited(x, y, cell.x, cell.y)

        return self.getPath(initialX, initialY, finalX, finalY, pq)

    def dummysearch(self):
        # example of some path in test1.map from [5,7] to [7,11]
        return [[5, 7], [5, 8], [5, 9], [5, 10], [5, 11], [6, 11], [7, 11]]

    def displayWithPath(self, image, path):
        mark = pygame.Surface((20, 20))
        mark.fill(GREEN)
        for move in path:
            image.blit(mark, (move[1] * 20, move[0] * 20))

        return image

    def getMap(self):
        return self.repository.getMap()

    def getDrone(self):
        return self.repository.getDrone()

