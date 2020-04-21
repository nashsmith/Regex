CFG rules
E -> T
E -> TE
T -> F
T -> F*
T -> F|T
T -> F?
T -> .
T -> \F
F -> v
F -> (E)

Not legal examples
**
??
..
(x
x|
x*|b -> should this be legal?
x?|
x.|b
.?
.|b