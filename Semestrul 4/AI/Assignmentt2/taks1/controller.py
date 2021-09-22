import heapq
import time

from Cell import Cell


class Controller():
    def __init__(self, repo):
        self.__UP = 0
        self.__DOWN = 2
        self.__LEFT = 1
        self.__RIGHT = 3
        self.__v = [[-1, 0], [1, 0], [0, 1], [0, -1]]

        self.__repository = repo

    def getPath(self, initialX, initialY, finalX, finalY, pq):
        if len(pq) == 0:
            return

        # reconstructing  path
        path = [(finalX, finalY)]
        while finalX != initialX or finalY != initialY:
            (finalX, finalY) = self.__repository.getMap().getCellValue(finalX, finalY)
            path.append((finalX, finalY))

        return reversed(path)

    def searchAStar(self, initialX, initialY, finalX, finalY):
        pq = []
        cell = Cell(initialX, initialY, finalX, finalY, 0)
        heapq.heappush(pq, cell)
        while len(pq) > 0 and (pq[0].x, pq[0].y) != (finalX, finalY):
            cell = heapq.heappop(pq)
            for dir in self.__v:
                x = cell.x + dir[0]
                y = cell.y + dir[1]
                if self.__repository.getMap().isAvailable(x, y):
                    new_cell = Cell(x, y, finalX, finalY, cell.cost + 1)
                    heapq.heappush(pq, new_cell)
                    self.__repository.getMap().markVisited(x, y, cell.x, cell.y)

        return self.getPath(initialX, initialY, finalX, finalY, pq)

    def searchGreedy(self, initialX, initialY, finalX, finalY):
        pq = []
        cell = Cell(initialX, initialY, finalX, finalY)
        heapq.heappush(pq, cell)
        while len(pq) > 0 and (pq[0].x, pq[0].y) != (finalX, finalY):
            cell = heapq.heappop(pq)
            for dir in self.__v:
                x = cell.x + dir[0]
                y = cell.y + dir[1]
                if self.__repository.getMap().isAvailable(x, y):
                    new_cell = Cell(x, y, finalX, finalY)
                    heapq.heappush(pq, new_cell)
                    self.__repository.getMap().markVisited(x, y, cell.x, cell.y)

        return self.getPath(initialX, initialY, finalX, finalY, pq)

    def getDrone(self):
        return self.__repository.getDrone()

    def getMap(self):
        return self.__repository.getMap()
