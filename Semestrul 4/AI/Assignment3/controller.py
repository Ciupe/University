from repository import *
from parameters import *

class Controller:
    def __init__(self, args):
        # args - list of parameters needed in order to create the controller
        self.repo = Repository()
        self.__runs = runs
        self.__iterations_per_run = iterations_per_run
        self.__population_size = population_size
        self.__individual_size = individual_size
        self.__crossover_probability = crossover_probability
        self.__mutation_probability = mutation_probability

    def iteration(self, args):
        # args - list of parameters needed to run one iteration
        # a iteration:
        # selection of the parents
        # create offsprings by crossover of the parents
        # apply some mutations
        # selection of the survivors
        pass
        
    def run(self, args):
        # args - list of parameters needed in order to run the algorithm
        
        # until stop condition
        #    perform an iteration
        #    save the information need it for the satistics
        
        # return the results and the info for statistics
        pass
    
    
    def solver(self, args):
        # args - list of parameters needed in order to run the solver
        
        # create the population,
        # run the algorithm
        # return the results and the statistics
        pass
       