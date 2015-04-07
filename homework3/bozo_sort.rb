def bozo_sort (before)
    result = before
    def shift (switcher, switchee, result)
        hold = result[switcher]
        result[switcher] = result[switchee]
        result[switchee] = hold
    end

    def unsorted (result)
        done = false
        if result != result.sort
            done = true
        end
        return done
    end

    while unsorted (result)
        chosen1 = rand(result.length)
        chosen2 = rand(result.length)
        shift(chosen1, chosen2, result)
    end
    return result
end

tests = []

while tests.length < 21
    testing_array = [9, 83, 7, 57, 12, 0, 52, 89, 37, 10, 11]
    beginning = Time.now
    bozo_sort(testing_array)
    fin = Time.now
    time_elapsed = fin - beginning
    tests.push(time_elapsed)
    sum = tests.reduce :+
end

average = sum/tests.length

puts average
puts tests.to_s