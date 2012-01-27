#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAXLIST 1024
#define LISTCHUNK 3

char **
string_to_list(const char *str, const char *delim)
{
    char *line;
    char **ret_list;
    char *context;
    char *tmp;
    int i;

    line = strdup(str);
    i = 0;

    if ((tmp = strtok_r(line, delim, &context)) != NULL) {
            ret_list = malloc(LISTCHUNK * sizeof(char *));
            if (ret_list == NULL)
                return NULL;
            ret_list[i++] = strdup(tmp);
    }

    while ((tmp = strtok_r(NULL, delim, &context)) != NULL) {
        if (i == LISTCHUNK)
            ret_list = realloc(ret_list, (i+LISTCHUNK) * sizeof(char *));
        ret_list[i++] = strdup(tmp);
    }

    ret_list[i] = (char *)0;
    ret_list = realloc(ret_list, i * sizeof(char *));
    free(tmp);
    tmp = NULL;

    return ret_list;
}

void
destroy_list(void **list)
{
    int i;
    if (list == NULL)
        return;
    for (i = 0; list[i] != NULL; i++) {
        free(list[i]);
        list[i] = NULL;
    }
    free(list);
    list = NULL;
    return;
}

int
main(void)
{
    char **list;
    int i;

    list = string_to_list("Johnny is a stupid boy.", " ");
    for (i = 0; list[i] != NULL; i++) {
        printf("%s\n", list[i]);
    }

    destroy_list((void **)list);
    return 0;
}
