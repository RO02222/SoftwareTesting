States:
- Starting: s-starting
- Playing: s-playing
- Halted: s-halted
- Game over:    s-gameover-won
                s-gameover-died
Actions: 
a_initializing
a_movingplayer
a_movingmonster
a_halting
a_resuming

Events:
e-start
e-moveplayer-live
e-moveplayer-die
e-moveplayer-win
e-movemonster-kill
e-quit

Transitions:
s-starting -> s-playing (e-start)
s-playing -> s-playing (e-moveplayer-live)
s-playing -> s-gameover-died (e-moveplayer-die)
s-playing -> s-gameover-died (e-movemonster-kill)
s-playing -> s-gameover-won (e-moveplayer-win)
s-playing -> s-halted (e-quit)
s-halted -> s-playing (e-start)
