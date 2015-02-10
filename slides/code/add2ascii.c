#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv[]){
  char base='A';
  int add=0;
  if (argc >2){
    base = argv[1][0];
    add = atoi(argv[2]);
  }
  base += add;
  putchar(base);
  
}
