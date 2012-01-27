#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define WMAX 256

typedef struct _list {
    char name[WMAX];
    struct _list *next;
} list;

int
main(int argc, char **argv)
{
    if (argc == 1)
        exit(1);
    int i;
    list *head, *curr, *nodeptr, *tmp;

    i = 1;
    head = (list *) malloc(sizeof(list *));
    strncpy(head->name,argv[i],strlen(argv[i]));
    curr = head;

    // Read in the list from args
    while (argv[++i]) {
        nodeptr = (list *) malloc(sizeof(list *));
        strncpy(nodeptr->name,argv[i],strlen(argv[i]));
        nodeptr->next = NULL;
        curr->next = nodeptr;
        curr = nodeptr;
    }

    for (nodeptr = head; nodeptr != NULL; nodeptr = nodeptr->next)
        printf("straight list\t%s\n",nodeptr->name);

    printf("\n");

    // reverse the list
    curr = head;
    nodeptr = NULL;
    
    while(curr->next != NULL) {
        tmp = curr->next;
        curr->next = nodeptr;
        nodeptr = curr;
        curr = tmp;
    }

    curr->next = nodeptr;
    head = curr;

    for (nodeptr = head; nodeptr != NULL; nodeptr = nodeptr->next)
        printf("reversed list\t%s\n",nodeptr->name);

    exit(0);
}
