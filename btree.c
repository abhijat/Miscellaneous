#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static int tabby = 0;

typedef struct Node_ {
    int node_value;
    struct Node_ *left;
    struct Node_ *right;
} Node;

void itw(Node *);
void init_node(Node **, const char *);

int
main()
{
    Node *root;
    Node *tmp1, *tmp2;

    init_node(&root, "root");
    init_node(&tmp1, "tmp1");
    init_node(&tmp2, "tmp2");

    root->left = tmp1;
    root->right = tmp2;

    root->node_value = 10;
    tmp1->node_value = 6;
    tmp2->node_value = 11;

    init_node(&tmp1, "tmp1");
    init_node(&tmp2, "tmp2");

    root->left->left = tmp1;
    tmp1->node_value = 2;
    root->left->right = tmp2;
    tmp2->node_value = 7;

    itw(root);

    return 0;
}

void
init_node(Node **n, const char *name)
{
    *n = (Node *)malloc(sizeof(Node));
    (*n)->left = NULL;
    (*n)->right = NULL;
    printf("I am stoopid %p handling %s now shifted it to %p\n",n,name,*n);
}

void
itw(Node *n)
{
    int i = 0;
    if (n != NULL) {
        itw(n->left);
        while(i++ < tabby)
            printf(" * ");
        printf("%d\n",n->node_value);
        itw(n->right);
    }
    tabby++;

    return;
}
