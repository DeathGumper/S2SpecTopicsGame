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
from dto.lobbyingDto import LobbyingDto
import json

class LobbyController:
    player = None
    lobbyState = None

    def createLobby(self, playerName: str, lobbyId: str):
        try:
            with urllib.request.urlopen(SERVER_URL + "/lobby/create/" + lobbyId + "/" + playerName) as response:
                if 200 <= response.code < 300:
                    responseLoaded = json.loads(response.read().decode('utf-8'))
                    lobbyingDto = LobbyingDto(responseLoaded['lobbyState'], responseLoaded['player']) 
                    self.lobbyState = lobbyingDto.lobbyState
                    self.player = lobbyingDto.player
                    return True
                return response.code
        except urllib.error.HTTPError as e:
            print(f"HTTP Error: Status code {e.code} - {e.reason}")
            return False
        except Exception as e: 
            print(str(e))
            return False
    
    def joinLobby(self, playerName: str, lobbyId: str):
        try:
            with urllib.request.urlopen(SERVER_URL + "/lobby/join/" + lobbyId + "/" + playerName) as response:
                if 200 <= response.code < 300:
                    responseLoaded = json.loads(response.read().decode('utf-8'))
                    lobbyingDto = LobbyingDto(responseLoaded['lobbyState'], responseLoaded['player']) 
                    self.lobbyState = lobbyingDto.lobbyState
                    self.player = lobbyingDto.player

                    print(lobbyingDto)
                    print(self.lobbyState)
                    print(self.player)
                    return True
                return response.code
        except urllib.error.HTTPError as e:
            print(f"HTTP Error: Status code {e.code} - {e.reason}")
            return False
        except Exception as e:
            print(str(e))
            return False
        
    def startLobby(self):
        pass
    
lobbyController = LobbyController()