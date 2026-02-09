"""
Docstring for frontend.code.controllers.lobbyController

Controller for communicating with the server regarding lobby actions. 

Ex: Joining/Creating a lobby
"""

import pygame
import urllib.request

from models.lobbyState import LobbyState
from models.creature import Creature
from utils.CONSTANTS import SERVER_URL
from models.player import Player
import json

class lobbyController:
    player = None
    def makePlayer(self, name):
        contents = urllib.request.urlopen(SERVER_URL + "/lobby/makePlayer/" + name).read()
        playerJson = json.loads(contents.decode('utf-8'))
        self.player = Player(playerJson['name'], playerJson['creatures'])
        return self.player

    def createLobby(self, lobby_id, name):
        self.makePlayer(name)
        contents = urllib.request.urlopen(SERVER_URL + "/lobby/create/" + lobby_id + "/" + self.player.name).read()
        lobby = json.loads(contents.decode('utf-8'))
        print("Created Lobby Data:", lobby)
        return LobbyState(lobby['id'], lobby['players']) 
    
    def joinLobby(self, lobby_id, name):
        self.makePlayer(name)
        contents = urllib.request.urlopen(SERVER_URL + "/lobby/join/" + lobby_id + "/" + self.player.name).read()
        lobby = json.loads(contents.decode('utf-8'))
        return LobbyState(lobby['id'], lobby['players'])