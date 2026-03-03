from dataclasses import dataclass

@dataclass
class ClientMessage:
    type: str
    payload: object

    def __init__(self, type: str, payload: object):
        self.type = type
        self.payload = payload