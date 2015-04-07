class Cup
    def initialize (le_size, le_content)
        @size, @content = le_size, le_content
    end

    def pouring_into(other_cup)
        if !@is_empty() && !other_cup.is_full()
            while !other_cup.is_full() && !@is_empty()
                other_cup.content--
                @content++
            end
        end
    end


    def is_empty ()
        return @content == 0
    end

    def is_full ()
        return @content == @size
    end

    def size_of()
        return @size
    end

    def current_is()
        return @content
    end 
end

class Sitch
    def initialize (biggest, middle, tiniest)
        
    end
end

class Pouring
    def water_algorithm (biggest=10, middle=7, tiniest=4)
        # are there 2 pints in mid or petite?
        finished = false

        # initialize cups, assuming the two smaller cups are full
        large = Cup.new(biggest, 0)
        mid = Cup.new(middle, middle)
        petite = Cup.new(tiniest, tiniest)


    end
end
