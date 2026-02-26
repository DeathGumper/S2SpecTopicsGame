"""
Docstring for frontend.code.controllers.gameController

Controller for communicating with the server regarding game actions. 

Ex: Player attack enemy creature.
"""
import pygame
import websockets
import asyncio

import urllib.request
from models.creature import Creature
from utils.CONSTANTS import SERVER_URL
from controllers.lobbyController import lobbyController
import json

class GameController:
    def __init__(self):
        self.websocket = None
        self.recvTask = None
        self.latestUpdate = None
    
    async def listenForUpdates(self, websocket):
        async for message in websocket:
            print(f"\n[Update from Server]: {message}")
        
    async def connectWebsocket(self):
        # The URI scheme for WebSockets is ws:// for standard or wss:// for secure connections
        uri = "ws://localhost:7831/hello"
        try:
            # The async with statement handles connection and cleanup automatically
            self.websocket = await websockets.connect(uri)
            self.recvTask = asyncio.create_task(self.websocket.recv())

            print("✅ Connected to server websocket for game updates.")

        except ConnectionRefusedError:
            print("❌ Could not connect to server. Is the server running?")
        except Exception as e:
            print(f"An error occurred: {e}")

    async def sayHi(self):
        if self.websocket:
            await self.websocket.send("Hi from the client!")

    async def update(self):
        if not self.recvTask:
            print("Websocket not connected. Call connectWebsocket() first.")
            return

        # ONLY continue if message is ready
        if not self.recvTask.done():
            return

        # If the task finished, check if it errored
        if self.recvTask.exception():
            print("❌ Recv error:", self.recvTask.exception())
            return


        try:
            message = self.recvTask.result()
            print(f"\n[Update from Server]: {message}")
            self.latestUpdate = message
            # Start listening again
            self.recvTask = asyncio.create_task(self.websocket.recv())

        except websockets.ConnectionClosed:
            print("⚠️ WebSocket closed.")
            return

        except Exception as e:
            print("Unexpected error:", e)
            return

            
    
gameController = GameController()
        