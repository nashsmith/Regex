CFG rules
E -> T
E -> T|E
T -> TE
T -> F
T -> F*
T -> F|T
T -> F?
F -> .
F -> \C
F -> v
F -> (E)

E = expression
T = term
F = factor
v = vocabulary
C = any character

Not legal examples
**
??
.\
(x
x|
x?|
Any spaces