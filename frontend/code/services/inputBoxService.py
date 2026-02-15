import pygame

from services.uiService import UIService

class InputBoxService:
    inputBoxes = None
    selectedInputBox = None
    def __init__(self):
        self.inputBoxes = []

    def addNew(self, position, size, color, defaultText):
        inputBox = UIService.makeInputBox(position, size, color, defaultText)
        self.inputBoxes.append(inputBox)
        return inputBox
    

    def keyPressed(self, key: str):
        if not self.selectedInputBox:
            return
        if key == pygame.K_BACKSPACE:
            self.selectedInputBox.removeLetter()
            return
        
        letter = pygame.key.name(key)
        if len(letter) > 1:
            return
        self.selectedInputBox.addLetter(letter)
    
    def render(self, surface: pygame.Surface):
        for inputBox in self.inputBoxes:
            inputBox.render(surface)

    def handleInputBoxes(self):
        self.selectedInputBox = None
        for inputBox in self.inputBoxes:
            inputBox.checkClick()
            if inputBox.wasClickedOnLastClick:
                self.selectedInputBox = inputBox

        # print(self.selectedInputBox)

        #if self.selectedInputBox:
            #self.selectedInputBox.checkTypingInput()
