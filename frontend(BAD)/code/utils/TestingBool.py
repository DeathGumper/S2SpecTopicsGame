class TestingBool:
    def __init__(self, value=False):
        self.value = value

    def set(self, value):
        self.value = value

    def get(self):
        return self.value