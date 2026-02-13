"""
Docstring for frontend.code.controllers.gameController

Controller for communicating with the server regarding game actions. 

Ex: Player attack enemy creature.
"""
import pygame
import urllib.request
from models.creature import Creature
from utils.CONSTANTS import SERVER_URL
from controllers.lobbyController import lobbyController
import json

class GameController:
    def __init__(self):
        pass

    def spawnCreature(self, creature_type):
        contents = urllib.request.urlopen(SERVER_URL + "/creature/spawn/" + creature_type + "/" + lobbyController.player.name).read()

        # Convert the contents to an Creature class and return it
        creature_data = json.loads(contents.decode('utf-8'))
        print("Spawned Creature Data:", creature_data)
        return Creature(creature_data['name'], creature_data['stats'])
    
gameController = GameController()
        