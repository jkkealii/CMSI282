"""
    modified code from github.com/jamescooke/blog/blob/master/content/1501-sketch-water-pouring.rst
"""

GOAL = 2

class Cup(object):

    capacity = 1
    contents = 0

    def __init__(self, cap=1, cont=0):
        if cap < cont:
            raise ValueError('Too much contents')
        if cont < 0:
            raise ValueError('Negative contents')
        self.capacity = cap
        self.contents = cont

    @property
    def space(self):
        return self.capacity - self.contents

    def is_goal(self):
        return self.contents == GOAL

    def __eq__(self, c):
        return self.capacity == c.capacity and self.contents == c.contents

    def pour_into(self, other_cup):
        return (
            Cup(
                cap=self.capacity,
                cont=self.contents-min(self.contents, other_cup.space)
            ),
            Cup(
                cap=other_cup.capacity,
                cont=other_cup.contents+min(self.contents, other_cup.space)
            )
        )

    def __repr__(self):
        return "<Cup {}:{}>".format(self.contents, self.capacity)