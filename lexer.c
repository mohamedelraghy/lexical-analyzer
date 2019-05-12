#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void math_operator_check()
{

    char ch, string_input[15], operators[] = "+-*/%";
    FILE *fp;
    char tr[20];
    int i, j = 0;

    fp = fopen("input.txt", "r");

    if (fp == NULL)
    {
        printf("error while opening the file\n");
        exit(0);
    }
    printf("\nMath Operators : ");
    while ((ch = fgetc(fp)) != EOF)
    {
        for (i = 0; i < 6; ++i)
        {
            if (ch == operators[i])
                printf("%c ", ch);
        }
    }
    printf("\n");

    fclose(fp);
}

void logical_operator_check()
{

    char ch, string_input[15], operators[] = "&&||<>";
    FILE *fp;
    char tr[20];
    int i, j = 0;

    fp = fopen("input.txt", "r");

    if (fp == NULL)
    {
        printf("error while opening the file\n");
        exit(0);
    }
    printf("\nLogical Operators : ");
    while ((ch = fgetc(fp)) != EOF)
    {
        for (i = 0; i < 6; ++i)
        {
            if (ch == operators[i])
                printf("%c ", ch);
        }
    }
    printf("\n");

    fclose(fp);
}

void numerical_check()
{

    char ch, string_input[15], operators[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    FILE *fp;

    int i, j = 0;

    fp = fopen("input.txt", "r");

    if (fp == NULL)
    {
        printf("error while opening the file\n");
        exit(0);
    }
    printf("\nNumerical Values : ");
    while ((ch = fgetc(fp)) != EOF)
    {
        for (i = 0; i < 6; ++i)
        {
            if (ch == operators[i])
                printf("%c ", ch);
        }
    }
    printf("\n");

    fclose(fp);
}

void others_check()
{
    char ch, string_input[15], symbols[] = "(){}[]";
    FILE *fp;
    char tr[20];
    int i, j = 0;

    fp = fopen("input.txt", "r");

    if (fp == NULL)
    {
        printf("error while opening the file\n");
        exit(0);
    }
    printf("\nOthers : ");
    while ((ch = fgetc(fp)) != EOF)
    {
        for (i = 0; i < 6; ++i)
        {
            if (ch == symbols[i])
                printf("%c ", ch);
        }
    }
    printf("\n");

    fclose(fp);
}

void identifier_check()
{
    char ch, string_input[15];
    FILE *fp;
    char operators[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    int i, j = 0;

    fp = fopen("input.txt", "r");

    if (fp == NULL)
    {
        printf("error while opening the file\n");
        exit(0);
    }

    printf("\nIdentifiers : ");
    while ((ch = fgetc(fp)) != EOF)
    {

        if (isalnum(ch))
        {
            string_input[j++] = ch;
        }
        else if ((ch == ' ' || ch == '\n') && (j != 0))
        {
            string_input[j] = '\0';
            j = 0;

            if (isKeyword(string_input) == 1)
            {
            }

            else
                printf("%s ", string_input);
        }
    }

    printf("\n");

    fclose(fp);
}

int isKeyword(char string_input[])
{
    char keywords[32][10] = {"auto", "break", "case", "char", "const", "continue", "default",
                             "do", "double", "else", "enum", "extern", "float", "for", "goto",
                             "if", "int", "long", "register", "return", "short", "signed",
                             "sizeof", "static", "struct", "switch", "typedef", "union",
                             "unsigned", "void", "volatile", "while"};
    int i, flag = 0;

    for (i = 0; i < 32; ++i)
    {
        if (strcmp(keywords[i], string_input) == 0)
        {
            flag = 1;
            break;
        }
    }

    return flag;
}

void keyword_check()
{

    char ch, string_input[15], operators[] = "+-*/%=";
    FILE *fp;
    char tr[20];
    int i, j = 0;

    printf(" Token Identification using C\n\n");

    fp = fopen("input.txt", "r");

    if (fp == NULL)
    {
        printf("error while opening the file\n");
        exit(0);
    }

    printf("\nKeywords : ");
    while ((ch = fgetc(fp)) != EOF)
    {

        if (isalnum(ch))
        {
            string_input[j++] = ch;
        }
        else if ((ch == ' ' || ch == '\n') && (j != 0))
        {
            string_input[j] = '\0';
            j = 0;

            if (isKeyword(string_input) == 1)
                printf("%s ", string_input);
        }
    }

    printf("\n");

    fclose(fp);
}

int main()
{
    /* By Ashik Rabbani
    Daffodil International University,CSE43 */
    keyword_check();
    identifier_check();
    math_operator_check();
    logical_operator_check();
    numerical_check();
    others_check();

    return 0;
}