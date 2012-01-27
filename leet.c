#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define WMAX 256

static const char map[] = "a4e3i1l1o0s5";
static void leetify(const char *, int);

int
main(int argc, char **argv)
{
    if (argc == 1)
        exit(1);

    int mode = 1;

    while(*++argv) {
        leetify(*argv,mode);
        printf(" ");
    }
    printf("\n");
    exit(0);
}

void
leetify(const char *unleet, int mode)
{
    char c, *d;
    int i,j,k;
    char leet[WMAX];

    d = (char *) malloc(sizeof(char));
    if (d == NULL || strlen(unleet) >= WMAX)
        exit(1);

    i = 0;
    // reverse the input but we need to account for the null character.
    if (mode == 0) {
        j = strlen(unleet) - 1;
        k = j;
    } else {
        j = WMAX - 1;
        k = i;
    }

    // LI - leet contains the mapping to the reversed portion of unleet so far
    while ((c = unleet[k++]) != 0 && i < WMAX) // && k-- >= 0)
        if (isalpha(c) && (d = strchr(map,c)) != NULL)
            leet[i++] = *++d;
        else
            leet[i++] = c;
    
    leet[i] = 0;
    printf("%s",leet);

    return;
}
