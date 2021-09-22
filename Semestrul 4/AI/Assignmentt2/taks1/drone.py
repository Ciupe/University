import pygame


class Drone():
    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y
        self.__RED = (255, 0, 0)

    def setXY(self, x, y):
        self.x = x
        self.y = y

    def mapWithDrone(self, mapImage, x, y):
        visited = pygame.Surface((20, 20))
        visited.fill(self.__RED)
        drona = pygame.image.load("drona.png")
        mapImage.blit(drona, (self.x * 20, self.y * 20))
        mapImage.blit(visited, (x * 20, y * 20))

        return mapImage