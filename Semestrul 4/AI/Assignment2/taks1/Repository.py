class Repository:
    def __init__(self, drone, map):
        self.drone = drone
        self.map = map

    def getDrone(self):
        return self.drone

    def getMap(self):
        return self.map
