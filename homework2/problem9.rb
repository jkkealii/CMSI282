def lcm(x, y)
        return lcm2(x, y, x, y)
end

def lcm2(x, y, a, b)
        if (a == b) then
                return a
        end
        if (a < b) then
                return lcm2(x, y, a+x, b)
        end
        if (a > b) then
                return lcm2(x, y, a, b+y)
        end
end