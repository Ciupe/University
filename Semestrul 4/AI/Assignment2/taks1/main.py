

# import the pygame module, so you can use it
from drone import *
from map import *
from pathfinding import *
import pickle,pygame,time
from pygame.locals import *
from random import random, randint
from UserInterface import *
'''
# define a main function
def main():

    # we create the map
    m = Map()
    #m.randomMap()
    #m.saveMap("test2.map")
    m.loadMap("test.map")


    # initialize the pygame module
    pygame.init()
    # load and set the logo
    logo = pygame.image.load("logo32x32.png")
    pygame.display.set_icon(logo)
    pygame.display.set_caption("Path in simple environment")

    # we position the drone somewhere in the area
    x = randint(0, 19)
    y = randint(0, 19)

    #create drona
    d = Drone(x, y)



    # create a surface on screen that has the size of 400 x 480
    screen = pygame.display.set_mode((400,400))
    screen.fill(WHITE)

    # run A-star
    st = time.time()
    path1 = self.__service.searchAStar(initialX, initialY, finalX, finalY)
    fn = time.time()

    # define a variable to control the main loop
    running = True

    # main loop
    while running:
        # event handling, gets all event from the event queue
        for event in pygame.event.get():
            # only do something if the event is of type QUIT
            if event.type == pygame.QUIT:
                # change the value to False, to exit the main loop
                running = False

            if event.type == KEYDOWN:
                d.move(m) #this call will be erased


        screen.blit(d.mapWithDrone(m.image()),(0,0))
        pygame.display.flip()

    #path = dummysearch()
    #screen.blit(displayWithPath(m.image(), path),(0,0))

    pygame.display.flip()
    time.sleep(5)
    pygame.quit()

'''

# run the main function only if this module is executed as the main script
# (if you import this as a module then nothing is executed)
if __name__=="__main__":
    drone = Drone()
    # n = int(input('map side dimension:'))
    n = 20
    map = Map(n, n)
    repository = Repository(drone, map)
    controller = Pathfinding(repository)
    app = UserInterface(controller)
    app.run()