import pygame
import copy

class Visual:
    def __deepcopy__(self, memo):
        # 1. Create a new instance of the class
        cls = self.__class__
        result = cls.__new__(cls)
        memo[id(self)] = result

        # 2. Manually copy each attribute
        for key, value in self.__dict__.items():
            if isinstance(value, pygame.Surface):
                # Use Pygame's native copy for surfaces
                setattr(result, key, value.copy())
            else:
                # Use standard deepcopy for everything else
                setattr(result, key, copy.deepcopy(value, memo))
        
        return result