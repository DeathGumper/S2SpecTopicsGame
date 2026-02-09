class Creature:
    def __init__(self, name, stats):
        self.name = name
        self.stats = stats

    def __repr__(self):
        return f"Creature(name={self.name}, stats={self.stats})"