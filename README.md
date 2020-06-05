# Regex Pattern Searching
### A home made Regex parser and pattern searcher

**Assignment Grade:** A+ (96%)


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
a*? (alternation provides same as option anyway)
a?* (makes little sense also)
