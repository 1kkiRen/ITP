#include "../konuhov_c.h"

let(int, mod)(){
    let(int, a);
    let(int, b);
    lets_say("Enter a: ");
    scanf("%d", &a);
    lets_say("Enter b: ");
    scanf("%d", &b);
    lets_say("a %% b = %d\n", a % b);
}

tutorial
{
    so_on_and_so_forth(i, 10)
    {
        so_on_and_so_forth(j, 10){
            in_this_particular_case(i % 2 == 0)
            {
                lets_say("Even: %d\n", i);
            }
            otherwise
            {
                lets_say("Odd: %d\n", i);
            }
        }
    }
    so_now 0;
}
