"""
Docstring for frontend.code.controllers.gameController

Controller for communicating with the server regarding game actions. 

Ex: Player attack enemy creature.
"""
import pygame
import urllib.request
from models.creature import Creature
from utils.CONSTANTS import SERVER_URL
import json

class gameController:   
    lobby_controller = None
    def __init__(self, lobby_controller):
        self.lobby_controller = lobby_controller

    def spawnCreature(self, creature_type):
        contents = urllib.request.urlopen(SERVER_URL + "/creature/spawn/" + creature_type + "/" + self.lobby_controller.player.name).read()

        # Convert the contents to an Creature class and return it
        creature_data = json.loads(contents.decode('utf-8'))
        print("Spawned Creature Data:", creature_data)
        return Creature(creature_data['name'], creature_data['stats'])
        