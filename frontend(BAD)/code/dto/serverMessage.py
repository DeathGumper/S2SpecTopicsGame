from dataclasses import dataclass

@dataclass
class ServerMessage:
    type: str
    payload: object
    
    def __init__(self, type: str, payload: object=None):
        self.type = type
        self.payload = payload