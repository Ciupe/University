import random
import time
import pygame


class UserInterface():
    def __init__(self, service):
        self.__service = service
        self.__BLUE = (0, 0, 255)
        self.__GRAYBLUE = (50, 120, 120)
        self.__RED = (255, 0, 0)
        self.__GREEN = (0, 255, 0)
        self.__BLACK = (0, 0, 0)
        self.__WHITE = (255, 255, 255)

    def showPath(self, image, path1):
        mark1 = pygame.Surface((20, 20))
        mark1.fill(self.__GREEN)
        for move in path1:
            image.blit(mark1, (move[0] * 20, move[1] * 20))
        return image

    def run(self):
        initialX = 0
        initialY = 0
        finalX = 13
        finalY = 16

        self.__service.getDrone().setXY(initialX, initialY)

        pygame.init()
        logo = pygame.image.load("logo32x32.png")
        pygame.display.set_icon(logo)
        pygame.display.set_caption("Path in simple environment")
        screen = pygame.display.set_mode(self.__service.getMap().getMapScale())
        screen.fill(self.__WHITE)

        # A Star
        screen.blit(self.__service.getDrone().mapWithDrone(self.__service.getMap().image(), finalX, finalY), (0, 0))
        pygame.display.flip()
        time.sleep(2)

        start = time.time()
        path1 = self.__service.searchAStar(initialX, initialY, finalX, finalY)
        finish = time.time()
        a_star_execution_time = str(finish - start)

        screen.blit(self.showPath(self.__service.getMap().image(), path1), (0, 0))
        pygame.display.flip()
        time.sleep(3)

        # GREEDY
        self.__service.getMap().resetMap()
        screen.blit(self.__service.getDrone().mapWithDrone(self.__service.getMap().image(), finalX, finalY), (0, 0))
        pygame.display.flip()
        time.sleep(2)

        start = time.time()
        path2 = self.__service.searchGreedy(initialX, initialY, finalX, finalY)
        finish = time.time()
        greedy_execution_time = str(finish - start)

        screen.blit(self.showPath(self.__service.getMap().image(), path2), (0, 0))
        pygame.display.flip()
        time.sleep(3)

        print("\nA* execution time: " + a_star_execution_time)
        print("\nGreedy execution time: " + greedy_execution_time)
