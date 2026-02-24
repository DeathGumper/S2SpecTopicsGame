"""
Docstring for frontend.code.controllers.lobbyController

Controller for communicating with the server regarding lobby actions. 

Ex: Joining/Creating a lobby
"""

import pygame
import urllib.request
import websockets
import asyncio
from dacite import from_dict

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
                    dataDict = json.loads(response.read().decode('utf-8'))
                    lobbyingDto = from_dict(data_class=LobbyingDto, data=dataDict)
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
    
    def joinLobby(self, playerName: str, lobbyId: str):
        try:
            with urllib.request.urlopen(SERVER_URL + "/lobby/join/" + lobbyId + "/" + playerName) as response:
                if 200 <= response.code < 300:
                    dataDict = json.loads(response.read().decode('utf-8'))
                    lobbyingDto = from_dict(data_class=LobbyingDto, data=dataDict)
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
        try:
            with urllib.request.urlopen(SERVER_URL + "/lobby/startGame/" + self.lobbyState.id + "/" + self.player.id) as response:
                if 200 <= response.code < 300:
                    responseLoaded = json.loads(response.read().decode('utf-8'))
                    print(responseLoaded)
                    return responseLoaded
                return response.code
        except urllib.error.HTTPError as e:
            print(f"HTTP Error: Status code {e.code} - {e.reason}")
            return False
        except Exception as e:
            print(str(e))
            return False
        
    def establishWebsocket(self):
        try:
            with urllib.request.urlopen(SERVER_URL + "/hello") as response:
                if 200 <= response.code < 300:
                    print("Websocket established successfully.")
                    return True
                return response.code
        except urllib.error.HTTPError as e:
            print(f"HTTP Error: Status code {e.code} - {e.reason}")
            return False
        except Exception as e:
            print(str(e))
            return False
        
    async def hello(self):
        # The URI scheme for WebSockets is ws:// for standard or wss:// for secure connections
        uri = "ws://localhost:7831/hello" 
        try:
            # The async with statement handles connection and cleanup automatically
            async with websockets.connect(uri) as websocket:
                print(f"✅ Connected to server at {uri}!")

                # Send a message
                name = "Hello, Server!"
                await websocket.send(name)
                print(f"> Sent: {name}")

                # Receive a response
                greeting = await websocket.recv()
                print(f"< Received: {greeting}")

        except ConnectionRefusedError:
            print("❌ Could not connect to server. Is the server running?")
        except Exception as e:
            print(f"An error occurred: {e}")
            
lobbyController = LobbyController()