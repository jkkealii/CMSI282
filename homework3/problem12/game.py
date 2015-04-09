"""
    modified code from github.com/jamescooke/blog/blob/master/content/1501-sketch-water-pouring.rst
"""

import copy
from functools import reduce
from cup import Cup

or_reduction = lambda x, y: x or y
and_reduction = lambda x, y: x and y

class Sitch(object):

    cups = None
    parent = None       # Sitch that created this one
    children = None

    def __init__(self, sizes=None, parent=None):
        self.cups = []

        if sizes is None:
            # Set up cups with default sizes
            sizes = [(10, 0), (7, 7), (4, 4)]

        for cap, cont in sizes:
            self.cups.append(Cup(cap=cap, cont=cont))

        # Save a pointer to the parent
        self.parent = parent

        # Children starts empty
        self.children = []

    def is_goal(self):
        return self.cups[1].is_goal() or self.cups[2].is_goal()
        
    def __eq__(self, g):
        return (
            len(self.cups) == len(g.cups)
            and reduce(
                and_reduction,
                [cup == g.cups[pos] for pos, cup in enumerate(self.cups)]
            )
        )

    def net_has_sitch(self, g):
        return self.top_parent().has_sitch(g)

    def top_parent(self):
        return self if self.parent is None else self.parent.top_parent()

    def has_sitch(self, g):
        return (
            self == g
            or (
                len(self.children) > 0
                and reduce(
                    or_reduction,
                    [sitch.has_sitch(g) for sitch in self.children]
                )
            )
        )

    def make_sitch(self, c_a, c_b):
        new_sitch = copy.deepcopy(self)
        new_sitch.parent = self
        (new_sitch.cups[c_a],
         new_sitch.cups[c_b]) = new_sitch.cups[c_a].pour_into(new_sitch.cups[c_b])
        return new_sitch

    def make_children(self):
        for c_a in range(len(self.cups)):
            for c_b in range(len(self.cups)):
                if c_b == c_a:
                    continue
                new_sitch = self.make_sitch(c_a, c_b)
                if not self.net_has_sitch(new_sitch):
                    self.children.append(new_sitch)
        return len(self.children)

    def is_solvable(self):
        if self.is_goal():
            self.print_trace()
            return True

        if self.make_children() == 0:
            return False

        return self.solvable_child()

    def solvable_child(self):
        for child in self.children:
            if child.is_solvable():
                return True

        return False

    def print_trace(self):
        if self.parent is not None:
            self.parent.print_trace()
        print(self.cups)

test = Sitch()
print test.is_solvable()