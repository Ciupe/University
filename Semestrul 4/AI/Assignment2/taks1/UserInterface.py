from random import randint
import time
import pygame

class UserInterface:
    def __init__(self, controller):
        self.__BLUE = (0, 0, 255)
        self.__GRAYBLUE = (50, 120, 120)
        self.__RED = (255, 0, 0)
        self.__GREEN = (0, 255, 0)
        self.__BLACK = (0, 0, 0)
        self.__WHITE = (255, 255, 255)

        self.__controller = controller

    def displayWithPath(self, image, path1):
        mark1 = pygame.Surface((20, 20))
        mark1.fill(self.__GREEN)
        for move in path1:
            image.blit(mark1, (move[0] * 20, move[1] * 20))
        return image

    def run(self):
        initialX = randint(0, 19)
        initialY = randint(0, 19)
        finalX = randint(0, 19)
        finalY = randint(0, 19)

        self.__controller.getDrone().setValues(initialX, initialY)

        # initialize the pygame module
        pygame.init()

        # load and set the logo
        logo = pygame.image.load("logo32x32.png")
        pygame.display.set_icon(logo)
        pygame.display.set_caption("Path in simple environment")

        # create a surface on screen that has the size of 400 x 480
        screen = pygame.display.set_mode((400, 400))
        screen.fill(self.__WHITE)

        # setup map for A-star
        screen.blit(self.__controller.getDrone().mapWithDrone(self.__controller.getMap().image(), finalX, finalY), (0, 0))
        pygame.display.flip()
        time.sleep(2)

        # run A-star
        start = time.time()
        path1 = self.__controller.searchAStar(initialX, initialY, finalX, finalY)
        finish = time.time()

        # show path
        exetimeAstar = str(finish - start)
        screen.blit(self.displayWithPath(self.__controller.getMap().image(), path1), (0, 0))
        pygame.display.flip()
        time.sleep(3)