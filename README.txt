CFG rules
E -> T
E -> T|E
E -> TE
T -> F
T -> F*
T -> F?
T -> .
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