from drone import Drone
from map import Map
from repository import Repository
from controller import Controller
from user_interface import UserInterface

if __name__ == '__main__':
    drone = Drone()
    n = 20
    map = Map(n, n)
    repository = Repository(drone, map)
    controller = Controller(repository)
    ui = UserInterface(controller)
    ui.run()
