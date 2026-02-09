import pygame

from visuals.button import Button
from visuals.inputBox import InputBox

class UIService:
    buttons = []
    inputBoxes = []
    def __init__(self):
        pass

    def makeButton(self, label, action, position=(0, 0), size=None):
        button = Button(label, action, position, size)
        self.buttons.append(button)
        return button
    
    def makeInputBox(self, position=(0, 0), size=(100, 30)):
        inputBox = InputBox(position[0], position[1], size[0], size[1])
        self.inputBoxes.append(inputBox) # Add the input box to the inputBoxes list for click checking and rendering
        return inputBox

    
    def checkClicks(self):
        for button in self.buttons:
            button.check_click()
        for inputBox in self.inputBoxes:
            for event in pygame.event.get():
                inputBox.handle_event(event) # Pass the event to the input box for handling
            button.check_click()

    def render(self, surface):
        for button in self.buttons:
            button.render(surface)
        for inputBox in self.inputBoxes:
            inputBox.render(surface)